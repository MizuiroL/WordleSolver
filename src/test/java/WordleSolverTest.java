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
        assertTrue(solver.checkWordValidity("abcde", false));
        assertTrue(solver.checkWordValidity("sport", true));
        assertFalse(solver.checkWordValidity("12345", false));
        assertFalse(solver.checkWordValidity("clue", false));
        assertFalse(solver.checkWordValidity("abcde", true));
    }

    @Test
    void checkCodeValidity() {
    }

    @Test
    void codify() {
    }

    @Test
    void applyRules() {
    }

    @Test
    void nPoints() {
    }

    @Test
    void applyFilter() {
    }
}
