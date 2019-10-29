import java.util.ArrayList;
import java.io.*;

/**
 * This Class is the bulk of operating the basic text editing system. Stores
 * file content in an ArrayList called text, chosen for its index ability and
 * dynamic size. Contains commands of processes of getting the file, printing
 * the file, replacing a line, and saving a line. Uses a reader and writer to
 * handle .txt files.
 * 
 * @author rachelbakke rmb2235
 *
 */
public class Editor {
	/**
	 * Constructor initializes the ArrayList text and
	 * 
	 */
	public Editor() {
		text = new ArrayList<String>();
		inLine = null;
	}

	/**
	 * This method receives the input line from the user given from the
	 * EditorInterface.
	 * 
	 * @param userInput is the line the user input
	 */
	public void passLine(String userInput) {
		inLine = userInput;
	}

	/**
	 * This method makes gets a .txt file named from the input line and reads it
	 * into the ArrayList text, using BufferedReader which was chosen for its
	 * ability to read more lines. If one is not found, a new file is made.
	 */
	public void getFile() {
		text.clear();
		if (inLine.length() > 2) {
			String fileName = inLine.substring(2);
			try {
				BufferedReader reader = new BufferedReader(new FileReader(path + fileName + ".txt"));
				String content = null;
				while ((content = reader.readLine()) != null) {
					text.add(content);
				}
				reader.close();
			} catch (Exception e) {
				System.out.println("File not found.");
			}
		} else {
			text.add(" ");
			print();
		}
	}

	/**
	 * This method saves the contents of ArrayList text to a file made with the name
	 * given from the command line. Uses a FileWriter to add lines and is saved as a
	 * .txt file to the appropriate path.
	 */
	public void saveFile() {
		if (inLine.length() > 2) { // has a saving name given in input line
			String inName = inLine.substring(2);
			File file = new File(path + inName + ".txt");
			try {
				Writer writer = new FileWriter(file, false);
				for (int i = 0; i < text.size(); i++) {
					if (!text.get(i).equals(" ")) // removes initial condition of place-holding blank
						writer.write(text.get(i) + "\n");
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
	 * This method takes separates a properly-formatted user input line into an
	 * index and phrase to insert in the ArrayList text. If the user specifies an
	 * index and no phrase to insert, the line of the index is removed.
	 */
	public void replace() {
		if (inLine.length() > 2) { // has an index given
			int inputIndex = Integer.parseInt(inLine.substring(2, 3));
			if (inLine.length() > 4) { // has contents to replace
				String phrase = inLine.substring(4);
				if (inputIndex == text.size()) {
					text.add(phrase);
				} else
					text.set(inputIndex, phrase);
			} else {
				text.remove(inputIndex); // a blank is entered so the line is removed
			}
		} else {
			System.out.println("Please enter a valid index");

		}
	}

	/**
	 * This method prints the contents of ArrayList text with prepended index
	 * numbers from 0 to N+1.
	 * 
	 */
	public void print() {
		int preIndex = 0;
		if (preIndex == text.size()) {
			System.out.println(preIndex);
			preIndex++;
		} else {
			while (preIndex < text.size()) {
				System.out.println(preIndex + " " + text.get(preIndex));
				preIndex++;
			}
		}
		System.out.println(preIndex);
	}

	/**
	 * Method added in 2.3. If the command is entered properly, the specified word
	 * is replaced with the following.
	 */
	public void findAndReplace() {
		if (inLine.length() > 4) {
			String[] inWords = inLine.substring(2).split(" ");
			String word = inWords[0];
			String replacement = inWords[1];
			for (int index = 0; index < text.size(); index++) {
				String[] textLine = text.get(index).split(" ");
				for (int i = 0; i < textLine.length; i++) {
					if (textLine[i].equals(word))
						textLine[i] = replacement;
				}
				StringBuilder builder = new StringBuilder();
				for (String s : textLine)
					builder.append(s + " ");
				text.set(index, builder.toString());
			}
		} else
			System.out.println("Please enter the words to find and replace with.");
	}

	private ArrayList<String> text;
	private String inLine;
	private String path = "/Users/rachelbakke/Desktop/rmb2235_HW2/";
}
