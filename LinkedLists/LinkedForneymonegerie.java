/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  LinkedForneymonagerie.java
 * @author    :  Andrew Forney (skeleton)
 * @author    :  Salem Tesfu
 * Date       :  2019-10-22
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
package forneymonagerie;

import java.util.NoSuchElementException;


public class LinkedForneymonegerie implements ForneymonagerieInterface {
    
    // Fields
    // -----------------------------------------------------------
    private ForneymonType head, tail;
    private int size, typeSize, modCount;
    
    
    // Constructor
    // -----------------------------------------------------------
    LinkedForneymonegerie () {
    	this.head = null;
    	this.tail = null;
    	this.size = 0; 
    	this.typeSize = 0;
    	this.modCount = 0;
    }
    
    
    // Methods
    // -----------------------------------------------------------
    
    /**
     * Determine if there are any Forneymon in the LinkedFoneymonegerie
     * @return Boolean indicating if the LinkedForneymonegerie is empty
     */
    
    public boolean empty () {
        return (this.size == 0); 
    }
    
    /**
     * Determine how long the LinkedForneymonegerie is
     * @return Integer of the LinkedForneymonegerie's size
     */
    
    public int size () {
        return this.size;
    }
    
    /**
     *  Determine how many of a particular type of Forneymomn are in the 
     *  Linked Forneymonegerie
     *  @return Integer of how many Forneymon of type
     */
     
    public int typeSize () {
        return this.typeSize;
    }
    
    /** Adds a Forneymon to the LinkedForneymonagerie
     * @param toAdd String of the type you are trying to add to the
     * LinkedForneymonagerie
     * @return Boolean whether or not the type was already in the array
     */
    
	public boolean collect(String toAdd) {

		boolean typeExists = true;

		int onlyHead = 0;

		if (this.head == null) {
			ForneymonType newForneymon = new ForneymonType(toAdd, 0);
			this.head = newForneymon;
			this.tail = newForneymon;
			this.typeSize++;
			typeExists = true;
			onlyHead = 1;
		}

		if (contains(toAdd) && this.head != null) {
			for (ForneymonType n = head; n != null; n = n.next) {
				if (isSameType(toAdd, n.type)) {
					this.size++;
					n.count++;
					sort();
					typeExists = false;
				}
			}
		} else {
			ForneymonType newTail = new ForneymonType(toAdd, 1);
			this.tail.next = newTail;
			newTail.prev = this.tail;
			newTail.next = null;
			this.tail = newTail;
			this.size++;
			this.typeSize++;
			typeExists = true;
		}

		if (onlyHead == this.size) {
			typeExists = true;
		}
		this.modCount++;

		return typeExists;
	}
    
    /** Remove one Forneymon of a particular type
     * @param toRemove String of the type of the specific Forneymon
     *  you are trying to take out of the LinkedForneymonagerie
     * @return Boolean whether a forneymon was removed or not
     */
	
	public boolean release(String toRemove) {
		for (ForneymonType n = head; n != null; n = n.next) {
			if (isSameType(toRemove, n.type)) {
				modCount++;
				n.count--;
				siftRight(n);
				if (n.count == 0) {
					releaseType(toRemove);
					System.out.println("Removed all");
				}
				this.size--;
				return true;
			}
		}
		return false;
	}
    
	/** Removes all Forneymon of a given type
     * @param toRemove String of the type you are trying to take out of the
     * LinkedForneymonagerie
     */
	
	public void releaseType(String toNuke) {
		for (ForneymonType n = head; n != null; n = n.next) {
			if (isSameType(toNuke, n.type)) {
				this.size -= n.count;
				if (this.typeSize == 1) {
					this.head = null;
					this.tail = null;
				} else if (n == this.head) {
					this.head = n.next;
					n.next.prev = null;
				} else if (n == this.tail) {
					this.tail = n.prev;
					n.prev.next = null;
				} else {
					ForneymonType temp1 = n.prev;
					ForneymonType temp2 = n.next;
					temp2.prev = temp1;
					temp1.next = temp2;
				}
				this.typeSize--;
				this.modCount++;
			}
		}
	}
    
