import java.io.*;

/**
 * Class RunnerForFiler that acts as a tester program; this system is for the
 * basic text editing system. To switch systems, adjust the type of editor
 *  made in the EditorInterface Class.
 * 
 * @author rachelbakke rmb2235
 * 
 *         Testing Editor 2.1: (give purpose/outputs) 
 *         A. File Creation (Cookies.txt is 84 Bytes)-
 *			File without a name is created with blank lines and 0 to 1 indexes prepended. 
 *			Incorrect commands are entered and the system outputs out how they should be fixed.  
 * 			Lines are replaced with content and with blanks, dance and dancing was found 
 * 			and replaced with code and coding. File is saved properly as Cookies.
 * 
 *         B. File Retrieval and change (cupcake.txt is 98 Bytes)-
 *         Cookies is opened and words are changed to say cupcake. A new file cupcake.txt is saved. 
 *         
 *         Testing CompressionEditor 2.2:
 *         A. File Creation (smallerCookies.cmp is 56 Bytes) 
 *         EditorInterface adjusted to create a CompressionEditor(). Handles same commands 
 *         entered and inputs given. It creates a smaller 
 *         
 *         B. File Retrieval and Change (Bananas.cmp is 64 Bytes)
 *         Able to be brought up in proper format and altered just the same. Similarly small 
 *         in size and updates dictionary properly.
 *         
 *         C. Text File Override (textOverride is 142 Bytes) 
 *         Overrides the making of a .cmp file into a .txt file. 
 * 
 * 
 */
public class Runner {
	public static void main(String[] args) throws IOException {
		EditorInterface textMaker = new EditorInterface();
		textMaker.talk();
	}
}
