import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Parser {
	
	boolean p; 
	String inputpath;
	String mydocid;
	
	
	/* Simples getopts() ever, it does not support
	 * other initialization than a single input file 
	 * parameter or 2 with a print flag option
	 */
	public void getopts( String[] args ){
		
		if(args.length == 0){
			System.out.println("You forgot to " +
				"introduce an input path, exiting...");
			System.exit(0);
		}
		if(args.length == 1){
			inputpath = args[0];
			p = false;
		}else if(args.length == 2){
			inputpath = args[1];
			p = true;
		}
		
	}
	
	
	/* Open the file, find <DOCNO> 
	and read in the variable String mydocid 
	until it reaches </DOCNO> */
	
	public void findid() throws FileNotFoundException{
		
		File file = new File(inputpath);
		Scanner input = new Scanner(file);

		while(input.hasNext()) {
			
			String currentToken = input.next();
			//System.out.println("This is the token" + " " + currentToken );
		    
		    if(currentToken.equals("<DOCNO>")){
		    	mydocid = input.next();	
		    }
		    
		}
		
		System.out.println("The DOC ID is:" + " " + mydocid );
		input.close();	
	}
	

	

}
