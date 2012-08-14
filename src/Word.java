import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Word {
	
	String term;
	int occurrences;
	HashMap<Integer, Integer> doc_occurrences = new HashMap<Integer, Integer>(); 
	
	
	/* Initialize a new Word */
	public Word (String term, int doc){
		this.term = term;
		this.occurrences = 1;
		this.doc_occurrences.put(doc, 1);
	}
	
	
	/* Initialize a new word from a "String" line read from disk*/
	public Word (String term, String rawWord){
		this.term = term;
		//Convert rawWord to Array of Strings
		int j = 1;
		String [] word = rawWord.split("\\s+");
		this.occurrences= Integer.parseInt(word[0]);
		//Adding doc/occurs to HashTable
		while(j<word.length){
		System.out.println("Doc: " + word[j] + " Occu:" + word[j+1]);
		this.doc_occurrences.put(Integer.parseInt(word[j]), Integer.parseInt(word[j+1]));	
		j = j+2;
		}		

	}
	
	/*Returns how many occurrence of this word are in a given DOC */
	public int occurrencesInDoc(int doc){
	return this.doc_occurrences.containsKey(doc) ? this.doc_occurrences.get(doc) : 0;
	}
		
	/*Add an occurrence for a given DOC */
	public void addOccurrenceToDoc(int doc){
		++this.occurrences;
		int count = occurrencesInDoc(doc);
		this.doc_occurrences.put(doc, count+1);		
	}
	
	/*Returns number of occurrences of this word in a given DOC*/
	public int getDocOccurrences(int doc){
		return this.doc_occurrences.get(doc);
	}
	
	/* Print/Writes the word info serialized */
	@SuppressWarnings("unchecked")
	public void printmap (){
		System.out.print(this.occurrences + " ");
		Iterator it = doc_occurrences.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry e = (Map.Entry)it.next();
		System.out.print(e.getKey() + " " + e.getValue() + " ");
		}
		System.out.println();
	}
	
	@SuppressWarnings("unchecked")
	/*Form a serialized String info of this word */
	public String serializeWord (){
		String swordA = this.occurrences + " ";
		String swordB = "";
		Iterator it = doc_occurrences.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry e = (Map.Entry)it.next();
		swordB = swordB + e.getKey() + " " + e.getValue() + " ";
		}
		String sword = swordA + swordB;
		return sword ;
	}

	
	public static void main(String[] args) throws IOException {
		
		/* Word myword = new Word("hola", 3);
		myword.addOccurrenceToDoc(3);
		myword.addOccurrenceToDoc(1);
		
		String terms = myword.term;
		int n3 = myword.getDocOccurrences(3);
		int n1 = myword.getDocOccurrences(1);
		int total = myword.occurrences;
		myword.printmap();
		String finalw = myword.serializeWord();
		System.out.println(finalw);
		
		System.out.println("A–adido " + terms + " tantas " + n3 + " veces. Global Ocurrences " + total );
		System.out.println("Ocurrences in Doc 1 ->" + n1); */
		
		Word myword = new Word("hola", "5 3 8 1 7");
		System.out.println("Added: " + myword.term + " | Ocurrences: " + myword.occurrences );
		int n3 = myword.getDocOccurrences(1);
		String terms = myword.term;
		int total = myword.occurrences;
		System.out.println("A–adido " + terms + " tantas " + n3 + " veces. Global Ocurrences " + total );
	} 

}
