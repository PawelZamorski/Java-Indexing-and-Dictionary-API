package ie.gmit.dip;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The <code>Menu</code> class provides a console menu for the User.
 * <p>
 * When the program is run as a console program, the Menu object should be instantiated in the <code>main</code> method.
 * Then the start() method should be invoked.
 */
public class Menu {
	private boolean keepRunning = true;
	private Input input;
	private Indexable indexer;
	
	/**
	 * Creates a new Menu object that is ready to control the console menu.
	 */
	public Menu() {
		super();
		input = new Input();
		indexer = new Index();
	}
	
    /**
     * Displays the main menu.
     */
    public void start(){
        System.out.println("-----------------------------------");
        System.out.println("----------- Indexer v. 1.0 --------");
        System.out.println("-----------------------------------");
        
        // Use while loop to run it at least once.
        // Keep running the loop until variable 'keepRunning' is true. Change the value by invoking quit() method.
        while(keepRunning){
            System.out.println("###################################");
            System.out.println("------------ MAIN MENU ------------");
            System.out.println("Program uses the default dictionary.");
            System.out.println("Program uses the default list of ignored words.");
            System.out.println("-----------------------------------");
            System.out.println("(1) Index the file");
            System.out.println("(2) Index the URL");
            System.out.println("(3) Display indexed words");
            System.out.println("(4) More options (process data)");
            System.out.println("(5) Quit");
            // Get the input from the User, validate and parse it to integer using 'selectOption' method
            int option = input.selectOption(1, 5);
            switch(option){
                case 1:
                    fileURLIndexer(false);
                    break;
                case 2:
                    fileURLIndexer(true);
                    break;
                case 3:
                	if(indexer.getIndices().size() > 0) {
                    	displayMap(indexer, indexer.getHeadingsSetAsc());
    	                input.pressEnter("Process successfully completed :)");
                	}else {
        	            input.pressEnter("There are no indexed words");
                	}
                    break;
                case 4:
                	if(indexer.getIndices().size() > 0) {
                    	moreOptionsMenu();
                	}else {
        	            input.pressEnter("There are no indexed words");
                	}
                    break;
                case 5:
                    // 'quit' method changes the 'keepRunning' variable to false. The loop ends and the program quits.
                    quit();
                    break;
                default:
                    // Invoke the below method when there is no such option for selected one.
                    // It may occur when the range of options ('min' and 'max' argument) in 'selectOption' method was set up incorrectly
                    
                	// displayIncorrectOption(option);
                    quit();
                    break;
            }
        }
    }
        
    private void fileURLIndexer(boolean isURL) { // Number of lines in a book per page????? Use as parameter
        // Clear 'encryptedText' variable from any previously encrypted text
    	
    	indexer = null;
        System.out.println("Any previously indexed text has been removed.");
        System.out.println("Insert the path of the file to be encrypted >");
        String path = input.inputString();
        System.out.println("Indexing during process... It will take a while to complete...");
    	
		try {
			IgnoreWordsParser ignoreWordsParser = new IgnoreWordsParser();
			ignoreWordsParser.parse(ignoreWordsParser.openBufferReader("./stopwords.txt", false));
			Set<String> ignoreWords = ignoreWordsParser.getIgnoreWords();
			
			DictionaryParser dictionaryParser = new DictionaryParser();
			dictionaryParser.parse(ignoreWordsParser.openBufferReader("./dictionary.csv", false));
			Map<String, List<WordDetails>> dictionary = dictionaryParser.getDictionary();
			
        	QueryParser indexingParser = new QueryParser(ignoreWords, dictionary);
        	if(isURL == false) {
        		indexingParser.parse(ignoreWordsParser.openBufferReader(path, false)); // takes local file
        	}else {
        		indexingParser.parse(ignoreWordsParser.openBufferReader(path, true)); // takes URL
        	}
        	indexer = indexingParser.getIndexer();
        	if(indexer.getIndices().size() > 0) {
                input.pressEnter("Process successfully completed :)");
        	}else {
	            input.pressEnter("There are no word in the file to be indexed");        		
        	}
        }catch(FileNotFoundException fnfEx){
            System.out.println(fnfEx.getMessage());
            fnfEx.printStackTrace();
            input.pressEnter("Please, make sure the inserted path of the file is valid");
        }catch(IOException ioEx){
            System.out.println(ioEx.getMessage());
            ioEx.printStackTrace();
            input.pressEnter("I/O Exception");  
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            input.pressEnter("Uppsss... something went wrong :(\n Please, try again");
        }
    }
    
    
    /*
     * Quits the program by assigning false value to the keepRunning global variable
     */
    private void quit(){
        System.out.println("Thank you for using 'An Indexer'. Bye bye!");
        keepRunning = false;
    }

