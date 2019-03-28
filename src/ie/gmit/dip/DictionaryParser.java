package ie.gmit.dip;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The DictionaryParser class is used to parse a dictionary stored in text file to the <code>Map</code> of word definitions.
 * <p>
 * It parses the text file to the map, where a key is a 'word' and a value is a List of word definitions.
 * A word may contain more than one definition.
 * The definitions are in type of <code>WordDetails</code>, that contains detailed information divided into three groups: "word", "word type" and "definition".
 * <p>
 * All the words definitions in the parsed file must be in the following format:
 * "word","wordtype","definition"
 * e.g.:
 * "Java","n.","Java coffee, a kind of coffee brought from Java."
 * <p>
 * All keys are in lower case.
 */
public class DictionaryParser extends Parser {
	private Map<String, List<WordDetails>> dictionary;
	private StringBuilder builder = new StringBuilder(); // STRINGBUFFER FOR THREADS	
	private WordDetails wordDetail = null;

	// Use ConcurrentHashMap and StringBuffer for threads	
	//	private Map<String, WordDetails> dictionary = new ConcurrentHashMap<String, WordDetails>();
		
	/**
	 * Creates a new DictionaryParser object with an empty dictionary as a map of words and corresponding list of WordDetails
	 */
	public DictionaryParser() {
		super();
		dictionary = new TreeMap<String, List<WordDetails>>();
	}
	
	/**
	 * Big-O notation - O(n)
	 * Parses a text file containing a string of symbols and groups them into an expected data structure ('dictionary').
	 * 
	 * @param br BufferedReader
	 * @throws FileNotFoundException if the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
     * @throws MalformedURLException a malformed URL has occurred
     * @throws IOException if an I/O error occurs.
     * @throws Exception other exception
     * 
	 */	
	public void parse(BufferedReader br)  throws FileNotFoundException, MalformedURLException, IOException, Exception{

		String line = null;
		while((line = br.readLine()) != null) {
			if(line.startsWith("\"")) {
				wordDetail = new WordDetails();
				builder.append(line);
			}else {
				builder.append("\n" + line.trim());
			}
			if(line.endsWith("\"") && wordDetail != null) process();
		}
		br.close();			
	}
	
	/**
	 * Big-O notation - O(n)
	 * Returns the map of the word definitions, where a key is a word and a value is a List of word definitions.
	 * <p>
	 * A word may contain more than one definition. The definitions are a <code>WordDetails</code> type.
	 * 
	 * @return a map of word definitions
	 * 
	 */
	public Map<String, List<WordDetails>> getDictionary() {
		return new HashMap<>(dictionary);
	}

	/*
	 * Big-O notation - O(n)
	 * Processes the word details stored in 'builder' to the WordDetails 'wordDetail'.
	 * The string in a 'builder' is in the following format: "word","word type","definition".
	 * The method divides the string into those three groups and adds the appropriate WordDetails group.
	 * The method uses '\u0022' as a mark for finding those three groups.
	 * 
	 */
	private void process() {
		//"word","word type","definition"		
		StringBuilder temp = new StringBuilder();
		
		int state = 0;
		for(int i = 0; i <builder.length(); i++) {
			char next = builder.charAt(i);
			if(next == '\u0022') {
				if(state == 1) {
					wordDetail.setWord(temp.toString());
				}else if(state == 2) {
					temp.setLength(0);
				}
				else if(state == 3) {
					wordDetail.setWordType(temp.toString());
				}else if(state == 4) {
					wordDetail.setDefinition(builder.substring(i+1, builder.length()-1));

					//Add to the Map
					addDetails();
					return;
				}
				state ++;
			}else {
				temp.append(next);
			}
		}
	}
	
	/*
	 * Resets the StringBuilder 'builder'.
	 */
	private void resetBuilder() {
		builder.setLength(0); // Very efficient. Sets the index counter to 0. The old array and entries still exists, but will not be used by the new one.
	}

	/*
	 * Adds new WordDetails to the 'dictionary' map.
	 * If the word already exists in a map, it adds the WordDetails to the List of WordDetails.
	 */
	private void addDetails() {
		// 'key' for the 'dictionary' map should be in lower case
		String key = wordDetail.getWord().toLowerCase();

		if(!dictionary.containsKey(key)) {
			List<WordDetails> details = new ArrayList<WordDetails>();
			details.add(wordDetail);
			dictionary.put(key, details);
		}else {
			List<WordDetails> details = dictionary.get(key);			
			details.add(wordDetail);
			dictionary.put(key, details);
		}		
		wordDetail = null;
		resetBuilder();
	}

}
