package ie.gmit.dip;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * An Indexable interface represents an abstraction of an index that is used in publishing, e.g. for indexing a book, a document or other text.
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
public interface Indexable {

	/**
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
	public boolean addIndex(String heading, Integer locator, List<WordDetails> wordDetails);

	/**
	 * Returns the indices as a map of 'headings' and associated set of 'locators'.
	 * <p>
	 * The returned map may be empty.
	 * 
	 * @return a map of 'headings' and associated set of 'locators'
	 */
	public Map<String, Set<Integer>> getIndices();

	/** Returns the words definitions as a map of 'headings' and an associated list of 'wordDetails'.
	 * <p>
	 * The returned map may be empty.
	 * 
	 * @return a map of words definitions
	 */
	public Map<String, List<WordDetails>> getWordDefinitions();		
	
	/**
	 * Returns a total number of unique 'headings' (words).
	 * <p>
	 * It returns 0 if the map is empty.
	 * 
	 * @return int a total number of 'headings'
	 */
	public int indicesTotal();

	/**
	 * Returns a sorted list of 'headings' in the alphanumeric ascending order.
	 * 
	 * @return list of 'headings' in the alphanumeric ascending order
	 */
	public List<String> getHeadingsSetAsc();

	/**
	 * Returns a sorted list of 'headings' in the alphanumeric descending order.
	 * 
	 * @return list of 'headings' in the alphanumeric ascending order
	 */
	public List<String> getHeadingsSetDesc();

	/**
	 * Returns sorted list of 'headings' in an ascending or descending order.
	 * The 'headings' with the same frequency are sorted in the alphanumeric ascending order.
	 * @param asc Sorts ascending (true) or descending (false)
	 * @return list of 'headings' in the frequency order
	 */
	public List<String> sortFrequency(boolean asc);

	
	/**
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
	 */
	public List<String> getXFrequent(int minAmount, boolean lessFrequent);

	/**
	 * Returns list of 'headings' containing the searched words.
	 * 
	 * @param words words to be searched for in the map of indices
	 * @return list of headings containing the searched words. It may be empty if no words given as an argument were found
	 */
	public List<String> getWord(Set<String> words);

	/**
	 * Returns list of 'headings' containing the searched words based on a regex passed to the method as an argument.
	 * 
	 * @param regex a regular expression that is used for searching a words in the map of indices
	 * @return list of 'headings' containing the searched words. The size may be 0 if no words were found
	 */
	public List<String> getWordsWithRegex(String regex);

	/**
	 * Returns an list o 'headings' containing the searched words based on a frequency passed to the method as an argument.
	 * 
	 * @param freq a frequency 
	 * @return an list of 'headings' containing the searched words with the given frequency
	 */
	public List<String> getWordsGivenFrequency(int freq);

	/**
	 * Returns the frequency for the given 'heading' (word) that was passed to the method as an argument. 
	 * 
	 * @param heading a word
	 * @return frequency of the given 'headings'
	 */	
	public int getFrequency(String heading);

	
	
	
	/*
	 * Sorts indices in the alphanumeric ascending order.
	 */
//	public void sortAsc();

	/*
	 * Sorts indices in the alphanumeric descending order.
	 */
//	public void sortDesc();



}