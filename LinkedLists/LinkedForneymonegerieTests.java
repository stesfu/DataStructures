package forneymonagerie;

import static org.junit.Assert.*;
import java.util.NoSuchElementException;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.rules.TestWatcher;

public class LinkedForneymonegerieTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    static private int START_SIZE = 16;
    
    // Global timeout to prevent infinite loops from
    // crashing the test suite
    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);
    
    // Each time you pass a test, you get a point! Yay!
    // [!] Requires JUnit 4+ to run
    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            passed++;
        }
    };
    
    // Grade record-keeping
    static int possible = 0, passed = 0;
    
    // Used as the basic empty Forneymonagerie to test; the @Before
    // method is run before every @Test
    LinkedForneymonegerie fm;
    @Before
    public void init () {
        possible++;
        fm = new LinkedForneymonegerie();
    }
    
    // Used for grading, reports the total number of tests
    // passed over the total possible
    @AfterClass
    public static void gradeReport () {
        System.out.println("============================");
        System.out.println("Tests Complete");
        System.out.println(passed + " / " + possible + " passed!");
        if ((1.0 * passed / possible) >= 0.9) {
            System.out.println("[!] Nice job!"); // Automated acclaim!
        }
        System.out.println("============================");
    }
    
    
    // =================================================
    // Unit Tests
    // =================================================
    // For grading purposes, every method has ~3 tests, 
    // weighted equally and totaled for the score.
    // The tests increase in difficulty such that the
    // basics are unlabeled and harder tiers are tagged
    // t1, t2, t3, ... easier -> harder
    
    
    // Initialization Tests
    // -------------------------------------------------
    @Test
    public void testInit() {
        assertTrue(fm.empty());
        assertEquals(0, fm.size());
    }

    // Basic Tests
    // -------------------------------------------------

    @Test
    public void testSize() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        assertEquals(2, fm.size());
        fm.collect("Dampymon");
        assertEquals(3, fm.size());
    }
    @Test
    public void testSize_t1() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
    }
    @Test
    public void testSize_t2() {
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        assertEquals(3, fm.size());
    }

    @Test
    public void testTypeSize() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        assertEquals(1, fm.typeSize());
        fm.collect("Dampymon");
        assertEquals(2, fm.typeSize());
    }
    public void testTypeSize_t1() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        assertEquals(1, fm.typeSize());
    }
    @Test
    public void testTypeSize_t2() {
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        assertEquals(3, fm.typeSize());
    }
    @Test
    public void testTypeSize_t3() {
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.release("Burnymon");
        assertEquals(1, fm.typeSize());
        fm.release("Dampymon");
        assertEquals(0, fm.typeSize());
    }

    // Forneymonagerie Manipulation Tests
    // -------------------------------------------------
    @Test
    public void testCollect() {
        boolean collected = fm.collect("Burnymon");
        assertTrue(collected);
        fm.collect("Burnymon");
        collected = fm.collect("Burnymon");
        assertFalse(collected);
        fm.collect("Dampymon");
        assertTrue(fm.contains("Burnymon"));
        assertTrue(fm.contains("Dampymon"));
    }
    @Test
    public void testCollect_t1() {
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Burnymon"));
        assertTrue(fm.contains("Dampymon"));
        assertEquals(3, fm.size());
        assertEquals(2, fm.typeSize());
    }
    @Test
    public void testCollect_t2() {
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.release("Dampymon");
        assertTrue(fm.contains("Burnymon"));
        assertFalse(fm.contains("Dampymon"));
        assertEquals(2, fm.size());
        assertEquals(1, fm.typeSize());
    }
    @Test
    public void testCollect_t3() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.collect("Zappymon");
        fm.release("Dampymon");
        assertEquals(4, fm.size());
        assertEquals(2, fm.typeSize());
    }
    @Test
    public void testCollect_t4() {
        for (int i = 0; i < 1000; i++) {
            fm.collect("" + i);
        }
        assertEquals(1000, fm.size());
        assertEquals(1000, fm.typeSize());
    }
    @Test
    public void testCollect_t5() {
        for (int i = 0; i < 1000; i++) {
            fm.collect("SAMESIES");
        }
        assertEquals(1000, fm.size());
        assertEquals(1, fm.typeSize());
    }
    @Test
    public void testCollect_t6() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.collect("Zappymon");
        fm.release("Dampymon");
        assertEquals(4, fm.size());
        assertEquals(2, fm.typeSize());
    }

    @Test
    public void testRelease() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        assertEquals(2, fm.size());
        assertEquals(1, fm.typeSize());
        boolean released = fm.release("Burnymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
        assertTrue(released);
    }
    @Test
    public void testRelease_t1() {
        fm.collect("Dampymon");
        boolean released = fm.release("Dampymon");
        assertEquals(0, fm.size());
        assertTrue(released);
        assertFalse(fm.contains("Dampymon"));
        released = fm.release("Dampymon");
        assertFalse(released);
    }
    @Test
    public void testRelease_t2() {
        fm.release("Dampymon");
        fm.collect("Dampymon");
        fm.release("uni");
        assertEquals(1, fm.size());
        assertTrue(fm.contains("Dampymon"));
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.release("Dampymon");
        assertEquals(2, fm.size());
        assertFalse(fm.contains("Dampymon"));
    }
    @Test
    public void testRelease_t3() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.release("Dampymon");
        assertEquals(4, fm.size());
        assertEquals(2, fm.typeSize());
        fm.collect("Zappymon");
        fm.collect("Zappymon");
        boolean released = fm.release("Burnymon");
        assertTrue(released);
        released = fm.release("Burnymon");
        assertTrue(released);
        assertEquals(4, fm.size());
        assertEquals(2, fm.typeSize());
        assertFalse(fm.contains("Burnymon"));
    }

    @Test
    public void testReleaseAll() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        assertEquals(3, fm.size());
        assertEquals(2, fm.typeSize());
        fm.releaseType("Burnymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
    }
    @Test
    public void testReleaseAll_t1() {
        fm.releaseType("Dampymon");
        fm.collect("Dampymon");
        fm.releaseType("Dampymon");
        assertEquals(0, fm.size());
        assertFalse(fm.contains("Dampymon"));
    }
    @Test
    public void testReleaseAll_t2() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.releaseType("Dampymon");
        fm.releaseType("Burnymon");
        assertEquals(1, fm.size());
        assertFalse(fm.contains("Dampymon"));
    }
    @Test
    public void testReleaseAll_t3() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.releaseType("Burnymon");
        assertEquals(3, fm.size());
        assertEquals(2, fm.typeSize());
        assertFalse(fm.contains("Burnymon"));
        fm.releaseType("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
        fm.releaseType("Zappymon");
        assertEquals(0, fm.size());
        assertEquals(0, fm.typeSize());
    }

    @Test
    public void testCount() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.countType("Burnymon"));
        assertEquals(1, fm.countType("Dampymon"));
        assertEquals(0, fm.countType("forneymon"));
    }
    @Test
    public void testCount_t1() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        assertEquals(3, fm.countType("Burnymon"));
        fm.releaseType("Burnymon");
        assertEquals(0, fm.countType("Burnymon"));
    }
    @Test
    public void testCount_t2() {
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.countType("Burnymon"));
        assertEquals(2, fm.countType("Dampymon"));
        fm.releaseType("Burnymon");
        assertEquals(0, fm.countType("Burnymon"));
        fm.release("Dampymon");
        assertEquals(1, fm.countType("Dampymon"));
    }

    @Test
    public void testContains() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        assertTrue(fm.contains("Burnymon"));
        assertTrue(fm.contains("Dampymon"));
        assertFalse(fm.contains("forneymon"));
    }
    // This is tested pretty much everywhere so...

    @Test
    public void testNth() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        String[] answers = {
            "Burnymon",
            "Burnymon",
            "Dampymon",
            "Zappymon"
        };
        for (int i = 0; i < fm.size(); i++) {
            assertEquals(answers[i], fm.nth(i));
        }
    }
    @Test
    public void testNth_t1() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        String[] answers = {
            "Dampymon",
            "Dampymon",
            "Burnymon",
            "Burnymon",
            "Zappymon",
            "Zappymon"
        };
        for (int i = 0; i < fm.size(); i++) {
            assertEquals(answers[i], fm.nth(i));
        }
    }
    @Test
    public void testNth_t2() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.release("Dampymon");
        fm.releaseType("Burnymon");
        String[] answers = {
            "Zappymon",
            "Zappymon",
            "Dampymon"
        };
        for (int i = 0; i < fm.size(); i++) {
            assertEquals(answers[i], fm.nth(i));
        }
    }
    @Test
    public void testNth_t3() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Zappymon");
        String[] answers = {
                "Dampymon",
                "Dampymon",
                "Dampymon",
                "Burnymon",
                "Burnymon",
                "Zappymon",
                "Zappymon"
        };
        for (int i = 0; i < fm.size(); i++) {
            assertEquals(answers[i], fm.nth(i));
        }
    }
    @Test
    public void testNth_t4() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Zappymon");
        fm.collect("Zappymon");
        fm.collect("Zappymon");
        String[] answers = {
                "Zappymon",
                "Zappymon",
                "Zappymon",
                "Zappymon",
                "Dampymon",
                "Dampymon",
                "Dampymon",
                "Burnymon",
                "Burnymon"
        };
        for (int i = 0; i < fm.size(); i++) {
            assertEquals(answers[i], fm.nth(i));
        }
    }
    @Test
    public void testNth_t5() {
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Leafymon");
        fm.collect("Burnymon");
        fm.collect("Leafymon");
        fm.release("Dampymon");
        fm.collect("Zappymon");
        fm.collect("Zappymon");
        String[] answers = {
                "Zappymon",
                "Zappymon",
                "Zappymon",
                "Zappymon",
                "Burnymon",
                "Burnymon",
                "Leafymon",
                "Leafymon",
                "Dampymon",
                "Dampymon"
        };
        for (int i = 0; i < fm.size(); i++) {
            assertEquals(answers[i], fm.nth(i));
        }
    }
    @Test
    public void testNth_t6() {
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Leafymon");
        fm.collect("Burnymon");
        fm.collect("Leafymon");
        fm.releaseType("Zappymon");
        fm.collect("Zappymon");
        fm.collect("Zappymon");
        String[] answers = {
                "Dampymon",
                "Dampymon",
                "Burnymon",
                "Burnymon",
                "Leafymon",
                "Leafymon",
                "Zappymon",
                "Zappymon"
        };
        for (int i = 0; i < fm.size(); i++) {
            assertEquals(answers[i], fm.nth(i));
        }
        // Just to make sure nth isn't a mutator
        for (int i = 0; i < fm.size(); i++) {
            assertEquals(answers[i], fm.nth(i));
        }
    }

    @Test
    public void testMostRecentAtRarity() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.mostRecentAtRarity(1));
        assertEquals("Burnymon", fm.mostRecentAtRarity(2));
    }
    @Test
    public void testMostRecentAtRarity_t1() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.release("Burnymon");
        assertEquals("Burnymon", fm.mostRecentAtRarity(1));
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        assertEquals("Dampymon", fm.mostRecentAtRarity(2));
        fm.collect("Burnymon");
        assertEquals("Burnymon", fm.mostRecentAtRarity(2));
        fm.collect("Burnymon");
        assertEquals("Dampymon", fm.mostRecentAtRarity(2));
        assertEquals("Burnymon", fm.mostRecentAtRarity(3));
    }
    @Test
    public void testMostRecentAtRarity_t2() {
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        assertEquals(null, fm.mostRecentAtRarity(3));
        assertEquals(null, fm.mostRecentAtRarity(1));
        assertEquals("Dampymon", fm.mostRecentAtRarity(2));
        fm.releaseType("Zappymon");
        assertEquals("Dampymon", fm.mostRecentAtRarity(2));
        fm.releaseType("Dampymon");
        assertEquals("Burnymon", fm.mostRecentAtRarity(2));
    }
    @Test
    public void testMostRecentAtRarity_t3() {
        assertEquals(null, fm.mostRecentAtRarity(1));
        assertEquals(null, fm.mostRecentAtRarity(2));
    }

    // Inter-Forneymonagerie Tests
    // -------------------------------------------------
    @Test
    public void testClone() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        LinkedForneymonegerie dolly = fm.clone();
        assertEquals(2, dolly.countType("Burnymon"));
        assertEquals(1, dolly.countType("Dampymon"));
        dolly.collect("Zappymon");
        assertFalse(fm.contains("Zappymon"));
    }
    @Test
    public void testClone_t1() {
        LinkedForneymonegerie dolly = fm.clone();
        fm.collect("Dampymon");
        assertFalse(dolly.contains("Dampymon"));
    }
    @Test
    public void testClone_t2() {
        fm.collect("Dampymon");
        LinkedForneymonegerie dolly = fm.clone();
        dolly.collect("Burnymon");
        LinkedForneymonegerie superDolly = dolly.clone();
        superDolly.collect("Zappymon");
        assertTrue(superDolly.contains("Dampymon"));
        assertTrue(superDolly.contains("Burnymon"));
        assertFalse(dolly.contains("Zappymon"));
    }
    @Test
    public void testClone_t3() {
        for (int i = 0; i < START_SIZE+2; i++) {
            fm.collect("FT" + i);
        }
        LinkedForneymonegerie dolly = fm.clone();
        for (int i = 0; i < START_SIZE+2; i++) {
            assertTrue(dolly.contains("FT" + i));
        }
    }
    @Test
    public void testClone_t4() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        LinkedForneymonegerie dolly = fm.clone();
        fm.release("Burnymon");
        String[] answers = {
            "Burnymon",
            "Burnymon",
            "Zappymon",
            "Zappymon",
            "Dampymon"
        };
        for (int i = 0; i < dolly.size(); i++) {
            assertEquals(answers[i], dolly.nth(i));
        }
    }

    @Test
    public void testTrade() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Burnymon");
        fm1.collect("Burnymon");
        fm1.collect("Dampymon");
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm2.collect("yo");
        fm2.collect("sup");
        fm1.trade(fm2);
        assertTrue(fm1.contains("yo"));
        assertTrue(fm1.contains("sup"));
        assertTrue(fm2.contains("Burnymon"));
        assertTrue(fm2.contains("Dampymon"));
        assertFalse(fm1.contains("Burnymon"));
    }
    @Test
    public void testTrade_t1() {
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm.collect("Dampymon");
        fm2.trade(fm);
        assertTrue(fm.empty());
        assertFalse(fm2.empty());
    }
    @Test
    public void testTrade_t2() {
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        LinkedForneymonegerie fm3 = new LinkedForneymonegerie();
        fm2.collect("Dampymon");
        fm.collect("Burnymon");
        fm2.trade(fm);
        fm3.trade(fm2);
        assertTrue(fm2.empty());
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm3.contains("Burnymon"));
        fm.collect("Zappymon");
        assertFalse(fm2.contains("Zappymon"));
    }
    @Test
    public void testTrade_t3() {
        fm.collect("Dampymon");
        fm.trade(fm);
        assertTrue(fm.contains("Dampymon"));
        assertEquals(1, fm.size());
    }
    @Test
    public void testTrade_t4() {
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm.trade(fm2);
        assertTrue(fm.empty());
        assertTrue(fm2.empty());
    }

    // Static Method Tests
    // -------------------------------------------------
    @Test
    public void testDiffMon() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Burnymon");
        fm1.collect("Burnymon");
        fm1.collect("Dampymon");
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm2.collect("Burnymon");
        fm2.collect("Zappymon");
        LinkedForneymonegerie fm3 = LinkedForneymonegerie.diffMon(fm1, fm2);
        assertEquals(1, fm3.countType("Burnymon"));
        assertEquals(1, fm3.countType("Dampymon"));
        assertFalse(fm3.contains("Zappymon"));
        fm3.collect("Leafymon");
        assertFalse(fm1.contains("Leafymon"));
        assertFalse(fm2.contains("Leafymon"));
    }
    @Test
    public void testDiffMon_t1() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        LinkedForneymonegerie fm2 = LinkedForneymonegerie.diffMon(fm, fm1);
        assertEquals(0, fm2.size());
        assertFalse(fm2.contains("Dampymon"));
    }
    @Test
    public void testDiffMon_t2() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        LinkedForneymonegerie fm2 = LinkedForneymonegerie.diffMon(fm, fm1);
        assertEquals(1, fm2.size());
        assertFalse(fm2.contains("Dampymon"));
    }
    @Test
    public void testDiffMon_t3() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        for (int i = 0; i < START_SIZE+2; i++) {
            fm.collect("FT" + i);
            fm1.collect("FT" + i);
        }
        LinkedForneymonegerie diff = LinkedForneymonegerie.diffMon(fm, fm1);
        assertTrue(diff.empty());
    }
    @Test
    public void testDiffMon_t4() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        for (int i = 0; i < START_SIZE+2; i++) {
            fm.collect("FT" + i);
            fm.collect("FT" + i);
            fm1.collect("FT" + i);
        }
        LinkedForneymonegerie diff = LinkedForneymonegerie.diffMon(fm, fm1);
        assertTrue(LinkedForneymonegerie.sameCollection(diff, fm1));
    }
    @Test
    public void testDiffMon_t5() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        LinkedForneymonegerie diff = LinkedForneymonegerie.diffMon(fm, fm1);
        assertTrue(diff.empty());
    }
    @Test
    public void testDiffMon_t6() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm1.collect("Burnymon");
        fm1.collect("Zappymon");
        fm1.collect("Burnymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        fm1.collect("Leafymon");
        fm2.collect("Zappymon");
        fm2.collect("Burnymon");
        fm2.collect("Dampymon");
        LinkedForneymonegerie diff = LinkedForneymonegerie.diffMon(fm, fm1);
        String[] answers = {
            "Burnymon",
            "Burnymon",
            "Leafymon"
        };
        for (int i = 0; i < diff.size(); i++) {
            assertEquals(answers[i], diff.nth(i));
        }
    }
    @Test
    public void testDiffMon_t7() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm1.collect("Burnymon");
        fm1.collect("Burnymon");
        fm1.collect("Burnymon");
        fm1.collect("Zappymon");
        fm1.collect("Zappymon");
        fm1.collect("Zappymon");
        fm1.collect("Dampymon");
        fm1.collect("Leafymon");
        
        fm2.collect("Zappymon");
        fm2.collect("Sickymon");
        fm2.collect("Dampymon");
        fm2.collect("Dampymon");
        LinkedForneymonegerie diff = LinkedForneymonegerie.diffMon(fm, fm1);
        String[] answers = {
            "Burnymon",
            "Burnymon",
            "Burnymon",
            "Zappymon",
            "Zappymon",
            "Leafymon"
        };
        for (int i = 0; i < diff.size(); i++) {
            assertEquals(answers[i], diff.nth(i));
        }
    }

    @Test
    public void testSameCollection() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Burnymon");
        fm1.collect("Burnymon");
        fm1.collect("Dampymon");
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm2.collect("Dampymon");
        fm2.collect("Burnymon");
        fm2.collect("Burnymon");
        assertTrue(LinkedForneymonegerie.sameCollection(fm1, fm2));
        assertTrue(LinkedForneymonegerie.sameCollection(fm2, fm1));
        fm2.collect("Leafymon");
        assertFalse(LinkedForneymonegerie.sameCollection(fm1, fm2));
    }
    @Test
    public void testSameCollection_t1() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        assertTrue(LinkedForneymonegerie.sameCollection(fm, fm1));
        assertTrue(LinkedForneymonegerie.sameCollection(fm1, fm1));
        fm1.collect("Dampymon");
        assertTrue(LinkedForneymonegerie.sameCollection(fm1, fm1));
    }
    @Test
    public void testSameCollection_t2() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Burnymon");
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        assertFalse(LinkedForneymonegerie.sameCollection(fm, fm1));
        fm.releaseType("Burnymon");
        fm1.releaseType("Burnymon");
        fm1.release("Dampymon");
        assertTrue(LinkedForneymonegerie.sameCollection(fm, fm1));
    }
    @Test
    public void testSameCollection_t3() {
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Burnymon");
        fm1.collect("Zappymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        fm1.collect("Zappymon");
        fm1.collect("Burnymon");
        assertFalse(LinkedForneymonegerie.sameCollection(fm, fm1));
        fm1.collect("Zappymon");
        assertTrue(LinkedForneymonegerie.sameCollection(fm, fm1));
    }
    @Test
    public void testSameCollection_t4() {
        assertTrue(LinkedForneymonegerie.sameCollection(fm, new LinkedForneymonegerie()));
    }
    
    // Iterator Tests
    // -------------------------------------------------
    
    @Test
    public void testIteratorBasics() {
        fm.collect("Andrewmon");
        fm.collect("Andrewmon");
        fm.collect("Baddymon");
        fm.collect("Cooliomon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();

        // Test next()
        assertEquals("Andrewmon", it.getType());
        it.next();
        assertEquals("Andrewmon", it.getType());
        it.next();
        assertEquals("Baddymon", it.getType());
        it.next();
        assertEquals("Cooliomon", it.getType());
        assertFalse(it.hasNext());
        
        // Test prev()
        assertEquals("Cooliomon", it.getType());
        it.prev();
        assertEquals("Baddymon", it.getType());
        it.prev();
        assertEquals("Andrewmon", it.getType());
        it.prev();
        assertEquals("Andrewmon", it.getType());
        assertFalse(it.hasPrev());
        
        it.replaceAll("Mimicmon");
        assertEquals(2, fm.countType("Mimicmon"));
        assertTrue(it.isValid());
        
        fm.collect("Cooliomon");
        assertFalse(it.isValid());
        
        // TODO: Test whether updates maintain the relative ordering of
        // ForneymonTypes appropriately!
    }
    
    @Test
    public void testIteratorBasics_t1() {
        try {
            LinkedForneymonegerie.Iterator it = fm.getIterator();
            it.next(); // Shouldn't even get here
        } catch (Exception e) {
            if (!(e instanceof IllegalStateException)) {
                fail();
            }
        }
    }
    
    @Test
    public void testIteratorValidity() {
        fm.collect("Burnymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        fm.collect("Burnymon");
        assertFalse(it.isValid());
    }
    @Test
    public void testIteratorValidity_t1() {
        fm.collect("Burnymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        fm.release("Burnymon");
        assertFalse(it.isValid());
    }
    @Test
    public void testIteratorValidity_t2() {
        fm.collect("Burnymon");
        LinkedForneymonegerie.Iterator it1 = fm.getIterator();
        fm.collect("Dampymon");
        LinkedForneymonegerie.Iterator it2 = fm.getIterator();
        fm.release("Dampymon");
        assertFalse(it1.isValid());
        assertFalse(it2.isValid());
    }
    @Test
    public void testIteratorValidity_t3() {
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm2.collect("Zappymon");
        fm2.collect("Dampymon");
        LinkedForneymonegerie.Iterator it1 = fm.getIterator();
        LinkedForneymonegerie.Iterator it2 = fm2.getIterator();
        fm.trade(fm2);
        assertFalse(it1.isValid());
        assertFalse(it2.isValid());
    }
    
    @Test
    public void testIteratorNext() {
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        String[] soln = {"Dampymon", "Dampymon", "Burnymon", "Zappymon"};
        for (int i = 0; i < soln.length; i++) {
            assertEquals(soln[i], it.getType());
            if (it.hasNext()) it.next();
        }
        assertFalse(it.hasNext());
    }
    @Test
    public void testIteratorNext_t1() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        String[] soln = {"Burnymon", "Burnymon", "Burnymon", "Dampymon", "Dampymon", "Zappymon"};
        for (int i = 0; i < soln.length; i++) {
            assertEquals(soln[i], it.getType());
            if (it.hasNext()) it.next();
        }
        assertFalse(it.hasNext());
    }
    @Test
    public void testIteratorNext_t2() {
        try {
            fm.collect("Burnymon");
            LinkedForneymonegerie.Iterator it = fm.getIterator();
            it.next();
            it.next();
        } catch (Exception e) {
            if (!(e instanceof NoSuchElementException)) {
                fail();
            }
        }
    }
    @Test
    public void testIteratorNext_t3() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.release("Burnymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        String[] soln = {"Dampymon", "Dampymon", "Burnymon", "Burnymon", "Zappymon"};
        for (int i = 0; i < soln.length; i++) {
            assertEquals(soln[i], it.getType());
            if (it.hasNext()) it.next();
        }
        assertFalse(it.hasNext());
    }
    @Test
    public void testIteratorNext_t4() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.releaseType("Burnymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        String[] soln = {"Dampymon", "Dampymon", "Zappymon"};
        for (int i = 0; i < soln.length; i++) {
            assertEquals(soln[i], it.getType());
            if (it.hasNext()) it.next();
        }
        assertFalse(it.hasNext());
    }
    
    @Test
    public void testIteratorPrev() {
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        String[] soln = {"Zappymon", "Burnymon", "Dampymon", "Dampymon"};
        while (it.hasNext()) {it.next();}
        for (int i = 0; i < soln.length; i++) {
            assertEquals(soln[i], it.getType());
            if (it.hasPrev()) it.prev();
        }
        assertFalse(it.hasPrev());
    }
    @Test
    public void testIteratorPrev_t1() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        String[] soln = {"Zappymon", "Dampymon", "Dampymon", "Burnymon", "Burnymon", "Burnymon"};
        while (it.hasNext()) {it.next();}
        for (int i = 0; i < soln.length; i++) {
            assertEquals(soln[i], it.getType());
            if (it.hasPrev()) it.prev();
        }
        assertFalse(it.hasPrev());
    }
    @Test
    public void testIteratorPrev_t2() {
        try {
            fm.collect("Burnymon");
            fm.collect("Burnymon");
            fm.collect("Burnymon");
            LinkedForneymonegerie.Iterator it = fm.getIterator();
            it.next();
            it.next();
            it.prev();
            it.prev();
            it.prev();
        } catch (Exception e) {
            if (!(e instanceof NoSuchElementException)) {
                fail();
            }
        }
    }
    @Test
    public void testIteratorPrev_t3() {
        try {
            fm.collect("Burnymon");
            LinkedForneymonegerie.Iterator it = fm.getIterator();
            fm.collect("Burnymon");
            it.prev();
        } catch (Exception e) {
            if (!(e instanceof IllegalStateException)) {
                fail();
            }
        }
    }
    @Test
    public void testIteratorPrev_t4() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.release("Burnymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        String[] soln = {"Zappymon", "Burnymon", "Burnymon", "Dampymon", "Dampymon"};
        while (it.hasNext()) {it.next();}
        for (int i = 0; i < soln.length; i++) {
            assertEquals(soln[i], it.getType());
            if (it.hasPrev()) it.prev();
        }
        assertFalse(it.hasPrev());
    }
    @Test
    public void testIteratorPrev_t5() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.releaseType("Burnymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        String[] soln = {"Zappymon", "Dampymon", "Dampymon"};
        while (it.hasNext()) {it.next();}
        for (int i = 0; i < soln.length; i++) {
            assertEquals(soln[i], it.getType());
            if (it.hasPrev()) it.prev();
        }
        assertFalse(it.hasPrev());
    }
    @Test
    public void testIteratorPrev_t6() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.releaseType("Dampymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        String[] soln = {"Zappymon", "Burnymon", "Burnymon"};
        while (it.hasNext()) {it.next();}
        for (int i = 0; i < soln.length; i++) {
            assertEquals(soln[i], it.getType());
            if (it.hasPrev()) it.prev();
        }
        assertFalse(it.hasPrev());
    }
    
    // Small helper function to test replaceAll
    public static void advanceIteratorTo (String toFind, LinkedForneymonegerie.Iterator it) {
        while (it.hasNext()) {
            if (it.getType().equals(toFind)) {
                return;
            }
            it.next();
        }
    }
    
    @Test
    public void testIteratorReplaceAll() {
        fm.collect("Burnymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        it.replaceAll("Dampymon");
        assertTrue(it.isValid());
        assertEquals("Dampymon", it.getType());
        assertEquals(1, fm.countType("Dampymon"));
        assertEquals(0, fm.countType("Burnymon"));
    }
    @Test
    public void testIteratorReplaceAll_t1() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        it.replaceAll("Dampymon");
        assertTrue(it.isValid());
        assertEquals("Dampymon", it.getType());
        assertEquals(2, fm.countType("Dampymon"));
        assertEquals(0, fm.countType("Burnymon"));
    }
    @Test
    public void testIteratorReplaceAll_t2() {
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Zappymon");
        fm.collect("Burnymon");
        fm.collect("Leafymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        advanceIteratorTo("Zappymon", it);
        it.replaceAll("Dampymon");
        assertTrue(it.isValid());
        assertEquals("Dampymon", it.getType());
        assertEquals(2, fm.countType("Dampymon"));
        assertEquals(0, fm.countType("Zappymon"));
    }
    @Test
    public void testIteratorReplaceAll_t3() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        advanceIteratorTo("Burnymon", it);
        it.replaceAll("Burnymon");
        assertTrue(it.isValid());
        assertEquals("Burnymon", it.getType());
        assertEquals(2, fm.countType("Burnymon"));
        assertEquals(2, fm.countType("Dampymon"));
    }
    @Test
    public void testIteratorReplaceAll_t4() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        advanceIteratorTo("Dampymon", it);
        it.replaceAll("Burnymon");
        assertTrue(it.isValid());
        assertTrue(it.hasNext());
        assertEquals("Burnymon", it.getType());
        assertEquals(4, fm.countType("Burnymon"));
        assertEquals(0, fm.countType("Dampymon"));
    }
    
    @Test
    public void testIteratorReplaceAllOrder_t0() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        advanceIteratorTo("Burnymon", it);
        it.replaceAll("Zappymon");
        assertTrue(it.isValid());
        assertEquals(0, fm.countType("Burnymon"));
        assertEquals(3, fm.countType("Zappymon"));
        it = fm.getIterator();
        String[] soln = {"Zappymon", "Zappymon", "Zappymon", "Dampymon", "Dampymon"};
        for (int i = 0; i < soln.length; i++) {
            assertEquals(soln[i], it.getType());
            if (it.hasNext()) it.next();
        }
    }
    
    @Test
    public void testIteratorReplaceAllOrder_t1() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        advanceIteratorTo("Burnymon", it);
        it.replaceAll("Zappymon");
        assertTrue(it.isValid());
        assertEquals(0, fm.countType("Burnymon"));
        assertEquals(3, fm.countType("Zappymon"));
        it = fm.getIterator();
        String[] soln = {"Dampymon", "Dampymon", "Zappymon", "Zappymon", "Zappymon"};
        while (it.hasNext()) {it.next();}
        for (int i = 0; i < soln.length; i++) {
            assertEquals(soln[i], it.getType());
            if (it.hasPrev()) it.prev();
        }
    }
    
}