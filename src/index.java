import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Main for Parsing + Indexing operations
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
		
		
		/*  Iterate Through all the Docs 
		 * of a given collection file path 
		 * 
		 */
			 
			File file = new File(myParser.inputpath);
			Scanner input = new Scanner(file);	
		    
			String starTokenD = "<DOC>";
		    String endTokenD = "</DOC>";
		    String currenttoken = "";
		    Parser.mydocnumber = 1;
		    
		    while(input.findWithinHorizon(starTokenD, 0) != null ){
		     //System.out.println("DOC En el primer loop: " + currenttoken );
		    	while(!input.hasNext(endTokenD)){
		    		
		    		currenttoken = input.next();
		    		//System.out.println("DOC En el segundo loop: " + currenttoken );
		    		if(currenttoken.equals("<DOCNO>")){
		    			//To la morcilla va aqu’
		    			
		    			/* Extract the ID of this DOC and save it to the map file */
		    			//System.out.println("Let's find Info for DOC: " + Parser.mydocnumber );
		    			//System.out.println();
		    			
		    			myParser.findid(input);
		    			myParser.printdocid();
		    			myParser.writemap();		
		    			
		    			//System.out.println("NUMBER DOC now: " + Parser.mydocnumber );
		    			/* Retrieve tokens of the Doc and steam them */
		    			List<String> currentTokens = myParser.readtext(input, Parser.mydocnumber);
		    			List<String> currentStemmedTokens = myParser.stemmtext(currentTokens);
		    					    					
		    			/*  ****** Indexing ******  */		    				
		    			Indexer myIndex = new Indexer();
		    			myIndex.exec(currentStemmedTokens); 
		    			
		    		}
		    			    		
		    	}
		    	++Parser.mydocnumber;
		    }
		    
		    System.out.println("Total DOCs found: " + Parser.mydocnumber);
		    System.out.println();
		    myParser.printtokens();
		    input.close();
	}
	

}
