/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  SentUtils.java
 * Purpose    :  Learning exercise to review Java
 * @author    :  Andrew Seaman 
 * @author    :  Salem Tesfu 
 * Date       :  2019-09-04
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
package sentutil;

import java.util.ArrayList;
import java.util.Arrays; 
import java.util.List;

/**
 * Simple library which might (outside of this assignment) contain
 * various functions related to some sentence utilities.
 */
public class SentUtils {

    /**
     * Returns the number of unique, repeated words that are found
     * in the given sentence sent
     * @param sent The sentence in which to count repeated words
     * @return The number of unique, repeated words
     */
    public static int repeats (String sent) {
    	String[] splitSent = sent.split(" ");
    	List<String> words = Arrays.asList(splitSent);
    	List<String> repeatedWords = new ArrayList<String>();
    	
    	for (int i = 0; i < words.size() - 1; i++) {
    		int repeatedIndex = words.lastIndexOf(words.get(i));
    		if(repeatedIndex != i && repeatedWords.indexOf(words.get(i)) == -1) {
    			repeatedWords.add(words.get(i));
    		}
    	}
    	return repeatedWords.size();
    }
}