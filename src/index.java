import java.io.FileNotFoundException;

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
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
				
		Parser myParser = new Parser();
		myParser.getopts(args);
		
		/* Test Unit - Getopts  */
		String thepath = myParser.inputpath;
		boolean pflag = myParser.p;
		System.out.println(pflag);
		System.out.println(thepath);
		
		myParser.findid();

		
		
		

	}

}
