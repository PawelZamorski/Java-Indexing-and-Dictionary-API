package ie.gmit.dip;

import java.io.Serializable;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The WordDetails class is a bean class that is used for storing a word definition.
 * <p>
 * It stores word, word type and word definition.
 * It may be used for storing a dictionary definition.
 * <p>
 * It is <code>Serializable</code>.
 * 
 */
public class WordDetails implements Serializable{
	private static final long serialVersionUID = -8717669629598745535L;
	private String word;
	private String wordType;
	private String definition;
	
	public WordDetails() {
		super();
	}
	
	/**
	 * Returns a word.
	 * 
	 * @return word a word that is defined
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Sets a word.
	 * 
	 * @param word a word that is defined
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * Returns a word type.
	 * 
	 * @return word type characterizes a word
	 */
	public String getWordType() {
		return wordType;
	}

	/**
	 * Sets a word type.
	 * 
	 * @param wordType a word type that characterizes a word
	 */
	public void setWordType(String wordType) {
		this.wordType = wordType;
	}

	/**
	 * Returns a word definition.
	 * 
	 * @return word definition
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * Sets a word definition.
	 * 
	 * @param definition a word definition
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	/**
	 * Returns a string representation of <code>WordDetails</code> object, e.g:
	 * "WordDetails [word=Java, WordType=n., definition=Java coffee, a kind of coffee brought from Java.]"
	 * 
	 * @return a word details as a string
	 */
	@Override
	public String toString() {
		return "WordDetails [word=" + word + ", WordType=" + wordType + ", definition=" + definition + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wordType == null) ? 0 : wordType.hashCode());
		result = prime * result + ((definition == null) ? 0 : definition.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordDetails other = (WordDetails) obj;
		if (wordType == null) {
			if (other.wordType != null)
				return false;
		} else if (!wordType.equals(other.wordType))
			return false;
		if (definition == null) {
			if (other.definition != null)
				return false;
		} else if (!definition.equals(other.definition))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

}
