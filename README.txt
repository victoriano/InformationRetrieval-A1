- README -

* Information Retrieval - RMIT University - Melbourne, Australia 2012 - *

Assignment 2
by Victoriano Izquierdo Ramirez
Student Nº 3395032


//Tested instruction for compiling and running the program in Yallara


** Indexing:

Compiling: javac index.java

Running:   java index p collection/nytimes.txt
		   java index p /public/courses/MultimediaInfoRetrieval/2012/a1/collection


** Querying

Compiling: javac search.java

---> For a simple "Ranked Search"
Running:   java search -cosine -q N51 -n 4 lexicon.txt indexinvert.txt map.txt the real madrid best team 

---> For "Novelty detection" search
Running:   java search -cosine -lambda 0.5 -q N51 -n 4 lexicon.txt indexinvert.txt map.txt -R 3 the real madrid best team