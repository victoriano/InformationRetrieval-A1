import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Class creating for handling the Novelty Detection
 * @author Victoriano Izquierdo
 * @student 3395032
 * @course Information Retrieval
 * @assignment 2
 */

public class RankedDoc {
	
	int DOC;
	double similarity;
	double DDmaxDifference;
	double novelty;
	
	static ArrayList<RankedDoc> R = new ArrayList<RankedDoc>();
	static HashMap<Integer, RankedDoc> S = new HashMap<Integer, RankedDoc>();
	public static NavigableMap<Double, String> lminheap = new TreeMap<Double, String>();
	
	/* Initialize a new RankedDoc*/
	public RankedDoc (int DOC, double similarity){
		this.DOC = DOC;
		this.similarity = similarity;
		this.DDmaxDifference = 0;
		this.novelty = 0;

	}
	
	/* Initialize manually S set */
	public static void initializeS (){
		//Including DOC 1
		S.put(2,new RankedDoc(2, 0.0));
	}
	
	
	/* 
	 * Initialize the R set 
	 * with the TOP -R values
	 * found in the first ranking search
	 *  
	 * */
	
	@SuppressWarnings("unchecked")
	public static void createR (int size){
		
		RankedDoc.initializeS();
		int i = 0;
		
		Iterator it = Accumulator.nminheap.entrySet().iterator();
		int Tsize = Accumulator.nminheap.size();		
		
		while (i< size && i < Tsize) {
			Map.Entry e = (Map.Entry) it.next();
			
			double  simValue = Double.parseDouble(e.getKey().toString());
			int doc = Integer.parseInt(e.getValue().toString());
			RankedDoc rdoc = new RankedDoc(doc, simValue);
			R.add(i, rdoc);
			
			++i;
		}
		
	}
	
	/* 
	 * We compare each DOC in R with the other DOCs, 
	 * n*n comparison and store the maximum similarity 
	 * in RamkedDoc.DDmaxDifference field associated to 
	 * that DOC tha belongs to R
	 * 
	 * */
	
	public static void computeNovelty(double lambda) throws NumberFormatException, FileNotFoundException{
			 
		for (int i = 0; i<R.size(); i++) {
			 
			double temporalMaxSim2 = 0;
			 double wdi = Double.parseDouble(Weights.readMapWeight(R.get(i).DOC));
		   
			 for(int j = 0; j<R.size(); j++){
			   
			   /* We ignore to alter the values of DOCs contained in the S set */
			   if(!S.containsKey(R.get(i).DOC)){
				   
				   	/* We avoid to compare the DOC with itself in each iteration */
					if(R.get(i).DOC != R.get(j).DOC){
						
						double wdj = Double.parseDouble(Weights.readMapWeight(R.get(j).DOC));
						
						//System.out.println( "Comp DOC " + R.get(i).DOC + " with " + wdi + " vs " 
						//+ R.get(j).DOC + " DOC with " + wdj + " DOC");
							
						/* Debated Similarity formula to compute sim2(Di,Dj) */
						
						//double sim2 = (wdi*(wdj*Math.random()));						
						//double sim2 = (wdi*wdj)/wdj;
						double sim2 = wdi-wdj;
						
						//System.out.println("Docs similiarity is " +  sim2);
						
						/* Keeping the highest similarity score in RankedDoc.DDmaxDifference */
						if(sim2 > temporalMaxSim2){
							R.get(i).DDmaxDifference = sim2;
							temporalMaxSim2 = sim2;
						}
						
					}
					
				}else{
					/* Case this DOC is part of the S set is kept intact*/
					R.get(i).DDmaxDifference = 0;
					//System.out.println( S.get(i).DOC + " est‡ en S " );
				}
			 			
	         }
		}
		
		//for(RankedDoc a : R)
		//System.out.println("Final Sim2 of DOC " + a.DOC + " " + a.DDmaxDifference);
		
		//Computing final novelty value for each
		for(RankedDoc i : R)
			i.novelty = (lambda * i.similarity) - ((1- lambda) * i.DDmaxDifference);
	}
	
	
	public static void addtoMinheap() {

		for(RankedDoc i : R){
			lminheap.put(i.novelty, Integer.toString(i.DOC));
		}

		lminheap =  lminheap.descendingMap(); 
	}
	
	@SuppressWarnings("unchecked")
	public static void printTopResults(int numResults, String queryLabel) throws FileNotFoundException {
			
		int i = 0;
		
		Iterator it = lminheap.entrySet().iterator();
		int size = lminheap.size();
		
		System.out.println("------ Novelty Detection Search ------");
		System.out.println();
		
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
	
	

}
