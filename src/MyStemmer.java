
public class MyStemmer {
	
	/* Checks if a String has a given termination end */
	public boolean ends(String thestring, String end){
		
		//Convert them to Arrays of Chars
		char[] word = thestring.toCharArray();
		char[] ending = end.toCharArray();
		int lend = ending.length;
		boolean equal = false;
		
		for(int i=0;i<lend;i++){
		 if(word[word.length-lend+i] == ending[i]){	
			 equal = true;
		 }else{
			 equal = false;
			 return equal;
		  }
		}
		
		return true;
	 
	}
	
	/* It removes the last nth elements of an array */
	public static char[] removeElement(char[] original, int nless){
		int nlenght = original.length - nless ;
		char[] n = new char[nlenght]; 
	    System.arraycopy(original, 0, n, 0, nlenght);
	    return n;
	}
	
	/* 
	 * Main function for the class
	 * following Porter 1 rules + ing and ed endings
	 */
	
	public String exec (String mystring){
		
		char[] stringch = mystring.toCharArray();
		char ch = stringch[0];
		if (Character.isLetter(ch)){
			
			if(ends(mystring, "sses") || ends(mystring, "ies") ){
			return	String.valueOf(removeElement(stringch, 2));
			}
			
			if(ends(mystring, "s")){
		    return String.valueOf(removeElement(stringch, 1));
			}
			
			if(ends(mystring, "ing") && stringch.length>4 ){
			return String.valueOf(removeElement(stringch, 3));
			}
			
			if(ends(mystring, "ed") && stringch.length>3 ){
			return String.valueOf(removeElement(stringch, 2));
			} 		
		   
		}
		
		return mystring;
		
	}
}
