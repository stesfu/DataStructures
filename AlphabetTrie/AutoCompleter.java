package autocompleter;

import java.util.ArrayList;

/**
 * A ternary-search-tree implementation of a text-autocompletion
 * trie, a simplified version of some autocomplete software.
 * @author Salem Tesfu 
 */
public class AutoCompleter implements AutoCompleterInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    TTNode root;
    
    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    AutoCompleter () {
        root = null;
    }
    
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    
    /**
     * Determines whether or not Autocompleter has search terms
     * stored 
     * @return boolean true if Autocompleter has no search terms, 
     * false otherwise 
     */
    public boolean isEmpty () {
        return(root == null);
      
    }
    
    /**
     * Adds a new term to the Autocompleter 
     * @param toAdd string of desired term to add
     */
	public void addTerm(String toAdd) {
		if (hasTerm(normalizeTerm(toAdd))) {
			return;
		}
		root = append(root, normalizeTerm(toAdd));
	}
    
    /**
     * Returns whether or not the given query is in the Autocompleter
     * @param query string to search if in the Autocompleter
     * @return boolean true if query in Autocompleter, false
     * otherwise
     */
	public boolean hasTerm(String query) {
		return isInTrie(root, normalizeTerm(query));

	}
    
    /**
     * Returns first search term that has query as a prefix
     * @param query string as a prefix to autofill
     * @return string of the word in autocompleter, null
     * if the given prefix is for no stored words 
     */
	public String getSuggestedTerm(String query) {
		return (getSuggested(root, normalizeTerm(query)));
	}
    
    /**
     * Sorts terms in Autocompleter alphabetically
     * @return ArrayList of all words in autocompleter 
     */
	public ArrayList<String> getSortedTerms() {
		return sortWords(root, "", new ArrayList<String>());
	}
    
    /**
     * Adds a new term to the Autocompleter with priority
     * @param toAdd string of desired term to add
     * @param priority int degree of priority
     */
	public void addTerm(String toAdd, int priority) {
		if (hasTerm(normalizeTerm(toAdd))) {
			return;
		}
		root = appendPriority(root, normalizeTerm(toAdd), priority);
	}
    
    /**
     * Returns search term that has query as a prefix with the 
     * highest priority
     * @param query string as a prefix to autofill
     * @return string of the word in autocompleter, null
     * if the given prefix is for no stored words 
     */
	public String getBestTerm(String query) {
		if (hasTerm(normalizeTerm(query))) {
			return query;
		}
		return suggestPriority(findPrefix(root, query), query, "");
	}
    
    
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
    
    /**
     * Normalizes a term to either add or search for in the tree,
     * since we do not want to allow the addition of either null or
     * empty strings within, including empty spaces at the beginning
     * or end of the string (spaces in the middle are fine, as they
     * allow our tree to also store multi-word phrases).
     * @param s The string to sanitize
     * @return The sanitized version of s
     */
	private String normalizeTerm(String s) {
		// Edge case handling: empty Strings illegal
		if (s == null || s.equals("")) {
			throw new IllegalArgumentException();
		}
		return s.trim().toLowerCase();
	}
    
    /**
     * Given two characters, c1 and c2, determines whether c1 is
     * alphabetically less than, greater than, or equal to c2
     * @param c1 The first character
     * @param c2 The second character
     * @return
     *   - some int less than 0 if c1 is alphabetically less than c2
     *   - 0 if c1 is equal to c2
     *   - some int greater than 0 if c1 is alphabetically greater than c2
     */
	private int compareChars(char c1, char c2) {
		return Character.toLowerCase(c1) - Character.toLowerCase(c2);
	}
    
    // [!] Add your own helper methods here!
    
    /**
     * Given a node adds a new node for each letter of word down the middle 
     * path 
     * @param node TTNode that you wish to build down from
     * @param s string you want to build down
     * @param priority int indication of priority
     */
	private void buildDown(TTNode node, String s, int priority) {
		char[] toBuild = s.toCharArray();
		TTNode current = node;
		for (char c : toBuild) {
			current.mid = new TTNode(c, false);
			current.priority = priority;
			current = current.mid;
		}
		current.wordEnd = true;
		current.wordEndPriority = priority;
		current.priority = priority;
	}
	
    /**
      * Given a node adds each letter of word down the middle 
      * path to a string
     * @param current TTNode you want to collect string from
     * @return string of the middle path characters 
     */
	private String buildDownString(TTNode current) {
		String answer = "";
		while (!current.wordEnd) {
			current = current.mid;
			answer += current.letter;
		}
		return answer;
	}
	
    /**
     * Finds the node of the last character of a prefix
     * @param current TTNode which you want to start 
     * searching 
     * @param query Prefix of which you want to find 
     * position 
     * @return TTNode of last character of prefix
     */
	private TTNode findPrefix(TTNode current, String query) {
		String answer = "";
		int index = 0;

		while (index < query.length()) {
			if (current == null && index != query.length()) {
				return null;
			}
			int compareValue = compareChars(query.charAt(index), current.letter);
			if (compareValue == 0) {
				answer += current.letter;
				index++;
				if (index < query.length()) {
					current = current.mid;
				}
			} else if (compareValue < 0) {
				current = current.left;
			} else if (compareValue > 0) {
				current = current.right;
			}

		}

		if (!answer.contentEquals(query)) {
			return null;
		}
		return current;
	}
    
    /**
     * Adds each character of a string to desired node
     * @param current TTNode where you want to start adding
     * more words to
     * @param toAppend String of desired added word
     * @return TTNode (current) after adding new term
     */
	private TTNode append(TTNode current, String toAppend) {
		if (current == null) {
			current = new TTNode(toAppend.charAt(0), toAppend.length() == 1);
			buildDown(current, toAppend.substring(1), 0);
			return current;
		}

		int compareValue = compareChars(toAppend.charAt(0), current.letter);

		if (compareValue == 0) {
			if (toAppend.length() == 1) {
				current.wordEnd = true;
			} else {
				current.mid = append(current.mid, toAppend.substring(1));
			}

		} else if (compareValue > 0) {
			current.right = append(current.right, toAppend);
		} else if (compareValue < 0) {
			current.left = append(current.left, toAppend);
		}
		return current;
	}
		
	/**
     * Determines whether given string is in autocompleter
     * @param current TTNode to start looking at
     * @param toFind string to see if in autocompleter
     * @return boolean true if toFind is in autocompleter,
     * false otherwise
     */
	private boolean isInTrie(TTNode node, String toFind) {

		TTNode current = findPrefix(node, toFind);

		if (current != null && current.wordEnd) {
			return true;
		} else {
			return false;
		}

	}
    
	/**
	 * 
	 * Returns first search term that has query as a prefix
	 * @param current TTNode wish to start searching on
     * @param prefix string to autofill
     * @return string of the word in autocompleter, null
     * if the given prefix is for no stored words 
	 */
	private String getSuggested(TTNode current, String prefix) {
		if (hasTerm(normalizeTerm(prefix))) {
			return prefix;
		}

		current = findPrefix(current, prefix);

		if (current == null) {
			return null;
		}

		return prefix + buildDownString(current);

	}
	
    /**
     * Sorts terms in Autocompleter alphabetically
     * @param current TTNode for looking at characters 
     * @param temp String consisting of added characters to make
     * a word
     * @param wordsSorted ArrayList of sorted words
     * @return ArrayList of all words in autocompleter 
     */
	private ArrayList<String> sortWords(TTNode current, String temp, ArrayList<String> wordsSorted) {
		if (current == null) {
			return wordsSorted;
		}

		wordsSorted = sortWords(current.left, temp, wordsSorted);

		if (current.wordEnd) {
			wordsSorted.add(temp + current.letter);
		}
		wordsSorted = sortWords(current.mid, temp + current.letter, wordsSorted);
		wordsSorted = sortWords(current.right, temp, wordsSorted);

		return wordsSorted;
	}
	
	/**
	 * Returns search term that has query as a prefix with the 
     * highest priority
	 * @param current TTNode to check priority with respect to
	 * that of the prefix
	 * @param query string a prefix to autofill
	 * @param temp String consisting of added characters to make
     * a word
	 * @return String autocompleted of highest priority
	 */
	private String suggestPriority(TTNode current, String query, String temp) {

		if (current == null) {
			return null;
		}
		
		int prefixPriority = findPrefix(root, query).priority;
		
		System.out.println("hi " + current.letter);
		
		while(current.wordEndPriority != prefixPriority) {
			if (current.left != null && current.left.priority == prefixPriority) {
				current = current.left;
				temp += current.letter;
			}

			if (current.right != null && current.right.priority == prefixPriority) {
				current = current.right;
				temp += current.letter;

			}

			if (current.mid != null && current.mid.priority == prefixPriority) {
				current = current.mid;
				temp += current.letter;

			}
		}

		return query + temp;
	}
    
    /**
     * Adds each character of a string to desired node keeping track of priority
     * @param current TTNode where you want to start adding
     * more words to
     * @param toAppend String of desired added word
     * @param priorityInt int of desired word priority 
     * @return TTNode (current) after adding new term
     */
	private TTNode appendPriority(TTNode current, String toAppend, int priorityInt) {
		if (current == null) {
			current = new TTNode(toAppend.charAt(0), toAppend.length() == 1);
			current.priority = priorityInt;
			buildDown(current, toAppend.substring(1), priorityInt);
			return current;
		}

		int compareValue = compareChars(toAppend.charAt(0), current.letter);

		if (priorityInt > current.priority) {
			current.priority = priorityInt;
		}

		if (compareValue == 0) {
			if (toAppend.length() == 1) {
				current.wordEnd = true;
			} else {
				current.mid = appendPriority(current.mid, toAppend.substring(1), priorityInt);
			}

		} else if (compareValue > 0) {
			current.right = appendPriority(current.right, toAppend, priorityInt);
		} else if (compareValue < 0) {
			current.left = appendPriority(current.left, toAppend, priorityInt);
		}
		return current;
	}
    
   
    // -----------------------------------------------------------
    // TTNode Internal Storage
    // -----------------------------------------------------------
    
    /**
     * Internal storage of autocompleter search terms
     * as represented using a Ternary Tree with TTNodes
     */
    private class TTNode {
        
        boolean wordEnd;
        char letter;
        int priority; 
        int wordEndPriority;
        TTNode left, mid, right;
        
        /**
         * Constructs a new TTNode containing the given character
         * and whether or not it represents a word-end, which can
         * then be added to the existing tree.
         * @param c Letter to store at this node
         * @param w Whether or not this is a word-end
         */
        TTNode (char c, boolean w) {
            letter  = c;
            wordEnd = w;
        }
        
    }
    
}
