import java.io.*;
import java.util.*;

/**
 * This class acts as an interface for the user (i.e. gets input from user to
 * decide succeeding actions). Can be used for both normal Editor and
 * Compression editor depending on the indication during construction. Prints
 * instructions so the user understands how the text editing system works and
 * takes in user commands to figure out succeeding actions, and has a sanity
 * check in order to make sure no inappropriate entries are inputed.
 * 
 * @author rachelbakke rmb2235
 */
public class EditorInterface {
	/**
	 * This method talks to the user by giving instructions, taking inputs, and
	 * figuring out succeeding actions based on input; succeeding actions are
	 * invoked with the Editor class methods.
	 * 
	 * @throws FileNotFoundException
	 */
	public void talk() throws FileNotFoundException {
		Scanner myScanner = new Scanner(System.in);
		editor = new CompressionEditor();
		System.out.println("Commands:\n" + "\n g   Get file from directory" + "\n p   Print entire file to console"
				+ "\n r   Replace this line" + "\n s   Set/Save file to directory" + "\n q   Quit"
				+ "\n\n The computer will prompt you for a command with ' > '. Start editing!");
		System.out.print("> ");
		goodLine = myScanner.nextLine();
		while (!goodLine.equalsIgnoreCase("q")) {
			try {
				if (needsAction(goodLine)) {
					process();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.print("\n>  ");
			goodLine = myScanner.nextLine();
		}
		myScanner.close();
		System.out.print("Goodbye!");
	}

	/**
	 * 
	 * This method identifies if the user entered an actionable command by the first
	 * character of the input line.
	 * 
	 * @param input is the String last entered by the user
	 * @return true if the input contains an valid command, false if the input does
	 *         not follow proper format of commands.
	 */
	private boolean needsAction(String input) throws FileNotFoundException {
		for (int i = 0; i < commands.length; i++) {
			if (input.charAt(0) == commands[i]) {
				return true;
			}
		}
		System.out.println("Please try again by entering a valid command.");
		return false;
	}

	/**
	 * This method communicates with the Editor by identifying which command the
	 * user entered and calling the next command along with passing the input line.
	 * 
	 * @throws FileNotFoundException
	 */
	public void process() throws FileNotFoundException {
		editor.passLine(goodLine);
		char command = goodLine.charAt(0);
		if (command == 'g')
			editor.getFile();
		else if (command == 'p')
			editor.print();
		else if (command == 'r')
			editor.replace();
		else if (command == 's')
			editor.saveFile();
		else if (command == 'w')
			editor.findAndReplace();
		else
			System.out.print("No file has been made yet.");
	}

	private char commands[] = { 'g', 'p', 'r', 's', 'q', 'w' };
	private CompressionEditor editor;
	private String goodLine;
}
