/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  WebNavigator.java
 * Purpose    :  Web browser that allows the user to view sites, go forward, and backwards
 * @author    :  Salem Tesfu 
 * Date       :  2019-09-30
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
package webnav;
import java.util.*;  

/**
 * Web Navigator used to track which URLs a user is currently
 * or was previously browsing, as well as tools for updating the
 * currently viewed based on their session history.
 */
public class WebNavigator {

    private String current;
  	LinkedList<String> surfinSalem = new LinkedList<String>(); 
    
    WebNavigator () {
    	this.current = null; 
    }
    
    /**
     *  Visits the current site, clears the forward history,
     *  and records the visited site in the back history
     *  @param site The new site being visited
     */
    public void visit (String site) {
    	if (this.current != null) {
    		surfinSalem.offerFirst(this.current);
    	}
        this.current = site; 
        surfinSalem.add(this.current); 
    }
    
    /**
     *  Changes the current site to the one that was last
     *  visited in the order on which visit was called on it
     */
    public void back () {
    	int index = surfinSalem.indexOf(this.current); 
    	if(surfinSalem.indexOf(this.current) > 0) {
    		this.current = surfinSalem.get(index - 1); 
    	} else { 
    		this.current = surfinSalem.get(index);
    	}
    }
    
    /**
     * Changes the current site to the one that was last
     * returned to via back()
     */
    public void forw () {
    	int index = surfinSalem.indexOf(this.current); 
    	if(surfinSalem.indexOf(this.current) <= (surfinSalem.size() - 1)) {
    		  this.current = surfinSalem.getLast();
    	}else { 
    		this.current = surfinSalem.get(index); 
    	}    
    }
    
    /**
     * Returns the String representing the site that the navigator
     * is currently at
     * @return The current site's URL
     */
    public String getCurrent () {
        return this.current;
    }
    
    public static void main (String[] args) {
    	 // Example Interaction
    	  WebNavigator navi = new WebNavigator();
    	  
    	  navi.visit("www.google.com");
    	  System.out.println(navi.getCurrent());
    	  // www.google.com
    	  
    	  navi.visit("www.reddit.com");
    	  System.out.println(navi.getCurrent());
    	  // www.reddit.com
    	  
    	  navi.back();
    	  System.out.println(navi.getCurrent());
    	  // www.google.com
    	  
    	  navi.back();
    	  System.out.println(navi.getCurrent());
    	  // www.google.com
    	  
    	  navi.forw();
    	  System.out.println(navi.getCurrent());
    	  // www.reddit.com
    	  
    	  navi.forw();
    	  System.out.println(navi.getCurrent());
    	  // www.reddit.com
    	  
    	  navi.visit("www.facebook.com");
    	  System.out.println(navi.getCurrent());
    	  // www.facebook.com
    	  
    	  navi.back();
    	  System.out.println(navi.getCurrent());
    	  // www.reddit.com
    	  
    	  // Visiting another site after moving back wipes
    	  // the "forward" collection
    	  navi.visit("www.amazon.com");
    	  System.out.println(navi.getCurrent());
    	  // www.amazon.com
    	  
    	  // See? doesn't go back to reddit
    	  navi.forw();
    	  System.out.println(navi.getCurrent());
    }
}
