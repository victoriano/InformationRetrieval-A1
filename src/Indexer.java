import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Dealing with the indexing process
 * @author Victoriano Izquierdo
 * @student 3395032
 * @course Information Retrieval
 * @assignment 1
 */

public class Indexer {
	
	/* Paths for files on disk*/
	String lexicon_path = "lexicon.txt";
	String indexinvert_path = "indexinvert.txt";
	
	/*Data Structures in memory */
	int docnum;
	boolean printing;
	int totalTermsLoaded = 0;
	List<String> stemmtokens = new ArrayList<String>();
	HashMap<Integer, String> lexicon = new HashMap<Integer, String>();
	HashMap<Integer, Word> indexinvert = new HashMap<Integer, Word>();
	
	
	/* Basic constructor */
	public Indexer(){
		this.docnum = Parser.mydocnumber;
		this.stemmtokens = Parser.stemmtokens;
		this.printing = Parser.p ;
	}
	
	
	/* Load lexicon from disk into a HashMap<Integer, String> */
	public void loadLexicon () throws IOException{		
		boolean exists = (new File("lexicon.txt")).exists();
		
		if (exists){
			
			File lexfile = new File("lexicon.txt");	
			
			Scanner lmap = new Scanner(lexfile);
			// Reading and storing in a HashMap current info of the map file
			while(lmap.hasNextLine()) {	
				String currentline = lmap.nextLine();
				String [] theline = currentline.split("\\s+");
				int tkey = Integer.parseInt(theline[0]);
				//System.out.println("Adding a Lexicon " + tkey + " " + theline[1]);
				lexicon.put(tkey, theline[1]);				
				++totalTermsLoaded;				
			}
			lmap.close();
			
		}else{
			File mapfile = new File("lexicon.txt");
			mapfile.createNewFile(); 
		}		
		
	}
	
	/*Load invert index from disk into a HashMap<Integer, Word> */
	public void loadInvertIndex () throws IOException{
		
		boolean exists = (new File("indexinvert.txt")).exists();
		
		if (exists){
			File lexfile = new File("indexinvert.txt");	
			
			Scanner lmap = new Scanner(lexfile);
			// Reading and storing in a HashMap current info of the map file
			while(lmap.hasNextLine()) {	
				String restofInfo = "";
				String currentline = lmap.nextLine();
				String [] theline = currentline.split("\\s+");
				int tkey = Integer.parseInt(theline[0]);
				//Find Word Term with tkey in the lexicon
				String theword = lexicon.get(tkey);
				// Compile Info
				for(int i=1;i<theline.length;i++){
					restofInfo = restofInfo + theline[i] + " ";
				}
				//System.out.println("Adding a Index " + theword + " "+ tkey + " " +restofInfo);
				Word termInfo = new Word(theword, restofInfo);
				//System.out.println("Traducido como " + termInfo.serializeWord());
				indexinvert.put(tkey, termInfo);								
			}
			lmap.close();
			
		}else{
			File mapfile = new File("indexinvert.txt");
			mapfile.createNewFile(); 
		}
		
	}
	
	/* Write Lexicon back */
	@SuppressWarnings("unchecked")
	public void writeLexicon() throws IOException{	
		Iterator it = lexicon.entrySet().iterator();
		BufferedWriter file = new BufferedWriter(new FileWriter("lexicon.txt"));
		while (it.hasNext()) { 
		Map.Entry e = (Map.Entry)it.next();
		String writingline = e.getKey() + " " + e.getValue();
		file.write(writingline);
		file.write("\n");			
		}
		file.flush();
		file.close();
	}
	
	/* Write InvertIndex back */
	@SuppressWarnings("unchecked")
	public void writeInvertIndex() throws IOException{
		Iterator it = indexinvert.entrySet().iterator();
		BufferedWriter file = new BufferedWriter(new FileWriter("indexinvert.txt"));
		while (it.hasNext()) { 
		Map.Entry e = (Map.Entry)it.next();
		Word winfo = (Word) e.getValue();
		String objseri = winfo.serializeWord();
		String writingline = e.getKey() + " " + objseri;
		file.write(writingline);
		file.write("\n");			
		}
		file.flush();
		file.close();
		
	}
	
	public void addTermtoLexicon(String term){
		//Add it to the lexicon
		int tkey = 1;
		tkey += lexicon.size();
		lexicon.put(tkey, term);
		
		//Add it to index
		//System.out.println("Docnum: " + docnum);
		Word myworte = new Word(term, docnum);
		indexinvert.put(tkey, myworte);
	}
	
	@SuppressWarnings("unchecked")
	public void updateOccurrences(String term){
		
		//Find this.term in the index HastTable
		Iterator it = indexinvert.entrySet().iterator();
		
		while (it.hasNext()) { 
			Map.Entry e = (Map.Entry)it.next();
			Word winfo = (Word) e.getValue();
			String wterm = winfo.term;
			//System.out.println("El termino es " + wterm);
			if(wterm.equals(term)){
				winfo.addOccurrenceToDoc(docnum);
				//++winfo.occurrences;
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public int findKey(String term){
		
		//Find this.term in the index HastTable
		Iterator it = lexicon.entrySet().iterator();
		int Gkey = 0;
		while (it.hasNext()) { 
			Map.Entry e = (Map.Entry)it.next();
			String fterm = (String)e.getValue();
			int fkey = (Integer)e.getKey();
			//System.out.println("El termino es " + wterm);
			if(fterm.equals(term)){
				Gkey = fkey;
				//++winfo.occurrences;
			}
		}
		
		return Gkey;
		
	}		

	/* Firer of instruction for the indexer */
	
	public void exec(List<String> currentStemmedTokens) throws IOException{
 
		//Checks if this DOC has already been indexed before indexing
		if(Parser.existingDoc){
			System.out.println("Document already indexed... exiting" );
			System.exit(0);
		}
		
		loadLexicon();
		loadInvertIndex();
		
		// Add terms to index
		for (String term : currentStemmedTokens){ 
			//Checks if the term was already in the lexicon
			if(!lexicon.containsValue(term)){ 
				addTermtoLexicon(term);
				if(printing){ 
					System.out.println("Indexing... " + term);
				}
			}else{
				updateOccurrences(term);
			}
		} 
		
		writeLexicon();
		writeInvertIndex();
		
	}	
	
	/* A main method for testing */
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Probando Indexer...");

	
	}
	
}
