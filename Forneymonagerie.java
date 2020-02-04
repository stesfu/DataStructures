/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  Forneymonagerie.java
 * Purpose    :  Create a new Data Structure that is a medium between ArrayLists and Multi-sets of String
 * @author    :  Andrew Forney (skeleton)
 * @author    :  Salem Tesfu
 * Date       :  2019-10-21
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

package forneymonagerie;

public class Forneymonagerie implements ForneymonagerieInterface {

    // Private Classes
    // ----------------------------------------------------------
    private class ForneymonType {
        String type;
        int count;

        ForneymonType (String t, int c) {
            type = t;
            count = c;
        }
    }


    // Fields
    // ----------------------------------------------------------
    private ForneymonType[] collection;
    private int size;
    private int typeSize;
    private static final int START_SIZE = 16;


    // Constructor
    // ----------------------------------------------------------
    Forneymonagerie () {
    	collection = new ForneymonType[START_SIZE];
        this.size = 0;
        this.typeSize = 0;
    }


    // Methods
    // ----------------------------------------------------------

    // >> [DM] Look into writing top-level javadoc comments correctly.
    /* Determine if there are any Forneymon in the Foneymonagerie
     * @return Boolean indicating if the Forneymonagerie is empty
     */
    public boolean empty () {
    	return (this.size == 0);
    }

    /* Determine how long the Forneymonagerie is
     * @return Integer of the Forneymonagerie's size
     */
    public int size () {
    	return this.size;
    }

    /* Determine how many of a particular type of Forneymomn are in
     * the Forneymonagerie
     * @return Integer of how many Forneymon of type
     */
    public int typeSize () {
    	return this.typeSize;
    }

    /* Adds a Forneymon to the Forneymonagerie
     * @param toAdd String of the type you are trying to add to the
     * Forneymonagerie
     * @return Boolean whether or not the type was already in the array
     */
    public boolean collect (String toAdd) {
    	checkAndGrow();
        boolean typeExists = false;
      //Case 1: The monster to be added is of the same type
        if(contains(toAdd)) {
        	for(int i = 0; i < this.typeSize; i++) {
        		if(isSameType(toAdd, collection[i].type)) {
        			this.size++;
        			collection[i].count++;
        			sortLeft(i);
        			typeExists = false;
        		}
        	}
        }
      //Case 2: The monster(s) is of a different type
        else {
        	collection[this.typeSize] = new ForneymonType(toAdd, 1);
    		this.size++;
    		this.typeSize++;
    		typeExists = true;
        }
    	return typeExists;
    }

    /* Remove one Forneymon of a particular type
     * @param toRemove String of the type of the specific Forneymon
     *  you are trying to take out of the Forneymonagerie
     * @return Boolean whether a forneymon was removed or not
     */
    public boolean release (String toRemove) {
    	for(int i = 0; i < this.typeSize; i++) {
    		if(isSameType(toRemove, collection[i].type)) { //method for this type
    			collection[i].count--;
    			if (collection[i].count == 0) {
    				this.typeSize--;
    			}
    			this.size--;
    			sortRight(i);
    			return true;
    		}

    	}
    	return false;
    }

    /* Removes all Forneymon of a given type
     * @param toRemove String of the type you are trying to take out of the
     * Forneymonagerie
     */
    public void releaseType (String toNuke) {
    	if(!contains(toNuke)) {
    		return;
    	}
    	for(int i = 0; i < this.typeSize; i++) {
    		if(isSameType(toNuke, collection[i].type)) {
    			this.size = this.size - collection[i].count; ///size -= that
    			this.collection[i].count = 0;
    			this.typeSize--;
    			sortRight(i);
    			//shiftLeft(i);
    		}
    	}
    }

    /* Determine the number of Forneymon of a particular type.
     * @param toCount String of the type you are counting Forneymon amount
     * @return Integer of the amount of Forneymon that have given type
     */
    public int countType (String toCount) {
    	int count = 0;
    	for(int i = 0; i < this.typeSize; i++) {
    		if(isSameType(toCount, collection[i].type)) {
    			 count = ((Integer) collection[i].count).intValue();
    		}
    	}
    	return count;
    }

