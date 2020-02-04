package autocompleter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;

public class AutoCompleterTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    // Global timeout to prevent infinite loops from
    // crashing the test suite
    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);
    
    // Used for grading, reports the total number of tests
    // passed over the total possible
    static int possible = 0, passed = 0;

    // Used as the basic empty Autocompleter to test; 
    // the @Before method is run before every @Test
    AutoCompleter ac;
    @Before
    public void init () {
        possible++;
        ac = new AutoCompleter();
    }
    
    // Each time you pass a test, you get a point! Yay!
    // [!] Requires JUnit 4+ to run
    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            passed++;
        }
    };
    
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
    
    // Initialization Tests
    // -------------------------------------------------
    @Test
    public void testAutocompleter() {
        assertTrue(ac.isEmpty());
    }

    // Basic Tests
    // -------------------------------------------------
    @Test
    public void testAddTerm() {
        ac.addTerm("is");
        ac.addTerm("it");
        ac.addTerm("as");
        ac.addTerm("ass");
        ac.addTerm("at");
        ac.addTerm("bat");
    }

    @Test
    public void testAddHasTerm() {
        ac.addTerm("is");
        ac.addTerm("it");
        ac.addTerm("as");
        ac.addTerm("ass");
        ac.addTerm("at");
        ac.addTerm("bat");
        assertTrue(ac.hasTerm("is"));
        assertTrue(ac.hasTerm("it"));
        assertTrue(ac.hasTerm("as"));
        assertTrue(ac.hasTerm("ass"));
        assertTrue(ac.hasTerm("at"));
        assertTrue(ac.hasTerm("bat"));
        assertFalse(ac.hasTerm("ii"));
        assertFalse(ac.hasTerm("i"));
        assertFalse(ac.hasTerm("ba"));
        assertFalse(ac.hasTerm("zoo"));
    }
    @Test
    public void testAddHasTerm_t1() {
        assertFalse(ac.hasTerm("a"));
        assertFalse(ac.hasTerm("aa"));
        try {
            ac.addTerm("");
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                fail();
            }
        }
    }
    @Test
    public void testAddHasTerm_t2() {
        ac.addTerm("as");
        ac.addTerm("at");
        ac.addTerm("bass");
        ac.addTerm("bat");
        assertTrue(ac.hasTerm("as"));
        assertTrue(ac.hasTerm("at"));
        assertTrue(ac.hasTerm("bass"));
        assertTrue(ac.hasTerm("bat"));
        assertFalse(ac.hasTerm("ba"));
    }
    @Test
    public void testAddHasTerm_t3() {
        ac.addTerm("a");
        ac.addTerm("at");
        ac.addTerm("attack");
        ac.addTerm("attacking");
        assertTrue(ac.hasTerm("a"));
        assertTrue(ac.hasTerm("at"));
        assertTrue(ac.hasTerm("attack"));
        assertTrue(ac.hasTerm("attacking"));
        assertFalse(ac.hasTerm("attac"));
    }
    @Test
    public void testAddHasTerm_t4() {
        ac.addTerm("bit");
        ac.addTerm("bite");
        ac.addTerm("bitter");
        ac.addTerm("bit");
        ac.addTerm("bite");
        assertTrue(ac.hasTerm("bit"));
        assertTrue(ac.hasTerm("bite"));
        assertTrue(ac.hasTerm("bitter"));
        assertFalse(ac.hasTerm("bi"));
        assertFalse(ac.hasTerm("bitt"));
    }
    @Test
    public void testAddHasTerm_t5() {
        ac.addTerm("at");
        ac.addTerm("ab");
        ac.addTerm("about");
        ac.addTerm("abominable");
        ac.addTerm("abomination");
        ac.addTerm("about");
        assertTrue(ac.hasTerm("at"));
        assertTrue(ac.hasTerm("ab"));
        assertTrue(ac.hasTerm("about"));
        assertTrue(ac.hasTerm("abominable"));
        assertTrue(ac.hasTerm("abomination"));
        assertFalse(ac.hasTerm("a"));
        assertFalse(ac.hasTerm("abomin"));
    }
    @Test
    public void testAddHasTerm_t6() {
        ac.addTerm("attic");
        ac.addTerm("baffled");
        ac.addTerm("catastrophe");
        ac.addTerm("demolished");
        ac.addTerm("eliminated");
        assertTrue(ac.hasTerm("attic"));
        assertTrue(ac.hasTerm("baffled"));
        assertTrue(ac.hasTerm("catastrophe"));
        assertTrue(ac.hasTerm("demolished"));
        assertTrue(ac.hasTerm("eliminated"));
        assertFalse(ac.hasTerm("a"));
        assertFalse(ac.hasTerm("b"));
        assertFalse(ac.hasTerm("c"));
        assertFalse(ac.hasTerm("d"));
        assertFalse(ac.hasTerm("e"));
    }
    @Test
    public void testAddHasTerm_t7() {
        ac.addTerm("catastrophe");
        ac.addTerm("attic");
        ac.addTerm("demolished");
        ac.addTerm("baffled");
        ac.addTerm("eliminated");
        assertTrue(ac.hasTerm("attic"));
        assertTrue(ac.hasTerm("baffled"));
        assertTrue(ac.hasTerm("catastrophe"));
        assertTrue(ac.hasTerm("demolished"));
        assertTrue(ac.hasTerm("eliminated"));
        assertFalse(ac.hasTerm("a"));
        assertFalse(ac.hasTerm("b"));
        assertFalse(ac.hasTerm("c"));
        assertFalse(ac.hasTerm("d"));
        assertFalse(ac.hasTerm("e"));
    }
    @Test
    public void testAddHasTerm_t8() {
        ac.addTerm("I");
        ac.addTerm("integer");
        ac.addTerm("integral");
        ac.addTerm("bar");
        ac.addTerm("barometer");
        ac.addTerm("zoo");
        ac.addTerm("zoologist");
        ac.addTerm("zen");
        assertTrue(ac.hasTerm("I"));
        assertTrue(ac.hasTerm("integer"));
        assertTrue(ac.hasTerm("integral"));
        assertTrue(ac.hasTerm("bar"));
        assertTrue(ac.hasTerm("barometer"));
        assertTrue(ac.hasTerm("zoo"));
        assertTrue(ac.hasTerm("zoologist"));
        assertTrue(ac.hasTerm("zen"));
        assertFalse(ac.hasTerm("in"));
        assertFalse(ac.hasTerm("zool"));
        assertFalse(ac.hasTerm("baro"));
    }
    @Test
    public void testAddHasTerm_t9() {
        ac.addTerm("integral");
        ac.addTerm("integer");
        ac.addTerm("I");
        ac.addTerm("barometer");
        ac.addTerm("bar");
        ac.addTerm("zoologist");
        ac.addTerm("zoo");
        ac.addTerm("zen");
        assertTrue(ac.hasTerm("I"));
        assertTrue(ac.hasTerm("integer"));
        assertTrue(ac.hasTerm("integral"));
        assertTrue(ac.hasTerm("bar"));
        assertTrue(ac.hasTerm("barometer"));
        assertTrue(ac.hasTerm("zoo"));
        assertTrue(ac.hasTerm("zoologist"));
        assertTrue(ac.hasTerm("zen"));
        assertFalse(ac.hasTerm("in"));
        assertFalse(ac.hasTerm("zool"));
        assertFalse(ac.hasTerm("baro"));
    }
    @Test
    public void testAddHasTerm_t10() {
        ac.addTerm("integral");
        ac.addTerm("integer");
        ac.addTerm("I");
        ac.addTerm("barometer");
        ac.addTerm("bar");
        ac.addTerm("zoologist");
        ac.addTerm("zoo");
        ac.addTerm("zen");
        ac.addTerm("I");
        ac.addTerm("bar");
        ac.addTerm("zoo");
        assertTrue(ac.hasTerm("I"));
        assertTrue(ac.hasTerm("integer"));
        assertTrue(ac.hasTerm("integral"));
        assertTrue(ac.hasTerm("bar"));
        assertTrue(ac.hasTerm("barometer"));
        assertTrue(ac.hasTerm("zoo"));
        assertTrue(ac.hasTerm("zoologist"));
        assertTrue(ac.hasTerm("zen"));
        assertFalse(ac.hasTerm("in"));
        assertFalse(ac.hasTerm("zool"));
        assertFalse(ac.hasTerm("baro"));
    }
    @Test
    public void testAddHasTerm_t11() {
        ac.addTerm("carve");
        ac.addTerm("canned");
        ac.addTerm("add");
        ac.addTerm("argue");
        ac.addTerm("bar");
        ac.addTerm("bad");
        ac.addTerm("sour");
        ac.addTerm("sorted");
        ac.addTerm("tent");
        ac.addTerm("tended");
        assertTrue(ac.hasTerm("carve"));
        assertTrue(ac.hasTerm("canned"));
        assertTrue(ac.hasTerm("add"));
        assertTrue(ac.hasTerm("argue"));
        assertTrue(ac.hasTerm("bar"));
        assertTrue(ac.hasTerm("bad"));
        assertTrue(ac.hasTerm("sour"));
        assertTrue(ac.hasTerm("sorted"));
        assertTrue(ac.hasTerm("tent"));
        assertTrue(ac.hasTerm("tended"));
        assertFalse(ac.hasTerm("ar"));
        assertFalse(ac.hasTerm("sou"));
        assertFalse(ac.hasTerm("sor"));
    }
    @Test
    public void testAddHasTerm_t12() {
        ac.addTerm("a");
        ac.addTerm("aa");
        ac.addTerm("aaa");
        ac.addTerm("aaaaa");
        assertTrue(ac.hasTerm("a"));
        assertTrue(ac.hasTerm("aa"));
        assertTrue(ac.hasTerm("aaa"));
        assertFalse(ac.hasTerm("aaaa"));
        assertTrue(ac.hasTerm("aaaaa"));
    }
    @Test
    public void testAddHasTerm_t13() {
        ac.addTerm("ab");
        ac.addTerm("ace");
        ac.addTerm("academy");
        ac.addTerm("acadeem");
        assertTrue(ac.hasTerm("ab"));
        assertTrue(ac.hasTerm("ace"));
        assertTrue(ac.hasTerm("academy"));
        assertTrue(ac.hasTerm("acadeem"));
        assertFalse(ac.hasTerm("aca"));
        assertFalse(ac.hasTerm("academ"));
    }
    @Test
    public void testAddHasTerm_t14() {
        ac.addTerm("a");
        ac.addTerm("z");
        ac.addTerm("b");
        ac.addTerm("y");
        ac.addTerm("c");
        ac.addTerm("x");
        assertTrue(ac.hasTerm("a"));
        assertTrue(ac.hasTerm("b"));
        assertTrue(ac.hasTerm("c"));
        assertTrue(ac.hasTerm("x"));
        assertTrue(ac.hasTerm("y"));
        assertTrue(ac.hasTerm("z"));
        assertFalse(ac.hasTerm("ab"));
        assertFalse(ac.hasTerm("az"));
        assertFalse(ac.hasTerm("zb"));
    }

    @Test
    public void getSuggestedTerm() {
        ac.addTerm("is");
        ac.addTerm("it");
        ac.addTerm("as");
        ac.addTerm("at");
        ac.addTerm("item");
        ac.addTerm("ass");
        ac.addTerm("bat");
        ac.addTerm("bother");
        ac.addTerm("goat");
        ac.addTerm("goad");
        assertEquals("is", ac.getSuggestedTerm("is"));
        assertEquals("it", ac.getSuggestedTerm("it"));
        assertEquals("item", ac.getSuggestedTerm("ite"));
        assertEquals("as", ac.getSuggestedTerm("as"));
        assertEquals("bat", ac.getSuggestedTerm("ba"));
        assertEquals("bother", ac.getSuggestedTerm("bo"));
        assertEquals(null, ac.getSuggestedTerm("bad"));
        assertEquals(null, ac.getSuggestedTerm("zoo"));
        String result = ac.getSuggestedTerm("go");
        assertTrue(result.equals("goat") || result.equals("goad"));
    }
    @Test
    public void getSuggestedTerm_t1() {
        ac.addTerm("ab");
        ac.addTerm("about");
        ac.addTerm("abort");
        String result = ac.getSuggestedTerm("a");
        assertTrue(result.equals("ab") || result.equals("about") || result.equals("abort"));
        result = ac.getSuggestedTerm("ab");
        assertTrue(result.equals("ab") || result.equals("about") || result.equals("abort"));
        result = ac.getSuggestedTerm("abo");
        assertTrue(result.equals("about") || result.equals("abort"));
        assertEquals("about", ac.getSuggestedTerm("abou"));
        assertEquals("abort", ac.getSuggestedTerm("abor"));
        assertEquals("abort", ac.getSuggestedTerm("abort"));
        assertEquals(null, ac.getSuggestedTerm("abortting"));
        assertEquals(null, ac.getSuggestedTerm("zzz"));
    }
    @Test
    public void getSuggestedTerm_t2() {
        ac.addTerm("bit");
        ac.addTerm("bite");
        ac.addTerm("bitter");
        ac.addTerm("bit");
        ac.addTerm("bite");
        String result = ac.getSuggestedTerm("bit");
        assertTrue(result.equals("bit") || result.equals("bite") || result.equals("bitter"));
        assertEquals("bite", ac.getSuggestedTerm("bite"));
        assertEquals("bitter", ac.getSuggestedTerm("bitt"));
    }
    @Test
    public void getSuggestedTerm_t3() {
        ac.addTerm("attic");
        ac.addTerm("baffled");
        ac.addTerm("catastrophe");
        ac.addTerm("demolished");
        ac.addTerm("eliminated");
        assertEquals("attic", ac.getSuggestedTerm("a"));
        assertEquals("attic", ac.getSuggestedTerm("attic"));
        assertEquals(null, ac.getSuggestedTerm("attics"));
        assertEquals("baffled", ac.getSuggestedTerm("b"));
        assertEquals("catastrophe", ac.getSuggestedTerm("c"));
        assertEquals("demolished", ac.getSuggestedTerm("d"));
        assertEquals("eliminated", ac.getSuggestedTerm("e"));
    }
    @Test
    public void getSuggestedTerm_t4() {
        ac.addTerm("catastrophe");
        ac.addTerm("attic");
        ac.addTerm("demolished");
        ac.addTerm("baffled");
        ac.addTerm("eliminated");
        assertEquals("attic", ac.getSuggestedTerm("a"));
        assertEquals("attic", ac.getSuggestedTerm("attic"));
        assertEquals(null, ac.getSuggestedTerm("attics"));
        assertEquals("baffled", ac.getSuggestedTerm("b"));
        assertEquals("catastrophe", ac.getSuggestedTerm("c"));
        assertEquals("demolished", ac.getSuggestedTerm("d"));
        assertEquals("eliminated", ac.getSuggestedTerm("e"));
    }
    @Test
    public void getSuggestedTerm_t5() {
        ac.addTerm("I");
        ac.addTerm("integer");
        ac.addTerm("integral");
        ac.addTerm("bar");
        ac.addTerm("barometer");
        ac.addTerm("zoo");
        ac.addTerm("zoologist");
        ac.addTerm("zen");
        String result = ac.getSuggestedTerm("I");
        assertTrue(result.equals("I") || result.equals("i") || result.equals("integer") || result.equals("integral"));
        result = ac.getSuggestedTerm("int");
        assertTrue(result.equals("integer") || result.equals("integral"));
        assertEquals("integer", ac.getSuggestedTerm("intege"));
        assertEquals("integral", ac.getSuggestedTerm("integr"));
        assertEquals(null, ac.getSuggestedTerm("integet"));
        result = ac.getSuggestedTerm("b");
        assertTrue(result.equals("bar") || result.equals("barometer"));
        result = ac.getSuggestedTerm("bar");
        assertTrue(result.equals("bar") || result.equals("barometer"));
        assertEquals("barometer", ac.getSuggestedTerm("baro"));
        assertEquals(null, ac.getSuggestedTerm("barro"));
        result = ac.getSuggestedTerm("zoo");
        assertTrue(result.equals("zoo") || result.equals("zoologist"));
        assertEquals("zoologist", ac.getSuggestedTerm("zoolo"));
        assertEquals("zoologist", ac.getSuggestedTerm("zoologist"));
        assertEquals("zen", ac.getSuggestedTerm("ze"));
        assertEquals(null, ac.getSuggestedTerm("a"));
    }
    @Test
    public void getSuggestedTerm_t6() {
        ac.addTerm("integer");
        ac.addTerm("integral");
        ac.addTerm("barometer");
        ac.addTerm("zoologist");
        ac.addTerm("zen");
        ac.addTerm("I");
        ac.addTerm("bar");
        ac.addTerm("zoo");
        String result = ac.getSuggestedTerm("I");
        assertTrue(result.equals("I") || result.equals("i") || result.equals("integer") || result.equals("integral"));
        result = ac.getSuggestedTerm("int");
        assertTrue(result.equals("integer") || result.equals("integral"));
        assertEquals("integer", ac.getSuggestedTerm("intege"));
        assertEquals("integral", ac.getSuggestedTerm("integr"));
        assertEquals(null, ac.getSuggestedTerm("integet"));
        result = ac.getSuggestedTerm("b");
        assertTrue(result.equals("bar") || result.equals("barometer"));
        result = ac.getSuggestedTerm("bar");
        assertTrue(result.equals("bar") || result.equals("barometer"));
        assertEquals("barometer", ac.getSuggestedTerm("baro"));
        assertEquals(null, ac.getSuggestedTerm("barro"));
        result = ac.getSuggestedTerm("zoo");
        assertTrue(result.equals("zoo") || result.equals("zoologist"));
        assertEquals("zoologist", ac.getSuggestedTerm("zoolo"));
        assertEquals("zoologist", ac.getSuggestedTerm("zoologist"));
        assertEquals("zen", ac.getSuggestedTerm("ze"));
        assertEquals(null, ac.getSuggestedTerm("a"));
    }
    @Test
    public void getSuggestedTerm_t7() {
        try {
            ac.getSuggestedTerm("");
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                fail();
            }
        }
    }
    @Test
    public void getSuggestedTerm_t8() {
        assertEquals(null, ac.getSuggestedTerm("a"));
        assertEquals(null, ac.getSuggestedTerm("aa"));
    }
    @Test
    public void getSuggestedTerm_t9() {
        ac.addTerm("carve");
        ac.addTerm("canned");
        ac.addTerm("add");
        ac.addTerm("argue");
        ac.addTerm("bar");
        ac.addTerm("bad");
        ac.addTerm("sour");
        ac.addTerm("sorted");
        ac.addTerm("tent");
        ac.addTerm("tended");
        assertEquals("carve", ac.getSuggestedTerm("car"));
        assertEquals("canned", ac.getSuggestedTerm("can"));
        assertEquals("add", ac.getSuggestedTerm("ad"));
        assertEquals("argue", ac.getSuggestedTerm("ar"));
        assertEquals("bar", ac.getSuggestedTerm("bar"));
        assertEquals("sorted", ac.getSuggestedTerm("sor"));
        assertEquals("tended", ac.getSuggestedTerm("tend"));
    }
    @Test
    public void getSuggestedTerm_t10() {
        ac.addTerm("ab");
        ac.addTerm("ace");
        ac.addTerm("academy");
        ac.addTerm("acadeem");
        assertEquals("ab", ac.getSuggestedTerm("ab"));
        assertEquals("ace", ac.getSuggestedTerm("ace"));
        assertEquals("academy", ac.getSuggestedTerm("academ"));
        assertEquals("acadeem", ac.getSuggestedTerm("acadee"));
        String result = ac.getSuggestedTerm("aca");
        assertTrue(result.equals("academy") || result.equals("acadeem"));
        assertEquals(null, ac.getSuggestedTerm("acadeemy"));
    }
    @Test
    public void getSuggestedTerm_t11() {
        ac.addTerm("a");
        ac.addTerm("z");
        ac.addTerm("b");
        ac.addTerm("y");
        ac.addTerm("c");
        ac.addTerm("x");
        assertEquals("a", ac.getSuggestedTerm("a"));
        assertEquals("z", ac.getSuggestedTerm("z"));
        assertEquals("b", ac.getSuggestedTerm("b"));
        assertEquals("y", ac.getSuggestedTerm("y"));
        assertEquals(null, ac.getSuggestedTerm("az"));
        assertEquals(null, ac.getSuggestedTerm("d"));
        assertEquals(null, ac.getSuggestedTerm("ab"));
        assertEquals(null, ac.getSuggestedTerm("by"));
    }
    
    @Test
    public void getSortedTerms() {
        ac.addTerm("is");
        ac.addTerm("it");
        ac.addTerm("as");
        ac.addTerm("itenerary");
        ac.addTerm("ass");
        ac.addTerm("at");
        ac.addTerm("zoo");
        ac.addTerm("bat");
        ac.addTerm("bother");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "as", "ass", "at", "bat", "bother", "is", "it", "itenerary", "zoo"
        ));
        assertEquals(solution, ac.getSortedTerms());
    }
    @Test
    public void getSortedTerms_t1() {
        ArrayList<String> solution = new ArrayList<String>();
        assertEquals(solution, ac.getSortedTerms());
    }
    @Test
    public void getSortedTerms_t2() {
        ac.addTerm("a");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList("a"));
        assertEquals(solution, ac.getSortedTerms());
    }
    @Test
    public void getSortedTerms_t3() {
        ac.addTerm("a");
        ac.addTerm("b");
        ac.addTerm("e");
        ac.addTerm("d");
        ac.addTerm("c");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "a", "b", "c", "d", "e"
        ));
        assertEquals(solution, ac.getSortedTerms());
    }
    @Test
    public void getSortedTerms_t4() {
        ac.addTerm("catastrophe");
        ac.addTerm("attic");
        ac.addTerm("demolished");
        ac.addTerm("baffled");
        ac.addTerm("eliminated");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "attic", "baffled", "catastrophe", "demolished", "eliminated"
        ));
        assertEquals(solution, ac.getSortedTerms());
    }
    @Test
    public void getSortedTerms_t5() {
        ac.addTerm("bit");
        ac.addTerm("bite");
        ac.addTerm("biter");
        ac.addTerm("bitter");
        ac.addTerm("bit");
        ac.addTerm("bite");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "bit", "bite", "biter", "bitter"
        ));
        assertEquals(solution, ac.getSortedTerms());
    }
    @Test
    public void getSortedTerms_t6() {
        ac.addTerm("i");
        ac.addTerm("integer");
        ac.addTerm("integral");
        ac.addTerm("bar");
        ac.addTerm("barometer");
        ac.addTerm("zoo");
        ac.addTerm("zoologist");
        ac.addTerm("zen");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "bar", "barometer", "i", "integer", "integral", "zen", "zoo", "zoologist"
        ));
        assertEquals(solution, ac.getSortedTerms());
    }
    @Test
    public void getSortedTerms_t7() {
        ac.addTerm("integer");
        ac.addTerm("integral");
        ac.addTerm("barometer");
        ac.addTerm("zoologist");
        ac.addTerm("zen");
        ac.addTerm("i");
        ac.addTerm("bar");
        ac.addTerm("zoo");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "bar", "barometer", "i", "integer", "integral", "zen", "zoo", "zoologist"
        ));
        assertEquals(solution, ac.getSortedTerms());
    }
    @Test
    public void getSortedTerms_t8() {
        ac.addTerm("carve");
        ac.addTerm("canned");
        ac.addTerm("add");
        ac.addTerm("argue");
        ac.addTerm("bar");
        ac.addTerm("bad");
        ac.addTerm("sour");
        ac.addTerm("sorted");
        ac.addTerm("tent");
        ac.addTerm("tended");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "add", "argue", "bad", "bar", "canned", "carve", "sorted", "sour", "tended", "tent" 
        ));
        assertEquals(solution, ac.getSortedTerms());
    }
    @Test
    public void getSortedTerms_t9() {
        ac.addTerm("a");
        ac.addTerm("z");
        ac.addTerm("b");
        ac.addTerm("y");
        ac.addTerm("c");
        ac.addTerm("x");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "a", "b", "c", "x", "y", "z"
        ));
        assertEquals(solution, ac.getSortedTerms());
    }
    @Test
    public void getSortedTerms_t10() {
        ac.addTerm("b");
        ac.addTerm("bb");
        ac.addTerm("a");
        ac.addTerm("aa");
        ac.addTerm("c");
        ac.addTerm("cc");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "a", "aa", "b", "bb", "c", "cc"
        ));
        assertEquals(solution, ac.getSortedTerms());
    }
    
}