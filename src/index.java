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
		
		/* Get parameters */
		myParser.getopts(args);
		myParser.printparam();
			
		
		/*  
		 *  Iterate Through all the Docs 
		 *  in a single collection file 
		 * 
		 */
			 
			File file = new File(myParser.inputpath);
			Scanner input = new Scanner(file);	
		    
			String starTokenD = "<DOC>";
		    String endTokenD = "</DOC>";
		    String currenttoken = "";
		    Parser.mydocnumber = 1;
		    
		    while(input.findWithinHorizon(starTokenD, 0) != null ){

		    	while(!input.hasNext(endTokenD)){
		    		
		    		currenttoken = input.next();

		    		if(currenttoken.equals("<DOCNO>")){
		    			
		    			/* Extract the ID of this DOC */		    			
		    			myParser.findid(input);
		    			myParser.printdocid();
		    			
		    			/* If it's a new DOC, save its ID to the map file */
		    			myParser.writemap();		
		    					    			
		    			/* Retrieve tokens of the DOC Text and steam them */
		    			List<String> currentTokens = myParser.readtext(input, Parser.mydocnumber);
		    			List<String> currentStemmedTokens = myParser.stemmtext(currentTokens);
		    			//myParser.printtokens(currentStemmedTokens);
		    					    					
		    			/*  ****** Indexing ******  */
		    			/* Index the SteemedTokens parsed in this DOC */
		    			Indexer myIndex = new Indexer();
		    			myIndex.exec(currentStemmedTokens); 
		    			myIndex.writeLexicon();
		    			myIndex.writeInvertIndex();
		    			
		    		}
		    			    		
		    	}
		    	++Parser.mydocnumber;	    	
		    	System.out.println();
		    }
		    
		    //Weights.printWeights();
		    /* Rewrite map with Documents Weights */
	    	Weights.rewritemap();
		    input.close();
	}
	

}