    /* Checks if a particular type is in the Forneymonagerie
     * @param toCheck String of the type that might be in the
     * Forneymonagerie
     * @return Boolean whether or not it is in the Forneymonagerie
     */
    public boolean contains (String toCheck) {
    	for(int i = 0; i < this.typeSize; i++) {
    		if(isSameType(toCheck, collection[i].type)) {
    			return true;
    		}
    	}
    	return false;
    }

    /* Return the Forneymon at a particular place in regards to
     * the amount of all Forneymon in the Forneymonagerie
     * @param n int of the position of a Forneymon
     * @return String of the type of the Forneymon at that
     * particular position
     */
    public String nth (int n) {
    	if(n >= this.size) {
    		throw new IllegalArgumentException("argument beyond size");
    	}
    	int count = -1;
    	for(int i = 0; i < this.typeSize; i++) {
    		for(int j = 0; j < this.collection[i].count; j++ ) {
    			count ++;
    			if(count == n) {
        			return this.collection[i].type;
        		}
    		}
    	}
    	return null;
	}

    /* Determine the Forneymon type of the most recently
     * modified Forneymon Type of a particular rarity
     * @param count Integer of the rarity to seek
     * @return String of the type of the last modified
     * Forneymon of that particular type
     */
    public String mostRecentAtRarity (int count) {
    	for(int i = this.typeSize - 1 ; i >= 0; i--) {
    		if(count == collection[i].count) {
    			return collection[i].type;
    		}
    	}
    	return null;
    }

    /* Swap the contents of two Forneymonagerie
     * @param Another Forneymonagerie to exchange with
     */
    public void trade (Forneymonagerie other) {
        // >> [DM] There is no need to create a clone. Copy via referncing fields.
    	Forneymonagerie temp = new Forneymonagerie();
    	temp.size = other.size;
    	temp.typeSize = other.typeSize;
    	temp.collection = other.collection;
    	other.size = this.size;
    	other.typeSize = this.typeSize;
    	other.collection = this.collection;
    	this.size = temp.size;
    	this.typeSize = temp.typeSize;
    	this.collection = temp.collection;


//    	other.collection = this.collection;
//    	other.size = this.size;
//    	other.typeSize = this.typeSize;
//    	this.collection = temp.collection;
//    	this.size = temp.size;
//    	this.typeSize = temp.typeSize;
    }

    /* Create a deep copy of another Forneymonagerie
     * @return An identical copy of a Forneymonagerie
     */
    @Override
    public Forneymonagerie clone () {
        Forneymonagerie clone = new Forneymonagerie();
        // >> [DM] Does this create a deep copy of collection?
    	//clone.collection = this.collection;
    	clone.size = this.size;
    	clone.typeSize = this.typeSize;
    	for(int i = 0; i < clone.typeSize; i++) {
    		clone.collection[i] = new ForneymonType(this.collection[i].type, this.collection[i].count);
    	}
    	return clone;
    }

    /* Create a string representation of the calling
     * Forneymonagerie
     * @return string of given Forneymonagerie
     */
    @Override
    public String toString () {
        String[] result = new String[typeSize];
        for (int i = 0; i < typeSize; i++) {
            result[i] = "\"" + collection[i].type + "\": " + collection[i].count;
        }
        return "[ " + String.join(", ", result) + " ]";
    }


