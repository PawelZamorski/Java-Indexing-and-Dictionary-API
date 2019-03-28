package ie.gmit.dip;

import java.util.*;
import java.io.*;
import java.net.MalformedURLException;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The IgnoreWordsParser class is used for parsing a text file to the <code>Set</code> of words.
 */
public class IgnoreWordsParser extends Parser {
	private Set<String> ignoreWords = new TreeSet<String>();
	
	/**
	 * Creates a new IgnoreWordsParser object that is ready for parsing a text file or URL.
	 */
	public IgnoreWordsParser() {
		super();
	}
	
	/**
	 * Big-O notation - O(n)
	 * <p>
	 * Parses a text file into a set of words.
	 * <p>
	 * It uses a space or newline as a delimiter.
	 * All non-word characters are removed.
	 * It is case insensitive. All words are parsed to the set of lower case words.
	 * 
	 * @param br BufferedReader
	 * @throws FileNotFoundException if the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
     * @throws MalformedURLException a malformed URL has occurred
     * @throws IOException if an I/O error occurs.
     * @throws Exception other exception
     * 
	 */
	public void parse(BufferedReader br) throws FileNotFoundException, MalformedURLException, IOException, Exception{
		
		String line = null;
		while((line = br.readLine()) != null) {
			
            line = line.replaceAll("[^A-Za-z0-9 ]", "");
			line = line.toLowerCase();
			String[] words = line.split(" +"); // space one or more times to prevent "" empty string
			ignoreWords.addAll(Arrays.asList(words));
		}
		br.close();
	}
	
	/**
	 * Big-O notation - O(n log(n))
	 * <p>
	 * Returns a set of words, that has been parsed.
	 * All words are in lower case.
	 * 
	 * @return a set of words (Strings)
	 * 
	 */
	public Set<String> getIgnoreWords(){
		return new TreeSet<String>(this.ignoreWords);
	}
}
