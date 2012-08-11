import java.io.IOException;

/**
 * Main File System
 * @author Victoriano Izquierdo
 * @student 3395032
 * @course Information Retrieval
 * @assignment 1
 */

public class index {
		
	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException {
		
		Parser myParser = new Parser();
		
		/* Get optional parameter from args[] */
		myParser.getopts(args);
		myParser.printparam();
		
		/* Extract the ID of the Doc and save it to the map file */
		myParser.findid();
		myParser.printdocid();
		myParser.writemap();
		
		
		/* Retrieve tokens of the Doc and steam them */
		myParser.readtext();
		myParser.printtokens();
		
		//myParser.stemmtext();
		
		MyStemmer test = new MyStemmer();
		boolean say = test.ends("ponies", "ies");
		String say2 = test.exec("sing");
		System.out.println("Palabras iguales= " + say);
		System.out.println("Nueva palabra: " + say2);
		
				
		
	}

}
