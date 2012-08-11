import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {
	
	boolean p; 
	String inputpath;
	String mydocid;
	List<String> texttokens = new ArrayList<String>();
	List<String> stemmtokens = new ArrayList<String>();	
	
	/*  Simple methods to check and print state variables */
	public void printparam(){
		
		System.out.println("P is set to: " + p);
		System.out.println("Retrieving Doc: " + inputpath);
		System.out.println();
	}
	
	public void printdocid(){
	System.out.println("With DOC ID:" + " " + mydocid );
	System.out.println();
	}
	
	public void printtokens(){
		ListIterator<String> linesIterator = texttokens.listIterator();
		ListIterator<String> linesIterator2 = stemmtokens.listIterator();
		
		System.out.println("Saving tokens -> ");
		//Getting tokens from the list
		while(linesIterator.hasNext()){
		String cline = linesIterator.next();
		System.out.println(cline);
		}
		System.out.println();
		
		System.out.println("Stemmed tokens -> ");
		//Getting tokens from the list
		while(linesIterator2.hasNext()){
		String cline = linesIterator2.next();
		System.out.println(cline);
		}
		System.out.println();
	}
	
	
	/* Simplest getopts() ever, it does not support
	 * other initialization than a single input file 
	 * parameter or 2 with a print flag option in the first place
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
	
	
	/* Open the doc file, find <DOCNO> 
	 * and store the ID in the variable String mydocid 
	 */
	
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
		
		input.close();
	}	
	
	/* I don't check whether this doc has been 
	 * already mapped or not
	 * just include it to end of the map adding
	 * the corresponding number
	 * 
	 */
	
	public void writemap() throws IOException{
		
		File mapfile = new File("map.txt");
		Scanner map = new Scanner(mapfile);
		int i = 0;
		ArrayList<String> lines = new ArrayList<String>();
		
		// Reading and storing current info of the map file
		while(map.hasNextLine()) {
			
			String currentline = map.nextLine();
			lines.add(currentline);
			++i;
		    //System.out.println("Reading: " + currentline + " number" + i);	
		}	
		
		// Writing back to map file using the Arraylist + the new information
		BufferedWriter file = new BufferedWriter(new FileWriter("map.txt"));
		ListIterator<String> linesIterator = lines.listIterator();
		
		//Getting info from the Arraylist
		while(linesIterator.hasNext()){
		String cline = linesIterator.next();
		//System.out.println(cline);
		file.write(cline);
		file.write("\n");
		}
		
		// New Information
		String lastline = i+1 + " " + mydocid ;
		System.out.println("Adding Doc to map -> " + lastline);
		System.out.println();
		file.write(lastline);
		
		map.close();
		file.flush();
		file.close();
	}
	
	
	/*  Saving all token found
	 *  within <TEXT> 
	 *  tags in texttokens List 
	 *  
	 */
	
	public void readtext( ) throws IOException{
		
		File file = new File(inputpath);
		Scanner input = new Scanner(file);	
	    
		String pat = "<TEXT>";
	    String patend = "</TEXT>";

	    while(input.findWithinHorizon(pat, 0) != null ){
	    	while(!input.hasNext(patend)){
	    		
	    		String currenttoken = input.next();
	    		
	    		// Regex for HTML tags within the <TEXT></TEXT>
	    		String patternStr = "<[/]*([A-Z]+)*[^/]*?>";
	    		Pattern pattern = Pattern.compile(patternStr);
	    		Matcher matcher = pattern.matcher(currenttoken);
	    		boolean matchFound = matcher.matches(); 
		
	    		if(matchFound){
	    		//System.out.println("Not Adding token: " + currenttoken );
	    		}
	    		else{
	    		//System.out.println("Adding token: " + currenttoken );
	    		texttokens.add(currenttoken);
	    		}   	
	    	}
 
	    }

	    input.close();		 
	}
	
	/* 5 - public void stemmtext( List texttokens ){}
	Get token one by one and apply stemming rules 
	to each token and save them in a List stemmtokens */
	
	public void stemmtext(){
		
		MyStemmer stemmer = new MyStemmer();
		ListIterator<String> linesIterator = texttokens.listIterator();
		
		//Getting Tokens
		while(linesIterator.hasNext()){
		
		String mytoken = linesIterator.next();
		
		//convert them to lower case first
		mytoken = mytoken.toLowerCase(); 

		String stemmedword = stemmer.exec(mytoken);
		//System.out.println(stemmedword);
		stemmtokens.add(stemmedword);
		
		}
		
	}
	
	
}
