import java.io.IOException;

/**
 * Main File System
 */

/**
 * @author Victoriano Izquierdo
 *
 */
public class index {
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException {
				
		Parser myParser = new Parser();
		myParser.getopts(args);
		
		/* Test Unit - Getopts  
		String thepath = myParser.inputpath;
		boolean pflag = myParser.p;
		System.out.println(pflag);
		System.out.println(thepath); */
		
		myParser.findid();
		myParser.writemap();
		
		
		

	}

}
