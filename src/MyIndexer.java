import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyIndexer {
	
	String lexicon_path = "lexicon.txt";
	String indexinvert_path = "indexinvert.txt";
	
	List<String> stemmtokens = new ArrayList<String>();
	HashMap lexicon = new HashMap();
	
	HashMap<Integer, ArrayList> indexinvert = new HashMap<Integer, ArrayList>();
	ArrayList index_content = new ArrayList();
	HashMap<Integer, Integer> index_occurrences = new HashMap<Integer, Integer>();
	
	

	
	/* Basic constructor */
	public MyIndexer(List<String> tokens){
		this.stemmtokens = tokens;
	}
	
	/* Load lexicon from disk into a Map */
	public void loadlexicon (){
		
		
	}
	
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