    /*
     * More options menu for further processing of data.
     */
    private void moreOptionsMenu() {
    	List<String> headings = null;

    	boolean run = true;
        while(run){
            // Display menu to the console for the User
	    	System.out.println("###################################");
	        System.out.println("------------ MORE OPTIONS MENU ------------");
	        System.out.println("-----------------------------------");
	        System.out.println("(1) Sort indices in alphanumeric ascending order");
	        System.out.println("(2) Sort indices in alphanumeric descending order");
	        System.out.println("(3) Sort indices by frequency of words in ascending order");
	        System.out.println("(4) Sort indices by frequency of words in descending order");
	        System.out.println("(5) Search indices by word/-s");
	        System.out.println("(6) Search indices by word pattern"); // Starting with, containing
	        System.out.println("(7) Search indices by frequency");	        
	        System.out.println("(8) Search x number of the most frequent indices");
	        System.out.println("(9) Search x number of the less frequent indices");
	        System.out.println("(10) Get total number of indices (unique words)");
	        System.out.println("(11) Get list of words in ascending order");
	        System.out.println("(12) Get list of words in descending order");
	        System.out.println("(13) Go back");
	        
	        // Get the input from the User, validate and parse it to integer using 'selectOption' method
	        int option = input.selectOption(1, 13);
	        switch(option){
	            case 1:
	            	displayMap(indexer, indexer.getHeadingsSetAsc());
	                input.pressEnter("Process successfully completed :)");
	                break;
	            case 2:
	            	displayMap(indexer, indexer.getHeadingsSetDesc());
	                input.pressEnter("Process successfully completed :)");
	            	break;
	            case 3:
	            	headings = indexer.sortFrequency(true);
	                displayMap(indexer, headings); // Save or display or go back
	            	input.pressEnter("Process successfully completed :)");

	            	headings = null;
	            	break;
	            case 4:
	            	headings = indexer.sortFrequency(false);
	                displayMap(indexer, headings); // Save or display or go back
	            	input.pressEnter("Process successfully completed :)");

	                headings = null;
	            	break;
	            case 5:
	            	System.out.println("Insert word/-s to be searched. Words must be seperated by space. All non-word characters are removed.");

	            	headings = indexer.getWord(input.inputWords());
	            	if(headings.size() > 0) {
		                displayMap(indexer, headings); // Save or display or go back
	            		input.pressEnter(headings.size() + " words has been found.");
	            	}else {
		            	input.pressEnter("No words has been found.");
	            	}
	            	
	            	headings = null;
	            	break;
	            case 6:
	            	System.out.println("Insert regex to search word/-s. Use '.' to replace any character, '.*' to replace many characters, e.g."
	            			+ "\n to search all words starting with 'a' insert 'a.*'"
	            			+ "\n  to search all words ending with 'a' insert '.*a'"
	            			+ "\n  to search all words having 'a' insert '.*a.*'");
	            	String regex = input.inputString();
	            	headings = indexer.getWordsWithRegex(regex);
	            	if(headings.size() > 0) {
	            		displayMap(indexer, headings); // Save or display or go back	            		
	            		input.pressEnter(headings.size() + " words has been found.");
	            	}else {
		            	input.pressEnter("No words has been found.");
	            	}

	            	headings = null;
	                break;	                
	            case 7:
	            	System.out.println("Insert the frequency to search for a words with a given frequency.");
	            	int numberFreq = input.inputInt();
	            	if(numberFreq > 0) {
	            		headings =  indexer.getWordsGivenFrequency(numberFreq);
		            	displayMap(indexer, headings); // Save or display or go back	            	
	            		input.pressEnter(headings.size() + " words has been found.");	            		
	            	}else {
		            	input.pressEnter("Process failed :( Insert number greater than 0.");	            			            			            		
	            	}
	            	
	            	headings = null;
	            	break;
	            case 8:
	            	System.out.println("Insert the number of the most popular words you wish to be searched for.\n"
	            			+ "The number of searched words may be bigger that expected if there are words with the same frequency."); // check it
	            	int numberMost = input.inputInt();
	            	if(numberMost > 0) {
	            		headings = indexer.getXFrequent(numberMost, false);
		            	displayMap(indexer, headings); // Save or display or go back	            	
	            		input.pressEnter(headings.size() + " words has been found.");	            		
	            	}else {
		            	input.pressEnter("Process failed :( Insert number greater than 0.");	            			            			            		
	            	}
	            	
	            	headings = null;
	            	break;
	            case 9:
	            	System.out.println("Insert the number of the less popular words you wish to be searched for.\n"
	            			+ "The number of searched words may be bigger that expected if there are words with the same frequency."); // !!!!!!!!!!!!!!!!!!!check it
	            	int numberLess = input.inputInt();
	            	if(numberLess > 0) {
	            		headings = indexer.getXFrequent(numberLess, true);
		            	displayMap(indexer, headings); // Save or display or go back	            	
	            		input.pressEnter(headings.size() + " words has been found.");	            		
	            	}else {
		            	input.pressEnter("Process failed :( Insert number greater than 0.");	            			            			            		
	            	}
	            	
	            	headings = null;
	            	break;
	            case 10:
	            	System.out.println();
	            	input.pressEnter("The total number of unique words is " + indexer.indicesTotal() + "\nProcess successfully completed :)");
	            	break;
	            case 11:
	            	displaySet(indexer.getHeadingsSetAsc());
	                input.pressEnter("Process successfully completed :)");
	            	break;
	            case 12:
	            	displaySet(indexer.getHeadingsSetDesc());
	                input.pressEnter("Process successfully completed :)");
	            	break;
	            case 13:
	                run = false;
	                break;
	            default:
	                // Invoke the below method when there is no such option for selected one.
	                // It may occur when the range of options ('min' and 'max' argument) in 'selectOption' method was set up incorrectly
	                
	            	// displayIncorrectOption(option);
	                run = false;
	                break;
	        }        
        }
    }
    
    /*
     * Opens the menu with options for saving data to the file, displaying data to the console or go back to he previous menu.
     * 
     * @param indexable an object that is Indexable for further processing of data
     */
    private void displayMap(Indexable indexable, List<String> headings){
    	IndexOutput output = new IndexOutput();
    	output.printIndices(indexable, headings);
		System.out.println("----------------------------------\n");
    }
    
    /*
     * Opens the menu with options for saving data to the file, displaying data to the console or go back to he previous menu.
     * 
     * @param wordsSet a Set of String for further processing of data
     */
    private void displaySet(List<String> wordsSet){
    	IndexOutput output = new IndexOutput();
    	output.printWords(wordsSet);
		System.out.println("----------------------------------\n");
    }
}
