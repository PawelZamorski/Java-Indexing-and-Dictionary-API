package ie.gmit.dip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The Index is an implementation of Indexable interface. It represents an index that is used in publishing, e.g. for indexing a book, a document or other text.
 * <p>
 * It uses 'heading', 'locator' and 'wordDetails', where:
 * - 'heading' is a word
 * - 'locator' are pointer associated with 'heading', page number or line number
 * - 'wordDetails' are dictionary word definitions associated with 'heading'
 * <p>
 * It uses a map of 'heading' and 'locators' (a map of indices) and a map of 'headings' and 'wordDetails' (a map of words definitions).
 * <p>
 * A 'locators' is a set of Integers. Integers cannot be duplicated within the same set of 'locators'.
 * It means, that if 'heading' appears more than once in the same location (page or line), it will not be duplicates in an associated set of 'locators'.
 * <p>
 * Some methods use a frequency of 'heading'.
 * The frequency of 'heading' is the number of 'locators' (pages or lines) associated with a 'heading'.
 * The frequency is not the total number of times a 'heading' appeared in a document, but the number of 'locators'.
 */
public class Index implements Indexable {
	private Map<String, Set<Integer>> indices;
	private Map<String, List<WordDetails>> wordDefinitions;

	/**
	 * Creates a new Index object with an empty map of indices and a map wordDefinitions
	 */
	public Index() {
		super();
		this.indices = new ConcurrentHashMap<String, Set<Integer>>();
		this.wordDefinitions = new ConcurrentHashMap<String, List<WordDetails>>();
	}
	
	/**
	 * Constructs a new Index object based on a map of indices and map of word definitions passed as arguments.
	 * 
	 * @param indices a map of indices
	 * @param wordDefinitions a map of word definitions
	 */
	public Index(Map<String, Set<Integer>> indices, Map<String, List<WordDetails>> wordDefinitions) { // Map interface doesn't guarantee the map to be sorted
		super();
		this.indices = indices;
		this.wordDefinitions = wordDefinitions;
	}
	

	/**
	 * Big-O notation - O(log(n))
	 * <p>
	 * Returns true if the 'heading' and associated 'locator' and 'wordDetails' was successfully added to the map of indices.
	 * If the index already exists in the map, it adds a 'locator' (e.g. page or line) to the corresponding set of 'locators'.
	 * <p>
	 * It returns false if the 'locator' already existed in a associated set of 'locators'.
	 * 
	 * @param heading a word
	 * @param locator a page number
	 * @param wordDetails word details
	 * @return true if the 'heading' and 'locator' was added to the map of indices, 
	 * 			true if 'locator' was added to the set of 'locators',
	 * 			otherwise false
	 */
	public boolean addIndex(String heading, Integer locator, List<WordDetails> wordDetails) {
		if(!indices.containsKey(heading)) { // Add new index to indices: a key & value pair ('heading' & 'locator') and return true
			Set<Integer> temp = new TreeSet<>();
			temp.add(locator);
			indices.put(heading, temp);
			// Add wordDetails
			wordDefinitions.put(heading, wordDetails);
			return true;
		}else { // Add new 'locator' the set of 'locators' associated with the 'heading' and return true.
			Set<Integer> temp = indices.get(heading);
			if(!temp.add(locator)) return false; // Return false if 'locator' already exists in a set of 'locators'
			indices.put(heading, temp);
			return true;
		}
	}
	
	/**
	 * Big-O notation - O(n log(n))
	 * <p>
	 * Returns the indices as a map of 'headings' and associated set of 'locators'.
	 * <p>
	 * The returned map may be empty.
	 * 
	 * @return a map of 'headings' and associated set of 'locators'
	 * 
	 */
	public Map<String, Set<Integer>> getIndices() {		
		return new TreeMap<String, Set<Integer>>(this.indices);
	}
	
	/** 
	 * Big-O notation - O(n log(n))
	 * <p>
	 * Returns the words definitions as a map of 'headings' and an associated list of 'wordDetails'.
	 * <p>
	 * The returned map may be empty.
	 * 
	 * @return a map of words definitions
	 * 
	 */
	public Map<String, List<WordDetails>> getWordDefinitions() {		
		return new TreeMap<String, List<WordDetails>>(this.wordDefinitions);
	}
	
	/**
	 * Big-O notation - O(1)
	 * <p>
	 * Returns a total number of unique 'headings' (words).
	 * <p>
	 * It returns 0 if the map is empty.
	 * 
	 * @return int a total number of 'headings'
	 * 
	 */
	public int indicesTotal() {
		return indices.size();
	}
	
	/**
	 * Big-O notation - O(n)
	 * <p>
	 * Returns a sorted list of 'headings' in the alphanumeric ascending order.
	 * 
	 * @return 'headings' in the alphanumeric ascending order
	 * 
	 */
	public List<String> getHeadingsSetAsc() {
		Set<String> wordsAsc = new TreeSet<String>(indices.keySet());
		return new ArrayList<String>(wordsAsc); // TreeSet has been used to guarantee natural ordering.
	}
	
	/**
	 * Big-O notation - O(n)
	 * <p>
	 * Returns a sorted list of 'headings' in the alphanumeric descending order.
	 * 
	 * @return list of 'headings' in the alphanumeric ascending order
	 * 
	 */
	public List<String> getHeadingsSetDesc() {
		Set<String> wordsDesc = new TreeSet<String>(Collections.reverseOrder());
		wordsDesc.addAll(indices.keySet());
		return new ArrayList<String>(wordsDesc); // TreeSet has been used to guarantee natural ordering.
	}	
	
