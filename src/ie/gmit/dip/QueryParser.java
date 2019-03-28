package ie.gmit.dip;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The QueryParser class is used to parse a text file or URL (e.g. book, document ) based on a 'dictionary' and 'ignore words' to an object that is <code>Indexable</code>.
 * <p>
 * It uses a single word a 'heading' and an associated page number as a 'locator'.
 * <p>
 * The default number of lines (lines of text or blank spaces) per page is 40.
 * It may be changed by using setLinesNumber method before parsing a text file.
 * <p>
 * It maps only the words that exist in a 'dictionary'.
 * A 'dictionary' map and an 'ignoreWords' set must be passed as an argument to the constructor.
 * <p>
 * It ignores:
 * - all non-words characters,
 * - all words included in a 'ignoreWords' set passed to the constructor,
 * - words that are not in a 'dictionary' map passed to the constructor.
 * <p>
 * All keys in an Indexable map are in lower case.
 */
public class QueryParser extends Parser {
	private Set<String> ignoreWords;
	private Map<String, List<WordDetails>> dictionary;
	private Indexable indexer = new Index();
	private int linesNumber = 40;
		
	/**
	 * Constructs an QueryParser object that is ready for parsing a text or file.
	 * An object is construct based on 'inoreWords' set and 'dictionary' map that are passed as an argument.
	 * 
	 * @param ignoreWords a set of words to be ignored during a parsing process
	 * @param dictionary a map words and word details.
	 */
	public QueryParser(Set<String> ignoreWords, Map<String, List<WordDetails>> dictionary) {
		super();
		this.ignoreWords = ignoreWords;
		this.dictionary = dictionary;
	}
	
	/**
	 * Big-O notation - O(n^2)
	 * <p>
	 * Parses a text file into an object that is Indexable.
	 * <p>
	 * It accepts a file path or URL as an argument.
	 * <p>
	 * It reads the single words from the file or URL.
	 * If the words is included in 'dictionary' map and not included in 'ignoreWords' set it is added altogether with a corresponding page to the object that is Indexable.
	 * <p>
	 * The default number of lines (lines of text or blank spaces) per page is 40.
	 * 
	 * @param br BufferedReader
	 * @throws FileNotFoundException if the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
     * @throws MalformedURLException a malformed URL has occurred
     * @throws IOException if an I/O error occurs.
     * @throws Exception other exception
     * 
     */
	public void parse(BufferedReader br) throws FileNotFoundException, MalformedURLException, IOException, Exception{

		int lineNumber = 0;
		int page = 1;
		String line = null;
		while((line = br.readLine()) != null) {
			line = line.toLowerCase().replaceAll("[^A-Za-z0-9 ]", "");
			String[] words = line.split(" ");
			for(String s : words) {
				new Thread(new Task(s, page)).start();

//				if(!ignoreWords.contains(s)) {
//					if(dictionary.containsKey(s)) indexer.addIndex(s, Integer.valueOf(page), dictionary.get(s));
//				}
			}
			lineNumber++;
			if((lineNumber % linesNumber) == 0) page++;
		}
		br.close();
	}
	
	/** 
	 * Big-O notation - O(1)
	 * <p>
	 * Returns object that is Indexable and contains a map of indices.
	 * <p>
	 * A map may be empty if no words were parsed to the object.
	 * 
	 * @return object that is Indexable and contains a map of indices and WordDetails
	 * 
	 */
	public Indexable getIndexer() {
		return new Index(indexer.getIndices(), indexer.getWordDefinitions());
	}
	
	/**
	 * Sets the number of lines per page.
	 * 
	 * @param linesNumber number of lines per page.
	 */
	public void setLinesNumber(int linesNumber) {
		this.linesNumber = linesNumber;
	}
	
	/*
	 * Private class for Threads
	 */
	private class Task implements Runnable {
		private String s;
		private int page;
		
		public Task(String s, int page) {
			this.s = s;
			this.page = page;
		}

		public void run() {
			addToIndexable();
		}
		
		public void addToIndexable() {
			if(!ignoreWords.contains(s)) {
				if(dictionary.containsKey(s)) {
					indexer.addIndex(s, Integer.valueOf(page), dictionary.get(s));
				}
			}
		}
	}

	
}
