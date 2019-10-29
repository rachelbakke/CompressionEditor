import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * This class operates a text editing system that compresses the files and edits
 * integer values. Communicates with a Compressor to encode Stores file content
 * in an ArrayList of int[] called encodedText, chosen for its index ability and
 * dynamic size while the Integer arrays have specified size from entered lines.
 * Contains commands of processes of getting the .cmp file, printing the file,
 * replacing a line, and saving to either .cmp or .txt. Uses a reader and writer
 * to handle .cmp files.
 * 
 * @author rachelbakke 2235
 *
 */
public class CompressionEditor {
	/**
	 * Constructs a Compression Editor by initializing ArrayList encodedText to hold
	 * number indexes, a compressor, and the currentLine.
	 */
	public CompressionEditor() {
		encodedText = new ArrayList<int[]>();
		compressor = new Compressor();
		inLine = null;
	}

	/**
	 * Takes in and stores the input line from the EditorInterface.
	 * 
	 * @param userInput
	 */
	public void passLine(String userInput) {
		inLine = userInput;
	}

	/**
	 * This method makes gets a .cmp file named from the input line, and encodes it
	 * into the ArrayList encodeText, using BufferedReader which was chosen for its
	 * ability to read more lines. If one is not found, a new file is made.
	 */
	public void getFile() {
		encodedText.clear();
		if (inLine.length() > 2) {
			String fileName = inLine.substring(2);
			try {
				BufferedReader reader = new BufferedReader(new FileReader(path + fileName + ".cmp"));
				String firstLine = reader.readLine();
				compressor.setDictionary(firstLine);
				String content = null;
				while ((content = reader.readLine()) != null) {
					String[] split = content.split("\\s");
					encodedLine = new int[split.length];
					for (int i = 0; i < split.length; i++) {
						encodedLine[i] = Integer.parseInt(split[i]);
					}
					encodedText.add(encodedLine);
				}
				reader.close();
			} catch (Exception e) {
				System.out.println("File not found.");
			}
		} else {
			print();
		}
	}

	/**
	 * This method saves the decoded contents of ArrayList encodedText to a file
	 * made with the name given from the command line. User Uses a FileWriter to add
	 * lines and is saved as a .cmp file to the appropriate path.
	 */
	public void saveFile() {
		String inName;
		if (inLine.length() > 2) { // has a saving name given in input line
			if (inLine.substring(2, 4).contentEquals("t ")) {
				fileType = ".txt";
				inName = inLine.substring(4);
			} else {
				fileType = ".cmp";
				inName = inLine.substring(2);
			}
			File file = new File(path + inName + fileType);
			try {
				Writer writer = new FileWriter(file, false);
				if (fileType.equals(".txt")) { // overridden to be a .txt file
					for (int i = 0; i < encodedText.size(); i++) {
						String line = compressor.decode(encodedText.get(i));
						writer.write(line + "\n");
					}
				} else { // saves as .cmp file
					writer.write(compressor.getDictionary() + "\n");
					for (int i = 0; i < encodedText.size(); i++) {
						int[] encodedLine = encodedText.get(i);
						String strOfInts = Arrays.toString(encodedLine).replaceAll("\\[|\\]|,|", "");
						writer.write(strOfInts + "\n");
					}
				}
				writer.close();
				System.out.println(inName + " written");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			System.out.println("Please enter s with a file name to set contents to");
	}

	/** 
	 * 
	 */
	public void replace() {
		if (inLine.length() > 2) { // has an index given
			int inputIndex = Integer.parseInt(inLine.substring(2, 3));
			if (inLine.length() > 4) { // has contents to replace
				String strPhrase = inLine.substring(4);
				int[] intPhrase = compressor.encode(strPhrase);
				if (inputIndex == encodedText.size()) {
					encodedText.add(intPhrase);
				} else
					encodedText.set(inputIndex, intPhrase);
			} else {
				encodedText.remove(inputIndex); // line is removed
				compressor.updateDictionary(encodedText);
			}
		} else {
			System.out.println("Please enter a valid index");
		}
	}

	/**
	 * This method prints the decoded contents of ArrayList encodedText with
	 * prepended index numbers from 0 to N+1.
	 */
	public void print() {
		int preIndex = 0;
		if (preIndex == encodedText.size()) { // if there is nothing in array
			System.out.println(preIndex);
			preIndex++;
		} else {
			while (preIndex < encodedText.size()) {
				int[] temp = encodedText.get(preIndex);
				System.out.println(preIndex + " " + compressor.decode(temp));
				preIndex++;
			}
		}
		System.out.println(preIndex);
	}

	/**
	 * Method added in 2.3. If the command is entered properly, the specified word
	 * is replaced with the following by sorting through indexes, even if the
	 * replacement exists in the dictionary already. If not in dictionary, it is
	 * added.
	 */
	public void findAndReplace() {
		if (inLine.length() > 4) {
			String[] inWords = inLine.substring(2).split(" ");
			String word = inWords[0];
			String replacement = inWords[1];
			int oldEncoded = compressor.getDictionaryIndex(word);
			int newEncoded = compressor.getDictionaryIndex(replacement);
			if (oldEncoded == -1) // word to change is in not dictionary
				System.out.println("The word commanded to replace is not in file.");
			else if (newEncoded != -1) { // replacement is in dictionary
				for (int[] temp : encodedText) {
					for (int j = 0; j < temp.length; j++) {
						if (temp[j] == oldEncoded)
							temp[j] = newEncoded;
					}
				}
			} else // word is in dictionary and replacement is not
				newEncoded = compressor.addWord(replacement);
			for (int[] temp : encodedText) {
				for (int j = 0; j < temp.length; j++) {
					if (temp[j] == oldEncoded)
						temp[j] = newEncoded;
				}
			}
		} else
			System.out.println("Please enter the words to find and replace with.");
	}

	private String fileType;
	private ArrayList<int[]> encodedText;
	private int[] encodedLine;
	private String inLine;
	private String path = "/Users/rachelbakke/Desktop/rmb2235_HW2/";
	private Compressor compressor;
}