	/** Determine the number of Forneymon of a particular type.
     * @param toCount String of the type you are counting Forneymon amount
     * @return Integer of the amount of Forneymon that have given type
     */
	
	public int countType(String toCount) {
		int count = 0;
		for (ForneymonType n = head; n != null; n = n.next) {
			if (isSameType(toCount, n.type)) {
				count = n.count;
			}
		}
		return count;
	}
    
	/** Checks if a particular type is in the LinkedForneymonagerie
     * @param toCheck String of the type that might be in the
     * LinkedForneymonagerie
     * @return Boolean whether or not it is in the LinkedForneymonagerie
     */
	
	public boolean contains(String toCheck) {
		for (ForneymonType n = head; n != null; n = n.next) {
			if (isSameType(toCheck, n.type)) {
				return true;
			}
		}
		return false;
	}
    
	/** Determine the Forneymon type of the most recently
     * modified Forneymon Type of a particular rarity
     * @param count Integer of the rarity to seek
     * @return String of the type of the last modified
     * Forneymon of that particular type
     */
	
	public String mostRecentAtRarity(int count) {
		for (ForneymonType n = tail; n != null; n = n.prev) {
			if (count == n.count) {
				return n.type;
			}
		}
		return null;
	}
    
	/** Create a deep copy of another LinkedForneymonagerie
     * @return An identical copy of a LinkedForneymonagerie
     */
	
	public LinkedForneymonegerie clone() {
		LinkedForneymonegerie clone = new LinkedForneymonegerie();
		for (ForneymonType n = this.head; n != null; n = n.next) {
			clone.append(n.type, n.count);
		}

		clone.size = this.size;
		clone.typeSize = this.typeSize;
		clone.modCount = this.modCount;
		return clone;
	}
    
    /** Swap the contents of two LinkedForneymonagerie
     * @param Another LinkedForneymonagerie to exchange with
     */
	
	public void trade(LinkedForneymonegerie other) {
		LinkedForneymonegerie temp = new LinkedForneymonegerie();
		temp.size = other.size;
		temp.typeSize = other.typeSize;
		temp.head = other.head;
		temp.tail = other.tail;
		temp.modCount = other.modCount;

		other.size = this.size;
		other.typeSize = this.typeSize;
		other.head = this.head;
		other.tail = this.tail;
		other.modCount = this.modCount;

		this.size = temp.size;
		this.typeSize = temp.typeSize;
		this.head = temp.head;
		this.tail = temp.tail;
		this.modCount = temp.modCount;

		this.modCount++;
		other.modCount++;
	}
    
    /** Return the Forneymon at a particular place in regards to
     * the amount of all Forneymon in the LinkedForneymonagerie
     * @param n int of the position of a Forneymon
     * @return String of the type of the Forneymon at that
     * particular position
     */
	
	public String nth(int n) {
		if (n >= this.size) {
			throw new IllegalArgumentException("argument beyond size");
		}

		int count = -1;
		String result = "";
		for (ForneymonType current = head; current != null; current = current.next) {
			for (int i = 0; i < current.count; i++) {
				count++;
				if (n == count) {
					result = current.type;
				}
			}
		}
		return result;
	}
    
	/**
	 * Returns a new iterator
	 * @return iterator
	 */
	
	public LinkedForneymonegerie.Iterator getIterator() {
		return new Iterator(this);
	}
	
	/** Create a string representation of the calling
     * LinkedForneymonagerie
     * @return string of given LinkedForneymonagerie
     */

    @Override
    public String toString () {
        String[] result = new String[typeSize];
        int i = 0;
        for (ForneymonType n = head; n != null; n = n.next) {
            result[i] = "\"" + n.type + "\": " + n.count;
            i++;
        }
        return "[ " + String.join(", ", result) + " ]";
    }
    
    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    /** Create a new LinkedForneymonagerie that contains all the Forneymon that are
     * in the first LinkedForneymonagerie but not the second one
     * @param Two LinkedForneymonageries to compare
     * @return new LinkedForneymonagerie that consists of the differences
     */
    
