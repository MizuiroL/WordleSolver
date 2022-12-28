import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordleSolverTest {
    private WordleSolver solver;

    @BeforeEach
    public void setUp() {
        solver = new WordleSolver();
    }

    @Test
    void checkWordValidity() {
        assertTrue(solver.checkWordValidity("abcde", false));   // Five letter word, doesn't check the dictionary
        assertTrue(solver.checkWordValidity("sport", true));    // Five letter word in the dictionary
        assertFalse(solver.checkWordValidity("abcd1", false));  // Contains one number meaning it's not a valid word
        assertFalse(solver.checkWordValidity("12345", false));  // Same as above
        assertFalse(solver.checkWordValidity("clue", false));   // Too short
        assertFalse(solver.checkWordValidity("escape", false)); // Too long
        assertFalse(solver.checkWordValidity("abcde", true));   // Right size but not in dictionary
    }

    @Test
    void checkCodeValidity() {
        assertTrue(solver.checkCodeValidity("00000"));
        assertTrue(solver.checkCodeValidity("11111"));
        assertTrue(solver.checkCodeValidity("22222"));
        assertTrue(solver.checkCodeValidity("01210"));
        assertTrue(solver.checkCodeValidity("00001"));
        assertTrue(solver.checkCodeValidity("20202"));
        assertTrue(solver.checkCodeValidity("02012"));
        assertFalse(solver.checkCodeValidity("02013"));  // Contains an invalid number
        assertFalse(solver.checkCodeValidity("0000"));   // Too short
        assertFalse(solver.checkCodeValidity("1111"));
        assertFalse(solver.checkCodeValidity("2222"));
        assertFalse(solver.checkCodeValidity("000000")); // Too long
        assertFalse(solver.checkCodeValidity("111111"));
        assertFalse(solver.checkCodeValidity("222222"));
        assertFalse(solver.checkCodeValidity("o2010"));  // Contains an invalid character
        assertFalse(solver.checkCodeValidity(new String())); // Invalid string
        assertFalse(solver.checkCodeValidity("*%$%&"));


    }

    @Test
    void codify() {
        // I previously checked the code that will be used as parameter so I know to expect only strings composed of 5 numbers
        assertArrayEquals(new int[]{0, 0, 0, 0, 0}, solver.codify("00000"));
        assertArrayEquals(new int[]{1, 1, 1, 1, 1}, solver.codify("11111"));
        assertArrayEquals(new int[]{2, 2, 2, 2, 2}, solver.codify("22222"));
        assertArrayEquals(new int[]{0, 1, 2, 1, 0}, solver.codify("01210"));
    }

    @Test
    void applyRules() {
    }

    @Test
    void nPoints() {
        assertEquals("", solver.nPoints(0));
        assertEquals(".", solver.nPoints(1));
        assertEquals("..", solver.nPoints(2));
        assertEquals("...", solver.nPoints(3));
        assertEquals("....", solver.nPoints(4));
    }

    @Test
    void applyFilter() {
    }
}
