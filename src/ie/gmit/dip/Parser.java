package ie.gmit.dip;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The Parser class is an abstract class for classes that parses (analyzes) a text file containing a string of symbols 
 * and group them into an expected data structure.
 * <p>
 * It contains the helper method for validation if the 'resource' is a type of URL.
 * 
 */
public abstract class Parser implements Parsable {
//REMOVE(try/catch should not be used for a condition operations): private boolean isURL;
	
	/**
	 * Parses a text file containing a string of symbols and groups them into an expected data structure.
	 * 
	 * @param resource      the file path or URL
	 * @throws FileNotFoundException if the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
     * @throws MalformedURLException a malformed URL has occurred
     * @throws IOException if an I/O error occurs.
     * @throws Exception other exception
     * 
	 */
	public abstract void parse(BufferedReader br) throws FileNotFoundException, MalformedURLException, IOException, Exception;
	
	/**
	 * Returns BufferedReader ready to procedure
	 * @param resource
	 * @param isURL pass true if a 'resource' is an URL, false otherwise
	 * @param br BufferedReader
	 * @return BufferedReader ready
	 * @throws Exception exceptions
	 */
	public BufferedReader openBufferReader(String resource, boolean isURL) throws Exception {
		BufferedReader br = null;
		if(isURL) {
            // Use 'openConnection' method from 'URL' class. It returns a URLConnection instance that represents a connection to the remote object referred to by the URL
            URLConnection urlConn = new URL(resource).openConnection();
            br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		}else {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(resource)));
		}		

		return br;
	}
	
/* REMOVE

	/**
	 * Returns true if 'resource' is a <code>URL</code>
	 * 
	 * @param resource - a String that is to be checked if it is URL
	 * @return true if a 'resource' is URL, otherwise false
	 *
	protected boolean isURL(String resource) {
		try {
			new URL(resource);
			isURL = true;
		}catch (MalformedURLException mUlrEx) {
			isURL = false;
		}
		return isURL;
	}	
END OF REMOVE */

}
