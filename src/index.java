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
		
		/*  ****** Parsing ******  */
		Parser myParser = new Parser();
		
		/* Get optional parameter from args[] */
		myParser.getopts(args);
		myParser.printparam();
		
		/* Extract the ID of the Doc and save it to the map file */
		myParser.findid();
		myParser.printdocid();
		myParser.writemap();		
		
		/* Retrieve tokens of the Doc and steam them */
		myParser.readtext();
		myParser.stemmtext();
		myParser.printtokens();
		
		
		/*  ****** Indexing ******  */

		//Indexer indexinvert = new Indexer(Parser.mydocnumber, Parser.stemmtokens);
		
		
	}

}