	public static LinkedForneymonegerie diffMon(LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
		LinkedForneymonegerie different = new LinkedForneymonegerie();

		for (ForneymonType n = y1.head; n != null; n = n.next) {
			for (ForneymonType n2 = y2.head; n2 != null; n2 = n2.next) {
				if (y2.contains(n.type) && n.count > n2.count) {
					int amount = Math.abs(n.count - n2.count);
					different.append(n.type, amount);
				} else if (!y2.contains(n.type)) {
					different.append(n.type, n.count);

				}
			}
		}

		return different;

	}
    
	/** Determines if two LinkedForneymonageries have the same Forneymon Types and
     * counts regardless of order
     * @param Two LinkedForneymonageries to compare
     * @return Boolean whether the two are the same or not
     */
	
	public static boolean sameCollection(LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
		int sameCount = 0;

		if (y1.typeSize != y2.typeSize || y1.size != y2.size) {
			return false;
		}
		for (ForneymonType n = y1.head; n != null; n = n.next) {
			for (ForneymonType n2 = y2.head; n2 != null; n2 = n2.next) {
				if (n.type.equals(n2.type) && (n.count == n2.count)) {
					sameCount++;
				}
			}
		}
		return (sameCount == y1.typeSize());
	}
    
    
    // Private helper methods
    // -----------------------------------------------------------

    
    /**
     * Checks to see if two Forneymon types are the same
     * @param Two strings of Forneymon types
     * @return Boolean for verification
     */
	private boolean isSameType(String t1, String t2) {
		return (t1.contentEquals(t2));
	}
	
	/**
	 * Sorts the whole LinkedForneymonagerie to account for increase in count
	 * Re-implemented from https://www.javatpoint.com/program-to-sort-the-elements-of-the-doubly-linked-list
	 */
	private void sort() {
		ForneymonType current = null;
		ForneymonType afterCurrent = null;
		String tempType = "";
		int tempCount = 0;

		for (current = head; current.next != null; current = current.next) {
			for (afterCurrent = current.next; afterCurrent != null; afterCurrent = afterCurrent.next) {
				if (current.count < afterCurrent.count) {
					tempCount = current.count;
					current.count = afterCurrent.count;
					afterCurrent.count = tempCount;

					tempType = current.type;
					current.type = afterCurrent.type;
					afterCurrent.type = tempType;
				}
			}
		}
		repairRef();
	}
	
	/**
	 * Swaps to the right based on ordering 
	 * @param toCheck String of type to check
	 */
	
	private void siftRight(ForneymonType toCheck) {
		ForneymonType current = toCheck;
		while (current.next != null) {
			if (current.count <= current.next.count && current.next != null) {
				swapLeft(current, current.next);
			}
			if (current.next == null) {
				break;
			}
		}
	}
	
	/**
	 * Swaps to the left based on ordering 
	 * @param toCheck String of type to check
	 */
	
	private void siftLeft(ForneymonType toCheck) {
		ForneymonType current = toCheck;
		while (current.prev != null) {
			System.out.println(current.type);
			if (current.count > current.prev.count && current.prev != null) {
				System.out.println("cheetos");
				swapLeft(current.prev, current);
			}
			if (current.prev == null) {
				break;
			}
		}
	}
	
	/**
	 * Finds the index of a particular type of Forneymon
	 * @param toFind type of desired Forneymon
	 * @return relative index of where the Forneymon is
	 */
	
	private int getIndex(String toFind) {
		int index = 0;
		int count = 0;
		for (ForneymonType n = head; n != null; n = n.next) {
			if (isSameType(n.type, toFind)) {
				index = count - 1;
			} else {
				count++;
			}
		}
		return index;
	}
	
	/**
	 * Returns the ForneymonType at given index
	 * @param index desired index
	 * @return Forneymon at desired index
	 */
	
	private ForneymonType counter(int index) {
		int count = 0;
		ForneymonType current = head;
		while (current.next != null && count != index) {
			current = current.next;
			count++;
		}
		return current;
	}
	
	/**
	 * Fix the references when edge cases occur
	 */
	
