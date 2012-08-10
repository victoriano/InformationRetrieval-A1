import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;


public class Parser {
	
	boolean p; 
	String inputpath;
	String mydocid;
	
	
	/* Simplest getopts() ever, it does not support
	 * other initialization than a single input file 
	 * parameter or 2 with a print flag option in the first place
	 */
	public void getopts( String[] args ){
		
		if(args.length == 0){
			System.out.println("You forgot to " +
				"introduce an input path, exiting...");
			System.exit(0);
		}
		if(args.length == 1){
			inputpath = args[0];
			p = false;
		}else if(args.length == 2){
			inputpath = args[1];
			p = true;
		}
		
	}
	
	
	/* Open the doc file, find <DOCNO> 
	 * and store the ID in the variable String mydocid 
	 */
	
	public void findid() throws FileNotFoundException{
		
		File file = new File(inputpath);
		Scanner input = new Scanner(file);

		while(input.hasNext()) {
			
			String currentToken = input.next();
			//System.out.println("This is the token" + " " + currentToken );
		    
		    if(currentToken.equals("<DOCNO>")){
		    	mydocid = input.next();	
		    }
		    
		}
		
		System.out.println("The DOC ID is:" + " " + mydocid );
		input.close();	
	}	
	
	/* I don't check whether this doc has been 
	 * already mapped or not
	 * just include it to end of the map adding
	 * the corresponding number
	 * 
	 */
	
	public void writemap() throws IOException{
		
		File mapfile = new File("map.txt");
		Scanner map = new Scanner(mapfile);
		int i = 0;
		ArrayList<String> lines = new ArrayList<String>();
		
		// Reading and storing current info in the map file
		while(map.hasNextLine()) {
			
			String currentline = map.nextLine();
			lines.add(currentline);
			++i;
			System.out.println("Reading: " + currentline + " number" + i);	
		}	
		
		// Writing back to map file using the Arraylist + the new information
		BufferedWriter file = new BufferedWriter(new FileWriter("map.txt"));
		ListIterator<String> linesIterator = lines.listIterator();
		
		//Getting info from the Arraylist
		while(linesIterator.hasNext()){
		String cline = linesIterator.next();
		System.out.println(cline);
		file.write(cline);
		file.write("\n");
		}
		
		// New Information
		String lastline = i+1 + " " + mydocid ;
		file.write(lastline);
		
		map.close();
		file.flush();
		file.close();
	}
	
	
	/* 4 - public void readfile( String inputpath ){}
	Open the file, find <TEXT> 
	and read in a global buffer String mytext everything
	until it reaches </TEXT> */
	

}
