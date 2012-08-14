import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	List<String> stemmtokens = new ArrayList<String>();
	HashMap<Integer, String> lexicon = new HashMap<Integer, String>();
	HashMap<Integer, Word> indexinvert = new HashMap<Integer, Word>();
	
	
	/* Basic constructor */
	public Indexer(){
		this.docnum = Parser.mydocnumber;
		this.stemmtokens = Parser.stemmtokens;		
	}
	
	
	/* Load lexicon from disk into a HashMap<Integer, String> */
	public void loadLexicon (){
		
		
	}
	
	/*Load invert index from disk into a HashMap<Integer, Word> */
	public void loadInvertIndex (){
		
		
	}
	
	public boolean isTermInLexicon(String term){
		//If it is, go to index and update both: global and reference
		return true;
	}
	
	public void addTermtoLexicon(String term){
		//Add it to both Lexicon and index
	}
	
	
	public void exec(){
	//Firer of Instructions on the indexer 
	}
	
	
	/* 6 - public int saveindex( List mytokens ){}
	
	Open the file lexicon.tx
	save all occurrences in a Arraylist mylexicon 

	Open the file invlist.txt
	savel all occurrences in Hastable myinvlist

	Iterate over the list
	a look if it is in the Hastable mylexicon
	If it is present access the Hastable myinvlist
	increment the counter total
	*/

	
}
