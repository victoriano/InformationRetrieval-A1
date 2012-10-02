import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


/**
 * Weights Class
 * @author Victoriano Izquierdo
 * @student 3395032
 * @course Information Retrieval
 * @assignment 2
 */


public class Weights {

	/* Final Hash table with the final weights for each document */
	public static HashMap<Integer, Double> realWeights = new HashMap<Integer, Double>();


	public static void printWeights() {

		Object[] mykeys = realWeights.keySet().toArray();
		Object[] myvalues = realWeights.values().toArray();

		for (int i = 0; i < mykeys.length; ++i) {
			System.out.println("DOC: " + mykeys[i] + " peso: " + myvalues[i]);
		}

	}

	/* Utility function for computing the final similarity value of a DOC respect to a query */
	public static String readMapWeight(int doc) throws FileNotFoundException {

		String foundWeight = "";

		// Load Map to Memory in the HashTable
		HashMap<Integer, String> virtualMap = new HashMap<Integer, String>();

		boolean exists = (new File("map.txt")).exists();

		if (exists) {
			
			File mapfile = new File("map.txt");
			Scanner map = new Scanner(mapfile);
			
			// Reading and storing in a HashMap current info of the map file
			while (map.hasNextLine()) {
				String currentline = map.nextLine();
				String[] theline = currentline.split("\\s+");
				int cdocnumber = Integer.parseInt(theline[0]);
				// If this is the DOC number return ID associated
				if (cdocnumber == doc) {
					foundWeight = theline[2];
				}
				;
				// System.out.println("El mydocnumber= " + mydocnumber);
				virtualMap.put(cdocnumber, theline[1]);
			}
			map.close();
			// Get and return Value of the Key "doc"
			System.out.println("The weight is" + " " + foundWeight );
			return foundWeight;
			
		} else {
			System.out.println("Run indexation before retrieval, map.txt not found");
					
		}
		return "nothing";

	}

	/* Rewrite the map with the weights computed */
	@SuppressWarnings("unchecked")
	public static void rewritemap() throws IOException {

		boolean exists = (new File("map.txt")).exists();

		int totaldocs = 0;
		HashMap<Integer, String> virtualMap = new HashMap<Integer, String>();

		// If the map.txt exits just load it into the virtualMap
		if (exists) {

			File mapfile = new File("map.txt");

			Scanner map = new Scanner(mapfile);
			// Reading and storing in a HashMap current info of the map file
			while (map.hasNextLine()) {
				String currentline = map.nextLine();
				String[] theline = currentline.split("\\s+");
				int cdocnumber = Integer.parseInt(theline[0]);

				// System.out.println("El mydocnumber= " + mydocnumber);
				virtualMap.put(cdocnumber, theline[1]);
				++totaldocs;

			}
			map.close();

			// Now save it to Disk plus the weights of each DOC

			Iterator it = virtualMap.entrySet().iterator();
			BufferedWriter file = new BufferedWriter(new FileWriter("map.txt"));
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				double x = realWeights.get(e.getKey());
				String writingline = e.getKey() + " " + e.getValue() + " " + x;
				file.write(writingline);
				file.write("\n");
			}
			file.flush();
			file.close();

		} else {
			// If map.txt does no exits something went wrong indexing
			System.out.println("Something went wrong when indexing");
		}

	}

	/* A main method for testing */

	public static void main(String[] args) throws IOException {

		System.out.println("Weights...");
		Weights.readMapWeight(2);

	}

}