    // Static methods
    // ----------------------------------------------------------
    /* Create a new Forneymonagerie that contains all the Forneymon that are
     * in the first Forneymonagerie but not the second one
     * @param Two Forneymonageries to compare
     * @return new Forneymonagerie that consists of the differences
     */
    public static Forneymonagerie diffMon (Forneymonagerie y1, Forneymonagerie y2) {
        Forneymonagerie different = new Forneymonagerie();
        // >> [DM] Make sure to have a space between for/if/else and the first parenthesis.
    	for(int i = 0; i < y1.typeSize(); i++) {
    		if(y2.contains(y1.collection[i].type) && y1.collection[i].count > (y2.collection[i].count) ){
    			different.collect(y1.collection[i].type);
    			different.collection[i].count = Math.abs(y1.collection[i].count - y2.collection[i].count);
    		}else if (!y2.contains(y1.collection[i].type)){
    			different.collect(y1.collection[i].type);
    			different.collection[i].count = y1.collection[i].count;
    		}
    	}
//    	for(int j = 0; j < y1.typeSize(); j++) {
//    		for(int i = j; i < y1.typeSize(); i++) {
//    			if(y1.collection[i].type.equals(y2.collection[i].type) && y1.collection[i].count > (y2.collection[i].count) ){
//        			different.collect(y1.collection[i].type);
//        			different.collection[i].count = Math.abs(y1.collection[i].count - y2.collection[i].count);
//        		}else {
//        			different.collect(y1.collection[i].type);
//        			different.collection[i].count = y1.collection[i].count;
//        		}
//    		}
//
//
//    	}
    	return different;
    }

    /* Determines if two Forneymonageries have the same Forneymon Types and
     * counts regardless of order
     * @param Two Forneymonageries to compare
     * @return Boolean whether the two are the same or not
     */
    public static boolean sameCollection (Forneymonagerie y1, Forneymonagerie y2) {
    	int sameCount = 0;
    	if(y1.typeSize != y2.typeSize || y1.size != y2.size) {
    		return false;
    	}
    	for(int i = 0; i < y1.typeSize(); i++) {
    		for(int j = 0; j < y1.typeSize(); j++) {
    			if(y1.collection[i].type.equals(y2.collection[j].type) && (y1.collection[i].count == y2.collection[j].count)) {
    				sameCount++;
    			}
    		}

    	}
        return (sameCount == y1.typeSize());
    }


    // Private helper methods
    // ----------------------------------------------------------

    /**
     * Shifts all elements to the right of the given
     * index one left
     */
    private void shiftLeft (int index) {
        for (int i = index; i < this.typeSize - 1; i++) {
            collection[i] = collection[i+1];
        }
    }

    /**
     * Checks to see if two Forneymon types are the same
     * @param Two strings of Forneymon types
     * @return Boolean for verification
     */
    private boolean isSameType (String t1, String t2 ) {
        return (t1.contentEquals(t2));
    }

    /**
     * Expands the length of the collection whenever it is at
     * capacity
     */
    private void checkAndGrow () {
        // Case: big enough to fit another item, so no
        // need to grow
        if (this.typeSize < this.collection.length) {
            return;
        }

        // Case: we're at capacity and need to grow
        // Step 1: create new, bigger array; we'll
        // double the size of the old one
        ForneymonType[] newCollection = new ForneymonType[this.typeSize * 2];

        // Step 2: copy the items from the old array
        for (int i = 0; i < this.typeSize; i++) {
            newCollection[i] = this.collection[i];
        }

        // Step 3: update IntList reference
        this.collection = newCollection;
    }

    /**
     * Sorts everything to the left of the index
     * @param index of item to be sorted
     */
	private void sortLeft (int index) {
		ForneymonType temp;
		for(int i = index; i < this.typeSize; i++) {
			if(i > 0 && collection[i].count > collection[i - 1].count) {
	    		temp = collection[i];
				collection[i] = collection[i - 1];
				collection[i - 1] = temp;
			}
		}
	 }

	 /**
     * Sorts everything to the right of the index
     * @param index of item to be sorted
     */
	private void sortRight (int index) {
		ForneymonType temp;
		for(int i = index; i < this.typeSize; i++) {
			if(collection[i + 1] != null && collection[i].count <= collection[i + 1].count) {
	    		temp = collection[i];
				collection[i] = collection[i + 1];
				collection[i + 1] = temp;
	    	}
		}
	 }
}

// ===================================================
// >> [DM] Summary
// ---------------------------------------------------
// Correctness: 43 / 60
// Style: 15 / 15
// Total: 58 / 75
// ---------------------------------------------------
// Your code styling is very good. The main feedback is to correctly implement
// javadoc comments and deep copies. Altogether, great job!
// ===================================================