	/**
	 * Big-O notation - O(n^2)
	 * <p>
	 * Returns sorted list of 'headings' in an ascending or descending order.
	 * The 'headings' with the same frequency are sorted in the alphanumeric ascending order.
	 * @param asc sorts ascending if true
	 * @return list of 'headings' in the frequency order
	 */
	public List<String> sortFrequency(boolean asc) {
		List<String> sortFreq = new ArrayList<String>();
		// Map of frequency
		Map<String, Integer> tempFreqMap = new TreeMap<String, Integer>();
		// Set of keys
		Set<String> keys = indices.keySet();
		for(String key : keys) {
			tempFreqMap.put(key, Integer.valueOf(indices.get(key).size()));
		}
		// Sorted set of keys
		Set<Integer> tempFreqSet = new TreeSet<Integer>(
				new Comparator<Integer>() {
					public int compare(Integer o1, Integer o2) {
						if(asc) return o1.compareTo(o2);//sort in ascending order											
						return -o1.compareTo(o2);//sort in descending order											
					}
				});
		tempFreqSet.addAll(tempFreqMap.values());
		// Add to sortFreq List
		for(Integer tempFreq : tempFreqSet) {
			for(String key : keys) {
				if(tempFreq == indices.get(key).size()) {
					sortFreq.add(key);
				}
			}
		}
		return sortFreq;
	}

	/**
	 * Big-O notation - O(n)
	 * <p>
	 * Returns list of 'headings' containing the most or less frequent 'headings' (words) in the alphanumeric ascending order.
	 * <p>
	 * The first parameter sets the minimum number of most or less frequent words returned.
	 * If there are words with the same frequency, the number of returned words may exceed the minimum number.
	 * <p>
	 * Returns the less frequent 'headings' if the 'lessFrequent' parameter is true. Otherwise returns the most frequent 'headings'.
	 * 
	 * @param minAmount minimum number of returned 'headings'
	 * @param lessFrequent if <code>true</code> it returns the less frequent 'headings'. If <code>false</code> it returns the most frequent 'headings'
	 * @return list of the most or less frequent 'headings'
	 * 
	 */
	public List<String> getXFrequent(int minAmount, boolean lessFrequent) {
		List<String> tempList = new ArrayList<String>();
		
		// get sorted list asc or desc, that is the keys to the map
		List<String> keys = sortFrequency(lessFrequent);
		int i = 0;
		for(String key : keys) {
			if(i < minAmount) {
				tempList.add(key);
			}
			i++;
		}		
		return tempList;
	}

	/**
	 * Big-O notation - O(n)
	 * <p>
	 * Returns list of 'headings' containing the searched words.
	 * 
	 * @param words words to be searched for in the map of indices
	 * @return list of headings containing the searched words. It may be empty if no words given as an argument were found
	 * 
	 */
	public List<String> getWord(Set<String> words) {
		List<String> tempList = new ArrayList<String>();
		for(String word : words) {
			if(indices.containsKey(word.toLowerCase())) {
				tempList.add(word);
			}
		}
		return tempList;
	}
	
	/**
	 * Big-O notation - O(n)
	 * <p>
	 * Returns list of 'headings' containing the searched words based on a regex passed to the method as an argument.
	 * 
	 * @param regex a regular expression that is used for searching a words in the map of indices
	 * @return list of 'headings' containing the searched words. The size may be 0 if no words were found
	 * 
	 */
	public List<String> getWordsWithRegex(String regex) {
		List<String> tempList = new ArrayList<String>();

		Set<String> keys = indices.keySet();
		for(String key : keys) {
			if(key.matches(regex.toLowerCase())) {
				tempList.add(key);
			}
		}
		return tempList;
	}
	
	/**
	 * Big-O notation - O(n)
	 * <p>
	 * Returns an list o 'headings' containing the searched words based on a frequency passed to the method as an argument.
	 * 
	 * @param freq a frequency 
	 * @return an list of 'headings' containing the searched words with the given frequency
	 * 
	 */
	public List<String> getWordsGivenFrequency(int freq) {
		List<String> tempList = new ArrayList<String>();
		
		Set<String> keys = indices.keySet();
		for(String key : keys) {
			if(getFrequency(key) == freq) {
				tempList.add(key);
			}
		}
		return tempList;
	}
	
	/**
	 * Big-O notation - O(1)
	 * <p>
	 * Returns the frequency for the given 'heading' (word) that was passed to the method as an argument. 
	 * 
	 * @param heading a word
	 * @return frequency of the given 'headings'
	 * 
	 */	
	public int getFrequency(String heading){
		if(this.indices.containsKey(heading)) {			
			return this.indices.get(heading).size();
		}
		return 0;
	}

	
	
	/* 
	 * Big-O notation - O(n log(n))
	 * <p>
	 * Sorts indices in the alphanumeric ascending order.
	 * 
	 */
/*	public void sortAsc() {
		// Use TreeMap for natural order. Keys are type String that are sorted in alphanumeric ascending order.
		Map<String, Set<Integer>> temp = new TreeMap<String, Set<Integer>>(this.indices);
		this.indices = temp;
	}	
*/	
	/* 
	 * Big-O notation - O(n log(n))
	 * <p>
	 * Sorts indices in the alphanumeric descending order.
	 * 
	 */
/*	public void sortDesc() {
		// Use TreeMap for natural order. Use Comparator to sort in descending order. Keys are type String.
		Map<String, Set<Integer>> temp = new TreeMap<String, Set<Integer>>(
				// Collections.reverseOrder(); // Other way of reversing an order
				new Comparator<String>() {
					public int compare(String o1, String o2) {
						return -o1.compareTo(o2);//sort in descending order
					}
				});
		temp.putAll(this.indices);
		// Use LinkedHashMap for keeping the descending order
		Map<String, Set<Integer>> tempLHM = new LinkedHashMap<String, Set<Integer>>(temp);				
		this.indices = tempLHM;
	}
*/

}
