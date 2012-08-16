import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser Class
 * @author Victoriano Izquierdo
 * @student 3395032
 * @course Information Retrieval
 * @assignment 1
 */

public class Parser {
	
	public static boolean p;
	public static boolean existingDoc;
	String inputpath;
	String mydocid;
	public static int mydocnumber;
	int currentDocN = 1;
	List<String> texttokens = new ArrayList<String>();
	public static List<String> stemmtokens = new ArrayList<String>();	
	
	
	/*  Simple methods to check and print state variables */
	public void printparam(){
		System.out.println("P is set to: " + p);
		System.out.println("Retrieving Doc: " + inputpath);
		System.out.println();
	}
	
	public void printdocid(){
		System.out.println("Found DOC ID:" + " " + mydocid );
		System.out.println("given number ID is :" + " " + mydocnumber );
		System.out.println();
	}
	
	public void printtokens(){
		
		/* System.out.println("Saved tokens -> ");
		
		for(String t : texttokens)
			System.out.println(t); */
		
		System.out.println("Saved stemmed tokens -> ");
		
		for(String t : stemmtokens)
			System.out.println(t);
		
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
	 * until </DOCNO>
	 */
	
	public void findid(Scanner input) throws FileNotFoundException{
		
		//File file = new File(inputpath);
		//Scanner input = new Scanner(file);
		
		String stopToken = "</DOCNO>";
		String currentToken = "";
		
		while(!currentToken.equals(stopToken)) {
			
			currentToken = input.next();
			if(currentToken.equals(stopToken)){break;}
			
			//System.out.println("This is the token" + " " + currentToken );
			mydocid = currentToken;
					    
		}

	}	
	
	
	/* ** Utility function for Search ** */
	//It returns the DocID of a given number Doc
	
	public static String getDocID(int doc) throws FileNotFoundException{
		
		String foundDoc = "";
		
		//Load Map to Memory in the HashTable
		HashMap<Integer, String> virtualMap = new HashMap<Integer, String>();
		File mapfile = new File("map.txt");	
		Scanner map = new Scanner(mapfile);
		// Reading and storing in a HashMap current info of the map file
		while(map.hasNextLine()) {	
			String currentline = map.nextLine();
			String [] theline = currentline.split("\\s+");
			int cdocnumber = Integer.parseInt(theline[0]);
			//If this is the DOC number return ID associated
			if(cdocnumber == doc){ 
				foundDoc = theline[1]; 
				};
			//System.out.println("El mydocnumber= " + mydocnumber);
			virtualMap.put(cdocnumber, theline[1]);				
			}
		map.close();
		//Get and return Value of the Key "doc"
		
		return foundDoc;
		
	}
	
	/* Loading the map from
	 * disk, checking if that
	 * DOC has already been indexed
	 * writing back to disk 
	 * 
	 */
	
	
	@SuppressWarnings("unchecked")
	public void writemap() throws IOException{
		
		boolean exists = (new File("map.txt")).exists();
		
		int totaldocs = 0;
		HashMap<Integer, String> virtualMap = new HashMap<Integer, String>();
		
		//First checking if map.txt already exists
		if (exists){
						
			File mapfile = new File("map.txt");	
			
			Scanner map = new Scanner(mapfile);
			// Reading and storing in a HashMap current info of the map file
			while(map.hasNextLine()) {	
				String currentline = map.nextLine();
				String [] theline = currentline.split("\\s+");
				int cdocnumber = Integer.parseInt(theline[0]);
				if(mydocid.equals(theline[1])){ 
					mydocnumber = cdocnumber;
					existingDoc = true;
					};
				//System.out.println("El mydocnumber= " + mydocnumber);
				virtualMap.put(cdocnumber, theline[1]);				
				++totaldocs;
				
			}
			map.close();
			
			/* If the present document is the 
			 * first time indexed, we added
			 * to the end of the HashMap and rewrite map.txt
			 *  */
			
			if(!virtualMap.containsValue(mydocid)){
				++totaldocs;
				virtualMap.put(totaldocs, mydocid);
				mydocnumber = totaldocs;
				//System.out.println("El mydocnumber2= " + mydocnumber);
			 }
			
			// Writing back to map file using the HashMap			
				
			Iterator it = virtualMap.entrySet().iterator();
			BufferedWriter file = new BufferedWriter(new FileWriter("map.txt"));
			while (it.hasNext()) { 
			Map.Entry e = (Map.Entry)it.next();
			String writingline = e.getKey() + " " + e.getValue();
			file.write(writingline);
			file.write("\n");			
			}
			file.flush();
			file.close();

			
		}else{
			/* Write back just the new Information read
			 * if didn't exits originally 
			 */
			File mapfile = new File("map.txt");
			mydocnumber = 1;
			String lastline = 1 + " " + mydocid ;
			mapfile.createNewFile();
			BufferedWriter file = new BufferedWriter(new FileWriter("map.txt"));
			file.write(lastline);
			file.flush();
			file.close();
		}
				

	}
	

	/*  Saving all tokens found
	 *  within <TEXT> 
	 *  tags in a texttokens List 
	 *  
	 */
	
	 
	public List<String> readtext(Scanner input, int theDocN ) throws IOException{
		
		List<String> texttokensC = new ArrayList<String>();
		
		//File file = new File(inputpath);
		//Scanner input = new Scanner(file);	
	    
		String pat = "<TEXT>";
	    String patend = "</TEXT>";
	    String currenttoken = "";
	    
	    //System.out.println("KKK: " + input.next() );

	    while(input.findWithinHorizon(pat, 0) != null ){
	    //System.out.println("En el primer loop: " + currenttoken );
	    	while(!input.hasNext(patend)){
	    		
	    		currenttoken = input.next();
	    		//System.out.println("KKK: " + currenttoken );
	    		//System.out.println("Current docN " + currentDocN + " " + theDocN );
	    		//if(currenttoken.equals(patend)){break;}
	    		//if(currenttoken.equals(patend)){break;}
	    		//System.out.println("En el segundo loop: " + currenttoken );
	    		
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
	    			if(currentDocN == theDocN){
	    				texttokensC.add(currenttoken);
	    				//System.out.println("Adding token: " + currenttoken );
	    			}
	    		}   	
	    		
	    	}
	    	currentDocN++;
	    	if(currentDocN>theDocN){
	    		break;
	    	}
    		//System.out.println( "We are in the docN" + docN);
	     
	    }
	    
	    return texttokensC;
 
	  } 
	
	
	
	/* Get token one by one and 
	 * apply stemming rules 
	 * to each token and save them in a List stemmtokens 
	 * 
	 */
	
	public List<String> stemmtext(List<String> currentTokens){
		
		List<String> stemmtokensC = new ArrayList<String>();
		
		Stemmer stemmer = new Stemmer();
		ListIterator<String> linesIterator = currentTokens.listIterator();
		
		//Getting Tokens
		while(linesIterator.hasNext()){
		
		String mytoken = linesIterator.next();
		
		//convert them to lower case first
		mytoken = mytoken.toLowerCase(); 

		String stemmedword = stemmer.exec(mytoken);
		//System.out.println(stemmedword);
		stemmtokensC.add(stemmedword);
		
		}
		return stemmtokensC;
	}
	
	
	
}
