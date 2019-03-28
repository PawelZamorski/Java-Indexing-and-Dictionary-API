
########## INDEXER ############
version 1.1

### OVERWIEV ###

The INDEXER is a command line application that allows a word index to be created from an e-book or URL.
Program parser a specified e-book and displays indices containing words, words definitions and pages words appeared on to the console. It is alse possible to output the data to the file.

### AUTHOR ###
Pawel Zamorski, G00364553@gmit.ie

### INSTALL ###

The programm uses command prompt.
To run the program go to the directory containing indexer.jar file and use the following command:

	java -cp .\indexer.jar ie.gmit.dip.Runner

### GETTING STARTED ###

Once the program is lunched the menu is displayed to the console. It is a multi-level menu. The menu tree is presented below:

--- MAIN MENU ---
(1) Index the file
(2) Index the URL
(3) Display indexed words
(4) More options (process data)
	--- MORE OPTIONS MENU ---
	(1) Sort indices in alphanumeric ascending order
	(2) Sort indices in alphanumeric descending order
	(3) Sort indices by frequency of words in ascending order
	(4) Sort indices by frequency of words in descending order
	(5) Search indices by word/-s
	(6) Search indices by word pattern
	(7) Search indices by frequency
	(8) Search x number of the most frequent indices
	(9) Search x number of the less frequent indices
	(10) Get total number of indices (unique words)
	(11) Get list of words in ascending order
	(12) Get list of words in descending order
	(13) Go back	
(5) Quit

A successful operation from MORE OPTIONS MENU displays the result to the console.

All the operations are case insensitive.

The program uses a default dictionary and a default ignore words. It is supposed that both files are stored in the current directory and accessible as ./stopwords.txt and ./dictionary.csv. 

Parse method in QueryParser class uses Threads.