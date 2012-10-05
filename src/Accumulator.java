import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;


public class Accumulator {
	
	/* The Hashtable where Accumulators for each DOC are stored */
	public static HashMap<Integer, Double> theAccumulator = new HashMap<Integer, Double>();
	
	/* The Tree storing in a sorted way the values */
	public static TreeMap<Double, String> minheap = new TreeMap<Double, String>();
	public static NavigableMap<Double, String> nminheap = new TreeMap<Double, String>();
	
	public static double computeAD ( int N, int ft, int fdt ){
		
		double Nd = (double)	N;
		double ftd = (double) ft;
		double fdtd = (double) fdt;
		
		return Math.log(1+(Nd/ftd)) * (1 + Math.log(fdtd));
	}
	
	/* Once all partial accumulators have been calculated we divided them by Wd */
	@SuppressWarnings("unchecked")
	public static void computeFinalAD() throws NumberFormatException, FileNotFoundException{
		
		Iterator it = theAccumulator.entrySet().iterator();

		while (it.hasNext()) {
			
			Map.Entry e = (Map.Entry) it.next();
			int  thedoc = Integer.parseInt( e.getKey().toString());
			double wd = Double.parseDouble(Weights.readMapWeight(thedoc));
			double accValue = Double.parseDouble(e.getValue().toString());
			double finalValue = (accValue/wd);
			theAccumulator.put(thedoc, finalValue);
		}
		
		
	}
	
	public static void addAccu(int docNum, double computedAccumulation){
		
		Double actualValue = theAccumulator.get(docNum);
		
		if(actualValue == null){
			theAccumulator.put(docNum, computedAccumulation);
		}else{
			theAccumulator.put(docNum, actualValue + computedAccumulation);
		} 
	
	}
	

	
	/* Simple utility function for counting the total number of DOC indexed in map.tx */
	public static int getN() throws FileNotFoundException{
		
		int total = 0;

		File mapfile = new File("map.txt");	
		Scanner map = new Scanner(mapfile);

		while(map.hasNextLine()) {	
          map.nextLine();
          ++total;
		}
		
		map.close();
		return total;
		
	}
	
	public static void printAccumulations() {

		Object[] mykeys = theAccumulator.keySet().toArray();
		Object[] myvalues = theAccumulator.values().toArray();

		for (int i = 0; i < mykeys.length; ++i) {
			System.out.println("DOC: " + mykeys[i] + " Cosine Similarity for Query is: " + myvalues[i]);
		}

	}
	
	public static void addtoMinheap() {

		Object[] mykeys = theAccumulator.keySet().toArray();
		Object[] myvalues = theAccumulator.values().toArray();

		for (int i = 0; i < mykeys.length; ++i) {
			
			//System.out.println("Adding DOC: " + mykeys[i] + " with similarity " + myvalues[i]);
			minheap.put(Double.parseDouble(myvalues[i].toString()), mykeys[i].toString());
			
		}

		nminheap =  minheap.descendingMap(); 
		//System.out.println(nminheap);

	}
	
	@SuppressWarnings("unchecked")
	public static void printTopResults(int numResults, String queryLabel) throws FileNotFoundException {
			
		int i = 0;
		
		Iterator it = nminheap.entrySet().iterator();
		int size = nminheap.size();
				
		while (i< numResults && i < size) {
						
			Map.Entry e = (Map.Entry) it.next();
			
			String  simValue = e.getKey().toString();
			
			int doc = Integer.parseInt(e.getValue().toString());
			String docID = Parser.getDocID(doc);
			
			// N51 LA010189-0003 1 110.541 format
			int newi = i+1;
			System.out.println(queryLabel + " " + docID + " " + newi + " " + simValue);
			++i;
		}
		
		System.out.println();
	}
	
	 
	/**
	 * Main for testing Accumulator structure and behaviour
	 * @throws FileNotFoundException 
	 */
	
	public static void main(String[] args) throws FileNotFoundException {
				
		double a = Accumulator.computeAD(8, 3, 2);
		System.out.println(a);
		
		int N = Accumulator.getN();
		System.out.println(N);


	}

}
