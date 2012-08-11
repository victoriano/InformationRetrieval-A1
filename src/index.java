import java.io.IOException;

/**
 * Main File System
 * @author Victoriano Izquierdo
 * @student 3395032
 * @course Information Retrieval
 * @assignment 1
 */

public class index {
		
	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException {
		
		Parser myParser = new Parser();
		
		myParser.getopts(args);
		//myParser.printparam();
		
		/* Get the ID of the Doc and save it to the map file */
		myParser.findid();
		myParser.writemap();
		
		/* Retrieve tokens of the Doc and steam them */
		myParser.readfile();
		//myParser.printtokens();
				
		
	}

}
