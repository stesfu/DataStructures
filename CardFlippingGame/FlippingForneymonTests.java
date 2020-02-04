/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  FlippingForneymonCardTests.java
 * Purpose    :  A JUnit tester for the FlippingForneymonCard game 
 * @author    :  Salem Tesfu 
 * Date       :  2019-09-11
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

package forneymon.cardgame;

import static org.junit.Assert.*;
import org.junit.Test;

public class FlippingForneymonTests {
	
	@Test
    public void testFlip() {
		
		FlippingForneymonCard toalymon = new FlippingForneymonCard("toal", "Dampymon", false);
        assertEquals(true,  toalymon.flip());
        
        FlippingForneymonCard missingNu = new FlippingForneymonCard();
        assertEquals(false,  missingNu.flip());
    }

    @Test
    public void testToString() {
    	
        FlippingForneymonCard beejymon = new FlippingForneymonCard("beej", "Leafymon", true);
        assertEquals("?: ?", beejymon.toString());
        
        beejymon.flip();
        assertEquals("Leafymon: beej", beejymon.toString());
    }
    
    @Test
    public void testGetName() {
    	
    	 FlippingForneymonCard quicheymon = new FlippingForneymonCard("kEEEEEEEEEEEEESHA", "Burnymon", false);
    	 assertEquals("kEEEEEEEEEEEEESHA", quicheymon.getName());
    }
    
    @Test
    public void testGetType() {
    	
    	FlippingForneymonCard tesfumon = new FlippingForneymonCard("Salem THE DESTROYER OF SOULS", "Dampymon", false);
   	    assertEquals("Dampymon", tesfumon.getType());
    }
    
    @Test
    public void testMatch() {
    	
   	    FlippingForneymonCard popeymon = new FlippingForneymonCard("francis", "Burnymon", false); //works with true and not false
 	    FlippingForneymonCard rocketmon = new FlippingForneymonCard("elton", "Burnymon", false);
	    assertEquals(0, popeymon.match(rocketmon));
	    
	    FlippingForneymonCard DrFourKnee = new FlippingForneymonCard("androo", "Dampymon", false); //works with true and not false
 	    FlippingForneymonCard DrFiveElbow = new FlippingForneymonCard("androo", "Dampymon", false);
	    assertEquals(1, DrFourKnee.match(DrFiveElbow));
	    
	    FlippingForneymonCard keckymon = new FlippingForneymonCard("LaBanthony", "Burnymon", true); //works with true and not false
    	FlippingForneymonCard doolymon = new FlippingForneymonCard("Jerome the Feard", "Leafymon", true);
   	    assertEquals(2, keckymon.match(doolymon));
    }

}
