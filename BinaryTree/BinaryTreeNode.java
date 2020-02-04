package tree.binary;

public class BinaryTreeNode {
    
    private String data;
    private BinaryTreeNode left, right;
    
    BinaryTreeNode(String s) {
    	this(s, null, null);
    }

    /**
     * Creates a new BinaryTreeNode that can be linked to
     * others to form a tree of arbitrary depth
     * @param s The data to store at this tree node
     */
    BinaryTreeNode(String s, BinaryTreeNode l, BinaryTreeNode r) {
        data = s;
        left = l; 
        right = r;
    }
    
    /**
     * Creates a new BinaryTreeNode storing data String s
     * at the left or right child of the current one.
     * @param s The data to store
     * @param child String "L" or "R" indicating desired child
     */
    public void add (String s, String child) {
        if (child.equals("L")) {
            left = new BinaryTreeNode(s);
        } else if (child.equals("R")) {
            right = new BinaryTreeNode(s);
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Returns the BinaryTreeNode located at the desired
     * location relative to this one.
     * @param child String "L" or "R" indicating desired child
     * @return The BinaryTreeNode at that position
     */
    public BinaryTreeNode getChild (String child) {
        return (child.equals("L")) ? left : right;
    }
    
    /**
     * Returns this node's String data
     * @return The data stored
     */
    public String getString () {
        return data;
    }
    
    /**
     * Doubles the tree rooted at the node on which this method
     * is called, creating a duplicate of each node, storing the
     * duplicate at the left reference of the original, and then
     * moving any previous left-child from the original to the
     * left child of the duplicate. (Toal & Sage assisted)
     */
    
    public void doubleTree () { 
    	 if (this.left != null) this.left.doubleTree();
    	 if (this.right != null) this.right.doubleTree();
    	 this.duplicate(this);
    }
    
    /**
     * Given two Binary Trees rooted at the provided BinaryTreeNodes
     * n1 and n2, determines whether or not the two trees are
     * equivalent (i.e., have the same nodes with the same values in
     * the same locations in the tree).
     * @param n1 The root of tree 1
     * @param n2 The root of tree 2
     * @return Whether or not n1 and n2 represent the same tree
     */
    
	public static boolean sameTree(BinaryTreeNode n1, BinaryTreeNode n2) {
		if (n1 == null && n2 == null) return true; 
		else if (n1 == null || n2 == null) return false;
		else {
			return n1.data == n2.data && sameTree(n1.left, n2.left) && sameTree(n1.right, n2.right);
		}
	}
	
	/**
	 * Duplicates a node and adds it to the left of it
	 * @param node to duplicate
	 */
	private void duplicate(BinaryTreeNode node) {
		node.left = new BinaryTreeNode(node.data, node.left, null);
		
	}
	
	public static void main(String args[]) {
		  BinaryTreeNode tree = new BinaryTreeNode("2");
          tree.add("1", "L");
          tree.left.add("5", "L");
          tree.add("3", "R");
          tree.right.add("4", "L");
          
          BinaryTreeNode tree2 = new BinaryTreeNode("2");
          tree2.add("1", "L");
          tree2.left.add("5", "L");
          tree2.add("3", "R");
          tree2.right.add("4", "L");
          
          System.out.println("This should be true: " + sameTree(tree, tree2));
          tree.doubleTree();
          System.out.println("This should be false: " + sameTree(tree, tree2));
          tree2.doubleTree();
          System.out.println("This should be true: " + sameTree(tree, tree2));
          
	}
	
}
