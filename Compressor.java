import java.util.*;

/**
 * This class deals with the text compression by using a dictionary of words and
 * integer arrays holding indexes of those word instances. ArrayList<String>
 * dictionary is chosen because of index ability and dynamic size.
 * 
 * @author rachelbakke rmb2235
 *
 */
public class Compressor {
	/**
	 * Constructs a Compressor and initializes ArrayList dictionary.
	 */
	public Compressor() {
		dictionary = new ArrayList<String>();
	}

	/**
	 * Converts the ArrayList dictionary into a printable String, allowing it to be
	 * placed at the header of a compressed file.
	 * 
	 * @return String of words stored in ArrayList dictionary
	 */
	public String getDictionary() {
		StringBuilder builder = new StringBuilder();
		for (String s : dictionary) {
			builder.append(s + " ");
		}
		return builder.toString();
	}

	/**
	 * Converts the header of line of a compressed file into a ArrayList dictionary.
	 * 
	 * @param input first line in compressed file, holding words for dictionary
	 */
	public void setDictionary(String input) {
		String[] split = input.split("\\s");
		for (String element : split) {
			dictionary.add(element);
		}
	}

	/**
	 * This method encodes a line of words into indexes of the dictionary.
	 * 
	 * @return encoded Integer Array of dictionary indexes
	 */
	public int[] encode(String input) {
		String[] split = input.split("\\s");
		int[] newEncodedLine = new int[split.length];
		for (int i = 0; i < split.length; i++) {
			int indexCode = -1;
			for (int j = 0; j < dictionary.size(); j++) {
				if (split[i].contentEquals(dictionary.get(j)))
					indexCode = j;
			}
			if (indexCode == -1) {// not in dictionary
				dictionary.add(split[i]);
				indexCode = dictionary.indexOf(split[i]);
			}
			newEncodedLine[i] = indexCode;
		}
		return newEncodedLine;
	}

	/**
	 * This method decodes indexes of the dictionary into the words they point to
	 * and formats them into a printable line.
	 * 
	 * @return readable line of words
	 */
	public String decode(int[] wordIndexes) {
		String[] wordArray = new String[wordIndexes.length];
		for (int i = 0; i < wordArray.length; i++) {
			wordArray[i] = dictionary.get(wordIndexes[i]) + " ";
		}
		StringBuilder builder = new StringBuilder();
		for (String s : wordArray) {
			builder.append(s);
		}
		return builder.toString();
	}

	/**
	 * This method cleans out the dictionary when lines are removed as to not hold
	 * on to more words than present in the file.
	 * 
	 * @param fullText holds all Integer arrays of corresponding dictionary indexes
	 */
	public void updateDictionary(ArrayList<int[]> fullTextNumbers) {
		int counter = 0;
		for (int i = 0; i < dictionary.size(); i++) {
			for (int[] temp : fullTextNumbers) {
				for (int j = 0; j < temp.length; j++) {
					if (temp[j] == i)
						counter++;
				}
			}
			if (counter == 0)
				fullTextNumbers.remove(i);
		}
	}

	/**
	 * This method facilitates communication between the ArrayList dictionary and
	 * the Compression Editor. Finds index of a given word.
	 * 
	 * @param wordToCheck a given word that could be in dictionary
	 * @return dictionary index of the indicated word, and if not present in
	 *         dictionary, returns -1.
	 */
	public int getDictionaryIndex(String wordToCheck) {
		return dictionary.indexOf(wordToCheck);
	}

	/**
	 * This method adds an individual word to the dictionary in the case of a
	 * findAndReplace() call and the word is not in the dictionary already. Checks
	 * if it is in already in the dictionary, in case user makes mistake.
	 * 
	 * @param wordToAdd a given word that is not already in the dictionary
	 * @return index of the newly added word, even if it is not actually newly
	 *         added.
	 */
	public int addWord(String wordToAdd) {
		if (getDictionaryIndex(wordToAdd) == -1)
			dictionary.add(wordToAdd);
		return dictionary.indexOf(wordToAdd);
	}
	private ArrayList<String> dictionary;
}
