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
	public Indexer( int mydocnum, List<String> tokens){
		this.docnum = mydocnum;
		this.stemmtokens = tokens;		
	}
	
	/* Load lexicon from disk into a Map */
	public void loadlexicon (){
		
		
	}
	
	/*Add word to index */
	// public void addWordToIndex(Word word){}
	
	
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
	
	
	
	/* Check if a token is already in the lexicon 
	 * returns the position in the lexicon or 0 if is not
	 * */
	public int isinlexicon(){
		
	return 0;
	}
	
	/* If it's a new word we add it at the end of the lexicon */
	public void addtolexicon( String toke ){
		
		
	}
	
	/*If it's an existing word we look for it in the lexicon  */

	
}
