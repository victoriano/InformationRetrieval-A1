import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * Main Search program class
 * @author Victoriano Izquierdo
 * @student 3395032
 * @course Information Retrieval
 * @assignment 1
 */

public class search {
	
	public int docid;
	
	/* Paths for files on disk*/
	String lexicon_path;
	String indexinvert_path;
	String map_path;
	
	ArrayList<String> querieTerms = new ArrayList<String>();
	
	
	/* Another simple getopts() 
	 * assign the corresponding path to each file; 
	 * and fill an ArrayList querieTerms with the queries
	 */
	
	public void getopts( String[] args ){
		
		lexicon_path = args[0];
		indexinvert_path = args[1];
		map_path = args[2];
		
		for(int i=3; i<args.length; i++){
			querieTerms.add(args[i]);
		}
		
	}
	
	public ArrayList<String> stemmer( ArrayList<String> rawTokens ){
		
		Stemmer stemmer = new Stemmer();
		ArrayList<String> stemmedTokens = new ArrayList<String>();
		ListIterator<String> linesIterator = rawTokens.listIterator();
		
		//Getting Tokens
		while(linesIterator.hasNext()){
		
		String mytoken = linesIterator.next();
		
		//convert them to lower case first
		mytoken = mytoken.toLowerCase();
		
		//remove any non-character in the string 
		mytoken = mytoken.replaceAll("\\W", "");

		String stemmedword = stemmer.exec(mytoken);
		//System.out.println(stemmedword);
		stemmedTokens.add(stemmedword);
		}
		
		return stemmedTokens;
		
		}
	
	public String readLineOfIndex(int line) throws FileNotFoundException{
		
		String desireLine = "";
		int istarts = line;
		
		File index = new File(indexinvert_path);	
		Scanner sindex = new Scanner(index);
		
		// Reading and returning just the line specified
		while(sindex.hasNextLine()) {
			String restofInfo = "";
			String currentline = sindex.nextLine();
			//System.out.println(currentline);
							
				String [] theline = currentline.split("\\s+");
				int tkey = Integer.parseInt(theline[0]);

				// Compile Info
				for(int i=1;i<theline.length;i++){
					restofInfo = restofInfo + theline[i] + " ";
				}
				//System.out.println("Adding a Index " + theword + " "+ tkey + " " +restofInfo);
				if( tkey == istarts){
				desireLine = restofInfo;
				
			}			
			
		}
		sindex.close();
		return desireLine;
	}
	
	/*
	 * Loop for every term of search
	 * stemming the query terms
	 * 
	 * Find the word in the lexicon if not - not found and exit()
	 * get and use the key of that word 
	 * to search in the Index that word
	 * retrieve from Word - total occurrences
	 * 					  - array of docs containing that word
	 * print these data
	 * 
	 * 

	*/
	
	public static void main(String[] args) throws IOException {
						
		search s = new search();
		Indexer indexer = new Indexer();
		int qkey;
		int totalOccurrences;
		
		s.getopts(args);
		ArrayList<String> StemmedQuerieTerms = new ArrayList<String>();
		StemmedQuerieTerms = s.stemmer(s.querieTerms);
		indexer.loadLexicon();
			
		for(String qterm : StemmedQuerieTerms){
			
			System.out.println("Search for: " + qterm);
			//Checks if the term was already in the lexicon
			if(indexer.lexicon.containsValue(qterm)){ 
				//Get the key associated with that value in the Lexicon
				qkey = indexer.findKey(qterm);
				//Scanner the doc line of qkey get a String 
				String rawstring = s.readLineOfIndex(qkey);
				//Convert that String to Word
				Word myword = new Word(qterm, rawstring);
				//Retrieve Total occurences of the query term
				totalOccurrences = myword.occurrences;
				System.out.println("Total Occurrences: " + totalOccurrences);
				//Retrieve Documents
				myword.retrievePrintDocs();
				System.out.println();

			}else{
				System.out.println(qterm + " is not indexed");
				System.out.println();
			}
			
		
		}
		
	}
}
