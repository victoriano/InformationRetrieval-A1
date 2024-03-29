import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Word Class for the Index
 * @author Victoriano Izquierdo
 * @student 3395032
 * @course Information Retrieval
 * @assignment 1
 */

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
	public Word (String theword, String rawWord){
		//Convert rawWord to Array of Strings
		this.term = theword;
		int j = 1;
		String [] word = rawWord.split("\\s+");
		this.occurrences= Integer.parseInt(word[0]);
		//Adding doc/occurs to HashTable
		while(j<word.length){
		//System.out.println("Doc: " + word[j] + " Occu:" + word[j+1]);
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
	
	/*Returns Array of documents id's of this word*/
	@SuppressWarnings("unchecked")
	public ArrayList<Integer> presentDocs(){
		ArrayList<Integer> docs = new ArrayList<Integer>();
		int i = 0;
		Iterator it = doc_occurrences.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry e = (Map.Entry)it.next();
		docs.add(Integer.parseInt(e.getKey().toString()));
		++i;
		}
		
		return docs;
		
	}
	
	/* ** Utility function for Search printing Occurrences of a Word along its Doc ID ** */
	
	public void retrievePrintDocs() throws FileNotFoundException{
		ArrayList<Integer> docs = this.presentDocs();
		for(int doc : docs){
			String docid = Parser.getDocID(doc); 
			System.out.print(docid);
			System.out.print( " " + this.getDocOccurrences(doc));	
			System.out.println();
		}		
		
	}
	

	
	/* ** Utility function for new Ranked Search, returns Fdt of a particular DOC for this Word ** */
	
	public int retrieveFDTDocs(int thedoc) throws FileNotFoundException{
		int fdt = 0;
		ArrayList<Integer> docs = this.presentDocs();
		for(int doc : docs){
			if(doc == thedoc){
				fdt = this.getDocOccurrences(doc);
				//System.out.println( "El Doc " + thedoc + " tiene " + this.getDocOccurrences(doc) + " repeticiones de " + this.term);				
			}	
		
		}
		
		return fdt;
	}
	
	/* ** Main Utility function for new Ranked Search, returns an array of DOCs where there is a instance of this Word ** */
	
	public void computeTotalAD() throws FileNotFoundException{
		int N = Accumulator.getN();
		int ft = occurrences;
		//System.out.println("Total Occurrences: " + ft);
		
		ArrayList<Integer> docs = this.presentDocs();
		for(int doc : docs){			
			int fdt = retrieveFDTDocs(doc);
			//System.out.println("The DOC: " + doc + " has " + fdt + " repetitions of " + this.term);
			double computedAccumulation = Accumulator.computeAD ( N, ft, fdt);
			//System.out.println("Computed Accumulation is " + computedAccumulation);
			Accumulator.addAccu(doc, computedAccumulation);		
		}	
		
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
		
		/* Some simple Unit Tests for this Word class */
		
		Word myword = new Word("hola", "15 3 8 1 7");
		System.out.println("Added Word Term: " + myword.term + " | Total Ocurrences: " + myword.occurrences );
		System.out.println("Serialized Word added: " + myword.serializeWord() );
		int ndoc1 = myword.getDocOccurrences(1);
		System.out.println("Ocurrences of the word in DOC 1 = " + ndoc1 );
		ArrayList<Integer> vals = myword.presentDocs(); 
		System.out.print("This word is present in Docs: ");
		for (int v : vals){ System.out.print(v + " ");}
		
	} 

}
