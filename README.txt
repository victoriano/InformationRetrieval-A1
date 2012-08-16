- README -
Victoriano Izquierdo
Student Nº 3395032

** For Indexing:

javac index.java
java index p collection/nytimes.txt
java index p /public/courses/MultimediaInfoRetrieval/2012/a1/collection

** For Querying

javac search.java
java search lexicon.txt indexinvert.txt map.txt singing car cats abuelo people


-------------------------------------------
*map.txt { 1 APW19999924.0019 }
		 { 2 BER6949424.63    }


*lexicon.txt { 1 what }
			 { 2 you  }
			 { 3 want }


*invlist.txt  <term nº>  <total docs with that term> <nº of doc, repititions> 
			
			{ 1          3   5 10  4 5  3 4}	
			{ 2          2   3 4   4 1  3 1}	
			{ 3          9   1 1   5 7  8 9}				   


./index [-p] <sourcefile>

----------------------------------------------------------