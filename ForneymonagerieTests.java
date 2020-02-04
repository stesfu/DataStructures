/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  ForneymonagerieTests.java
 * Purpose    :  Test the methods from Forneymonagerie.java
 * @author    :  Andrew Francis Forney  
 * @author    :  Salem Tesfu 
 * Date       :  2019-09-27
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

package forneymonagerie;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ForneymonagerieTests {

    // =================================================
    // Test Configuration
    // =================================================

    // Used as the basic empty menagerie to test; the @Before
    // method is run before every @Test
    Forneymonagerie fm;
    @Before
    public void init () {
        fm = new Forneymonagerie();
    }


    // =================================================
    // Unit Tests
    // =================================================

    @Test
    public void testSize() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.size());
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
    }
    
    @Test
    public void testEmpty() {
    	fm.collect("Dampymon");
    	fm.release("Dampymon");
    	assertEquals(true, fm.empty()); 
    	fm.collect("Burnymon"); 
    	assertEquals(false, fm.empty()); 
    }

    @Test
    public void testTypeSize() {
    	  fm.collect("Burnymon");
          fm.collect("Dampymon");
          fm.release("Burnymon");
          assertEquals(1, fm.typeSize());
          fm.release("Dampymon");
          assertEquals(0, fm.typeSize());
//    	 fm.collect("Burnymon");
//         fm.collect("Burnymon");
//         fm.collect("Burnymon");
//         assertEquals(1, fm.typeSize());
//        fm.collect("Damdpymon");
//        fm.collect("Dampymon");
//        fm.collect("Dampymon");
//        assertEquals(1, fm.typeSize());
//        fm.collect("Burnymon");
//        assertEquals(2, fm.typeSize());
    }

    @Test
    public void testCollect() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Alabama");
        fm.collect("Alaska");
        fm.collect("Arizona");
        fm.collect("Arkansas");
        fm.collect("California");
        fm.collect("Colorado");
        fm.collect("Conneticut");
        fm.collect("Delaware");
        fm.collect("Florida");
        fm.collect("Georgia");
        fm.collect("Hawaii");
        fm.collect("Idaho");
        fm.collect("Illinois");
        fm.collect("Indiana");
        fm.collect("Iowa");
        fm.collect("Kansas");
        fm.collect("Kentucky");
        fm.collect("Louisiana");
        fm.collect("Maine");
        fm.collect("Burnymon");
        fm.collect("Leafymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
    }
    
    @Test
    public void testToString() {
    	fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.toString();
    }

    @Test
    public void testRelease() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.size());
        assertEquals(1, fm.typeSize());
        fm.release("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
        assertEquals(false, fm.release("Burnymon"));
    }

    @Test
    public void testReleaseType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
        assertEquals(2, fm.typeSize());
        fm.releaseType("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
        fm.releaseType("Burnymon");
        assertEquals(0, fm.size());
        assertEquals(0, fm.typeSize());
        fm.collect("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
        fm.releaseType("Squishymon"); 
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
       
    }

    @Test
    public void testCountType() {
//        fm.collect("Dampymon");
//        fm.collect("Dampymon");
//        fm.collect("Burnymon");
//        assertEquals(2, fm.countType("Dampymon"));
//        assertEquals(1, fm.countType("Burnymon"));
//        assertEquals(0, fm.countType("forneymon"));
    	 fm.collect("Burnymon");
         fm.collect("Dampymon");
         fm.collect("Burnymon");
         fm.collect("Dampymon");
         assertEquals(2, fm.countType("Burnymon"));
         assertEquals(2, fm.countType("Dampymon"));
         fm.releaseType("Burnymon");
         assertEquals(0, fm.countType("Burnymon"));
         fm.release("Dampymon");
         System.out.println(fm.countType("Dampymon"));
         assertEquals(1, fm.countType("Dampymon"));
         System.out.println("fuck");
    }

    @Test
    public void testContains() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
        assertFalse(fm.contains("forneymon"));
    }

    @Test
    public void testNth() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        assertEquals("Dampymon", fm.nth(0));
        assertEquals("Dampymon", fm.nth(1));
        assertEquals("Burnymon", fm.nth(2));
        assertEquals("Zappymon", fm.nth(3));
     

        fm.release("Dampymon");
        assertEquals("Burnymon", fm.nth(0));
        assertEquals("Zappymon", fm.nth(1));
        assertEquals("Dampymon", fm.nth(2));
    }

    @Test
    public void testMostRecentAtRarity() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        assertEquals("Dampymon", fm.mostRecentAtRarity(2));
        assertEquals(null, fm.mostRecentAtRarity(1));
    }

    @Test
    public void testClone() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        Forneymonagerie dolly = fm.clone();
        assertEquals(dolly.countType("Dampymon"), 2);
        assertEquals(dolly.countType("Burnymon"), 1);
        dolly.collect("Zappymon");
        assertFalse(fm.contains("Zappymon"));
    }

    @Test
    public void testTrade() {
        Forneymonagerie fm1 = new Forneymonagerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect("Zappymon");
        fm2.collect("Leafymon");
        fm1.trade(fm2);
        assertTrue(fm1.contains("Zappymon"));
        assertTrue(fm1.contains("Leafymon"));
        assertTrue(fm2.contains("Dampymon"));
        assertTrue(fm2.contains("Burnymon"));
        assertFalse(fm1.contains("Dampymon"));
    }

    @Test
    public void testDiffMon() {
        Forneymonagerie fm1 = new Forneymonagerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect("Dampymon");
        fm2.collect("Zappymon");
        Forneymonagerie fm3 = Forneymonagerie.diffMon(fm1, fm2);
        assertEquals(fm3.countType("Dampymon"), 1);
        assertEquals(fm3.countType("Burnymon"), 1);
        assertFalse(fm3.contains("Zappymon"));
        fm3.collect("Leafymon");
        assertFalse(fm1.contains("Leafymon"));
        assertFalse(fm2.contains("Leafymon"));
    }

    @Test
    public void testSameForneymonegerie() {
        Forneymonagerie fm1 = new Forneymonagerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect("Burnymon");
        fm2.collect("Dampymon");
        fm2.collect("Dampymon");
        assertTrue(Forneymonagerie.sameCollection(fm1, fm2));
        assertTrue(Forneymonagerie.sameCollection(fm2, fm1));
        fm2.collect("Leafymon");
        assertFalse(Forneymonagerie.sameCollection(fm1, fm2));
    }

}
