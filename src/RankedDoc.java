import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class RankedDoc {
	
	int DOC;
	double similarity;
	double DDmaxDifference;
	double novelty;
	
	static ArrayList<RankedDoc> R = new ArrayList<RankedDoc>();
	static HashMap<Integer, RankedDoc> S = new HashMap<Integer, RankedDoc>();
	
	/* Initialize a new RankedDoc*/
	public RankedDoc (int DOC, double similarity){
		this.DOC = DOC;
		this.similarity = similarity;
		this.DDmaxDifference = 0;
		this.novelty = 0;

	}
	
	/* Initialize manually S set */
	public static void initializeS (){
		S.put(1,new RankedDoc(1, 0.0));
	}
	
	
	/* Initialize the R set */
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
	 * in 
	 * 
	 * */
	
	public static void computeDDmaxDifference() throws NumberFormatException, FileNotFoundException{
			 
		for (int i = 0; i<R.size(); i++) {
			 
			double temporalMaxSim2 = 0;
			 double wdi = Double.parseDouble(Weights.readMapWeight(R.get(i).DOC));
			 System.out.println(" -------- ");
		   
			 for(int j = 0; j<R.size(); j++){
			   
			   //We ignore to alter the values within the S set
			   if(!S.containsKey(R.get(i).DOC)){
				   
				   	// So we don't compare with itself in each iteration
					if(R.get(i).DOC != R.get(j).DOC){
						double wdj = Double.parseDouble(Weights.readMapWeight(R.get(j).DOC));
						System.out.println( "Comp " + R.get(i).DOC + " with " + wdi + " DOC vs " + R.get(j).DOC + " with " + wdj + " DOC");
											
						//double sim2 = (wdi*(wdj*Math.random()));
						double sim2 = (wdi*wdj)/wdj;
						System.out.println("Docs similiarity is " +  sim2);
						if(sim2 > temporalMaxSim2){
							R.get(i).similarity = sim2;
							temporalMaxSim2 = sim2;
						}
						System.out.println("Maximum kept is " + temporalMaxSim2);
						System.out.println();
						
					}
					
				}else{
					System.out.println("est‡ en S " + S.get(i).DOC);
				}
			 
				
	        }
		}
	}
	
	
	/* Collections.sort(reportList, new Comparator<Report>() {

		@Override public int compare(final Report record1, final Report record2) {
		    int c;
		    c = record1.getReportKey().compareTo(record2.getReportKey());
		    if (c == 0)
		       c = record1.getStudentNumber().compareTo(record2.getStudentNumber());
		    if (c == 0)
		       c = record1.getSchool().compareTo(record2.getSchool());
		    return c;
		}

	}); */
	

	
	public static void main(String[] args) {
		
		/* double wdi = 2;
		double wdj = 3;
		double sim2 = (wdi*wdj)/wdj;
		
		System.out.println(sim2); */
		
		/* if(R.get(i).DOC == S.get(i).DOC && S.size()< i){
			System.out.println("este no se toca");
		} */
		
		//R.add(0,new RankedDoc(1, 0.0));
		//S.add(0,new RankedDoc(1, 0.0));
		
		//R.get(i).DOC == S.get(i).DOC
		
	}

}