	private void repairRef() {
		if (this.typeSize() == 1) {
			this.head = this.tail;
			this.tail = this.head;
		} else if (empty()) {
			this.head = null;
			this.tail = null;
		} else {
			this.head = counter(0);
			this.tail = counter(typeSize - 1);
		}
	}
	
	/**
	 * Adds specified forneymontype
	 * @param toAppend sting of type to add
	 * @param amount how many of type to add
	 */
	
	private void append(String toAppend, int amount) {
		ForneymonType add = new ForneymonType(toAppend, amount);
		if (this.head == null) {
			this.head = add;
			return;
		}
		ForneymonType current = this.head;
		while (current.next != null) {
			current = current.next;
		}
		current.next = add;

	}
	

	/**
	 * Swaps two given nodes
	 * @param swap1 left node to swap
	 * @param swap2 right node to swap
	 */
	
	private void swapLeft(ForneymonType swap1, ForneymonType swap2) {
		ForneymonType temp1, temp2;
		temp1 = swap1.prev;
		temp2 = swap2.next;
		swap1.next = temp2;
		swap1.prev = swap2;
		swap2.next = swap1;
		swap2.prev = temp1;

		if (temp1 != null) {
			temp1.next = swap2;
		}
		if (temp2 != null) {
			temp2.prev = swap1;
		}

		if (swap1 == head) {
			head = swap2;
			swap2.prev = null;
		}
		if (swap2 == tail) {
			tail = swap1;
		}

	}
	
    // Inner Classes
    // -----------------------------------------------------------
    
    public class Iterator implements LinkedForneymonegerieIteratorInterface {
        LinkedForneymonegerie owner;
        ForneymonType current;
        int itModCount;
        int tracker;
        
		Iterator(LinkedForneymonegerie y) {
			owner = y;
			current = y.head;
			itModCount = modCount;
			tracker = current.count - 1;
		}
        
        /**
         * Checks to see us there is another forneymon after the current
         * @return boolean whether or not there is another forneymon
         */
		
		public boolean hasNext() {
			return ((tracker > 0 || current.next != null) && isValid());
		}
      
		/**
         * Checks to see us there is another forneymon before the current
         * @return boolean whether or not there is another forneymon
         */
		
		public boolean hasPrev() {
			return ((tracker < current.count - 1 || current.prev != null) && isValid());
		}
        
		/**
         * Checks to see pif the iterator itModCount agrees with owner modCount
         * @return boolean whether or not modCounts agree
         */
		
		public boolean isValid() {
			return (itModCount == owner.modCount);
		}
        
		/**
		 * Return the type of the current forneymon
		 * @return string type of current
		 */
		
		public String getType() {
			if (!isValid()) {
				throw new IllegalStateException();
			}
			return current.type;
		}

		/**
		 * Advances the iterator to the next Forneymon
		 */
		
		public void next() {
			if (!isValid()) {
				throw new IllegalStateException();
			}
			if (hasNext()) {
				if (tracker == 0) {
					current = current.next;
					tracker = current.count - 1;
				} else {
					tracker--;
				}
			} else {
				throw new NoSuchElementException();
			}
		}
        
		/**
		 * Reverses the iterator to the previous Forneymon
		 */
		
		public void prev() {
			if (!isValid()) {
				throw new IllegalStateException();
			}
			if (!hasPrev()) {
				throw new NoSuchElementException();
			}
			if (tracker != current.count - 1) {
				tracker++;
			} else {
				current = current.prev;
				tracker = 0;
			}
		}
        
		/**
		 * Replaces all Forneymon of ForneymonType that iterator is pointing to given
		 * in parameter
		 * @param toReplaceWith string to replace
		 */
		
		public void replaceAll(String toReplaceWith) {
			if (!isValid()) {
				throw new IllegalStateException();
			}
			if (contains(toReplaceWith)) {
				counter(getIndex(toReplaceWith)).count += current.count;
			} else {
				current.type = toReplaceWith;
			}
			sort();
			modCount++;
			itModCount++;

		}

	}
    
	private class ForneymonType {
		ForneymonType next, prev;
		String type;
		int count;

		ForneymonType(String t, int c) {
			type = t;
			count = c;
		}
	}
 
}