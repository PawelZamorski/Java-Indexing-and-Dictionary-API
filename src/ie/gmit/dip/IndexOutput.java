package ie.gmit.dip;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The Output class is used for outputting indices with word definitions as a text to the console.
 * It has also method to output indexed words (5 per line).
 */
public class IndexOutput {

	/**
	 * Creates a new IndexerOutput object that is ready for processing data to a console or text file.
	 */
	public IndexOutput() {
		super();
	}

	/**
	 * Displays indexed words with word definitions and pages to the console.
	 * 
	 * @param indexer an object that is Indexable
	 * @param headings list of headings
	 * 
	 */
	public void printIndices(Indexable indexer, List<String>headings) {
		Map<String, Set<Integer>> indicesMap = indexer.getIndices();
		Map<String, List<WordDetails>> dictionaryMap = indexer.getWordDefinitions();
		
    	System.out.println("____________________________________________________________________________________________________");    	
    	System.out.printf("%1$-20s %2$s %3$-20s%n", "    Word", "|", "    Details");
    	System.out.println("____________________________________________________________________________________________________");    	

		for( String heading : headings) {
	    	System.out.printf("%1$-20s %2$s %3$-20s%n", "    " + capitailizeWord(heading), "|", "    Definitions:");

			Collection<WordDetails> wordsDet = dictionaryMap.get(heading);
			for(WordDetails wordDet : wordsDet) {
				String word = wordDet.getWord();
				String wordType = wordDet.getWordType();
				String[] wordDefinition = wordDet.getDefinition().split("\n");
				
				if(wordDefinition.length<2) {					
			    	System.out.printf("%1$-20s %2$s %3$-20s%n", "", "|", "    " + "\"" + word + "\"," + "\"" + wordType + "\"," + "\"" + wordDefinition[0] + "\"");	
				}else {
			    	System.out.printf("%1$-20s %2$s %3$-20s%n", "", "|", "    " + "\"" + word + "\"," + "\"" + wordType + "\"," + "\"" + wordDefinition[0]);
			    	for(int i = 1; i < wordDefinition.length; i++) {
			    		if(i == wordDefinition.length-1) {
					    	System.out.printf("%1$-20s %2$s %3$-20s%n", "", "|", "    " + wordDefinition[i] + "\"");			    					    			
			    		}else {
					    	System.out.printf("%1$-20s %2$s %3$-20s%n", "", "|", "    " + wordDefinition[i]);			    		
			    		}
			    	}
				}
			}
	    	System.out.printf("%1$-20s %2$s %3$-20s%n", "", "|", "");			
	    	System.out.printf("%1$-20s %2$s %3$-20s%n", "", "|", "    Pages:");
	    	System.out.printf("%1$-20s %2$s %3$-20s%n", "", "|", "    " + indicesMap.get(heading.toString()));			
	    	System.out.println("____________________________________________________________________________________________________");    	
		}
	}
	
	/**
	 * Displays indexed words to the console.
	 * 
	 * @param wordsSet words to be displayed
	 * 
	 */	
	public void printWords(List<String> wordsSet) {
    	int i = 0;
    	for( String key : wordsSet) {
    		i++;
    		if(i%5 == 0) {
        		System.out.println(key);	            				            			
    		}else {
        		System.out.print(key + ", ");	            			
    		}
    	}
	}
	
	/*
	 *  Converts the first letter of a string to upper case.
	 * @param str a string to be converted
	 * @return converted string
	 *  
	 */
    private String capitailizeWord(String str) { 
        StringBuffer s = new StringBuffer();
    	s.append(str.substring(0, 1).toUpperCase());
    	int length = str.length();
    	if( length > 1) s.append(str.substring(1, length));
        return s.toString();
    } 

	
	/*
	 * Saves indexed words with word definitions and pages to a file.
	 * 
	 * @param indexer an object that is Indexable
	 * @param fileName 	a name of a file
     * @throws Exception exceptions
	 * 
	 */	
/*	public void saveIndices(String fileName, Indexable indexer) throws Exception {
		System.out.println("Saving a file....");
		PrintStream ps = new PrintStream(new File(fileName));
		PrintStream console = System.out;
		
		System.setOut(ps);
		printIndices(indexer);
		System.setOut(console);
	}
*/	
	/**
	 * Saves indexed words to a file.
	 * 
	 * @param wordsSet words to be displayed
	 * @param fileName a name of a file
     * @throws Exception exceptions
	 * 
	 */	
/*	public void saveWords(String fileName, Set<String> wordsSet) throws Exception {
		System.out.println("Saving a file....");
		PrintStream ps = new PrintStream(new File(fileName));
		PrintStream console = System.out;

		System.setOut(ps);
		printWords(wordsSet);
		System.setOut(console);		
	}
*/	

}
