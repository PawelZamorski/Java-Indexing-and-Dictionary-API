package ie.gmit.dip;

import java.io.BufferedReader;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The Parsable interface is an abstraction of object that is able to parse (to analyze) a text file containing a string of symbols 
 * and group them into an expected data structure.
 * 
 */
public interface Parsable {

	/**
	 * Parses a text file containing a string of symbols and groups them into an expected data structure.
	 * 
	 * @param resource      the file path or URL
	 * @param isURL pass true if a 'resource' is an URL, false otherwise
	 * @throws Exception    FileNotFoundException, MalformedURLException, IOException, or other exceptions
	 */
	public void parse(BufferedReader br) throws Exception;

}