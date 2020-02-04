package forneymonagerie;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.rules.TestWatcher;

public class ForneymonagerieSolutionTests {
    
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
    Forneymonagerie fm;
    @Before
    public void init () {
        possible++;
        fm = new Forneymonagerie();
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
        assertEquals("Zappymon", fm.mostRecentAtRarity(1), "Zappymon");
        assertEquals("Zappymon", fm.mostRecentAtRarity(2), "Burnymon");
    }
    @Test
    public void testMostRecentAtRarity_t1() {
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        fm.release("Burnymon");
        assertEquals("Burnymon", fm.mostRecentAtRarity(1), "Burnymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        assertEquals("Zappymon", fm.mostRecentAtRarity(2), "Dampymon");
        fm.collect("Burnymon");
        assertEquals("Zappymon", fm.mostRecentAtRarity(2), "Burnymon");
        fm.collect("Burnymon");
        System.out.println(fm.toString());
        System.out.println("fuck keziah lal");
        assertEquals("Zappymon", fm.mostRecentAtRarity(2), "Dampymon");
        assertEquals("Zappymon", fm.mostRecentAtRarity(3), "Burnymon");
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
        Forneymonagerie dolly = fm.clone();
        assertEquals(2, dolly.countType("Burnymon"));
        assertEquals(1, dolly.countType("Dampymon"));
        dolly.collect("Zappymon");
        assertFalse(fm.contains("Zappymon"));
    }
    @Test
    public void testClone_t1() {
        Forneymonagerie dolly = fm.clone();
        fm.collect("Dampymon");
        assertFalse(dolly.contains("Dampymon"));
    }
    @Test
    public void testClone_t2() {
        fm.collect("Dampymon");
        Forneymonagerie dolly = fm.clone();
        dolly.collect("Burnymon");
        Forneymonagerie superDolly = dolly.clone();
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
        Forneymonagerie dolly = fm.clone();
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
        Forneymonagerie dolly = fm.clone();
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
        Forneymonagerie fm1 = new Forneymonagerie();
        fm1.collect("Burnymon");
        fm1.collect("Burnymon");
        fm1.collect("Dampymon");
        Forneymonagerie fm2 = new Forneymonagerie();
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
        Forneymonagerie fm2 = new Forneymonagerie();
        fm.collect("Dampymon");
        fm2.trade(fm);
        assertTrue(fm.empty());
        assertFalse(fm2.empty());
    }
    @Test
    public void testTrade_t2() {
        Forneymonagerie fm2 = new Forneymonagerie();
        Forneymonagerie fm3 = new Forneymonagerie();
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
        Forneymonagerie fm2 = new Forneymonagerie();
        fm.trade(fm2);
        assertTrue(fm.empty());
        assertTrue(fm2.empty());
    }

    // Static Method Tests
    // -------------------------------------------------
    @Test
    public void testDiffMon() {
        Forneymonagerie fm1 = new Forneymonagerie();
        fm1.collect("Burnymon");
        fm1.collect("Burnymon");
        fm1.collect("Dampymon");
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect("Burnymon");
        fm2.collect("Zappymon");
        Forneymonagerie fm3 = Forneymonagerie.diffMon(fm1, fm2);
        assertEquals(1, fm3.countType("Burnymon"));
        assertEquals(1, fm3.countType("Dampymon"));
        assertFalse(fm3.contains("Zappymon"));
        fm3.collect("Leafymon");
        assertFalse(fm1.contains("Leafymon"));
        assertFalse(fm2.contains("Leafymon"));
    }
    @Test
    public void testDiffMon_t1() {
        Forneymonagerie fm1 = new Forneymonagerie();
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonagerie fm2 = Forneymonagerie.diffMon(fm, fm1);
        assertEquals(0, fm2.size());
        assertFalse(fm2.contains("Dampymon"));
    }
    @Test
    public void testDiffMon_t2() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        Forneymonagerie fm1 = new Forneymonagerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        Forneymonagerie fm2 = Forneymonagerie.diffMon(fm, fm1);
        assertEquals(1, fm2.size());
        assertFalse(fm2.contains("Dampymon"));
    }
    @Test
    public void testDiffMon_t3() {
        Forneymonagerie fm1 = new Forneymonagerie();
        for (int i = 0; i < START_SIZE+2; i++) {
            fm.collect("FT" + i);
            fm1.collect("FT" + i);
        }
        Forneymonagerie diff = Forneymonagerie.diffMon(fm, fm1);
        assertTrue(diff.empty());
    }
    @Test
    public void testDiffMon_t4() {
        Forneymonagerie fm1 = new Forneymonagerie();
        for (int i = 0; i < START_SIZE+2; i++) {
            fm.collect("FT" + i);
            fm.collect("FT" + i);
            fm1.collect("FT" + i);
        }
        Forneymonagerie diff = Forneymonagerie.diffMon(fm, fm1);
        assertTrue(Forneymonagerie.sameCollection(diff, fm1));
    }
    @Test
    public void testDiffMon_t5() {
        Forneymonagerie fm1 = new Forneymonagerie();
        Forneymonagerie diff = Forneymonagerie.diffMon(fm, fm1);
        assertTrue(diff.empty());
    }
    @Test
    public void testDiffMon_t6() {
        Forneymonagerie fm1 = new Forneymonagerie();
        Forneymonagerie fm2 = new Forneymonagerie();
        fm1.collect("Burnymon");
        fm1.collect("Zappymon");
        fm1.collect("Burnymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        fm1.collect("Leafymon");
        fm2.collect("Zappymon");
        fm2.collect("Burnymon");
        fm2.collect("Dampymon");
        Forneymonagerie diff = Forneymonagerie.diffMon(fm, fm1);
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
        Forneymonagerie fm1 = new Forneymonagerie();
        Forneymonagerie fm2 = new Forneymonagerie();
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
        Forneymonagerie diff = Forneymonagerie.diffMon(fm, fm1);
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
        Forneymonagerie fm1 = new Forneymonagerie();
        fm1.collect("Burnymon");
        fm1.collect("Burnymon");
        fm1.collect("Dampymon");
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect("Dampymon");
        fm2.collect("Burnymon");
        fm2.collect("Burnymon");
        assertTrue(Forneymonagerie.sameCollection(fm1, fm2));
        assertTrue(Forneymonagerie.sameCollection(fm2, fm1));
        fm2.collect("Leafymon");
        assertFalse(Forneymonagerie.sameCollection(fm1, fm2));
    }
    @Test
    public void testSameCollection_t1() {
        Forneymonagerie fm1 = new Forneymonagerie();
        assertTrue(Forneymonagerie.sameCollection(fm, fm1));
        assertTrue(Forneymonagerie.sameCollection(fm1, fm1));
        fm1.collect("Dampymon");
        assertTrue(Forneymonagerie.sameCollection(fm1, fm1));
    }
    @Test
    public void testSameCollection_t2() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        Forneymonagerie fm1 = new Forneymonagerie();
        fm1.collect("Burnymon");
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        assertFalse(Forneymonagerie.sameCollection(fm, fm1));
        fm.releaseType("Burnymon");
        fm1.releaseType("Burnymon");
        fm1.release("Dampymon");
        assertTrue(Forneymonagerie.sameCollection(fm, fm1));
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
        Forneymonagerie fm1 = new Forneymonagerie();
        fm1.collect("Burnymon");
        fm1.collect("Zappymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        fm1.collect("Zappymon");
        fm1.collect("Burnymon");
        assertFalse(Forneymonagerie.sameCollection(fm, fm1));
        fm1.collect("Zappymon");
        assertTrue(Forneymonagerie.sameCollection(fm, fm1));
    }
    @Test
    public void testSameCollection_t4() {
        assertTrue(Forneymonagerie.sameCollection(fm, new Forneymonagerie()));
    }
    
}