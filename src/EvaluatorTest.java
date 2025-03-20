import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class EvaluatorTest {

    @Test
    public void testEvaluateInteger() {
        Evaluator evaluator = new Evaluator();
        assertEquals(42, evaluator.evaluate(42));
    }

    @Test
    public void testEvaluateVariable() {
        Evaluator evaluator = new Evaluator();
        evaluator.evaluate(List.of("SETQ", "x", 42));
        assertEquals(42, evaluator.evaluate("x"));
    }

    @Test
    public void testEvaluateQuote() {
        Evaluator evaluator = new Evaluator();
        assertEquals(42, evaluator.evaluate(List.of("QUOTE", 42)));
    }

    @Test
    public void testEvaluateSetq() {
        Evaluator evaluator = new Evaluator();
        evaluator.evaluate(List.of("SETQ", "x", 42));
        assertEquals(42, evaluator.evaluate("x"));
    }

    @Test
    public void testEvaluateAddition() {
        Evaluator evaluator = new Evaluator();
        assertEquals(5, evaluator.evaluate(List.of("+", 2, 3)));
    }

    @Test
    public void testEvaluateSubtraction() {
        Evaluator evaluator = new Evaluator();
        assertEquals(1, evaluator.evaluate(List.of("-", 3, 2)));
    }

    @Test
    public void testEvaluateMultiplication() {
        Evaluator evaluator = new Evaluator();
        assertEquals(6, evaluator.evaluate(List.of("*", 2, 3)));
    }

    @Test
    public void testEvaluateDivision() {
        Evaluator evaluator = new Evaluator();
        assertEquals(2, evaluator.evaluate(List.of("/", 6, 3)));
    }

    @Test
    public void testEvaluateEqual() {
        Evaluator evaluator = new Evaluator();
        assertTrue((Boolean) evaluator.evaluate(List.of("EQUAL", 2, 2)));
        assertFalse((Boolean) evaluator.evaluate(List.of("EQUAL", 2, 3)));
    }

    @Test
    public void testEvaluateAtom() {
        Evaluator evaluator = new Evaluator();
        assertTrue((Boolean) evaluator.evaluate(List.of("ATOM", 2)));
        assertFalse((Boolean) evaluator.evaluate(List.of("ATOM", List.of(1, 2))));
    }

    @Test
    public void testEvaluateList() {
        Evaluator evaluator = new Evaluator();
        assertTrue((Boolean) evaluator.evaluate(List.of("LIST", 1, 2, 3)));
    }//Cambiar test

    @Test
    public void testEvaluateLessThan() {
        Evaluator evaluator = new Evaluator();
        assertTrue((Boolean) evaluator.evaluate(List.of("<", 2, 3)));
        assertFalse((Boolean) evaluator.evaluate(List.of("<", 3, 2)));
    }

    @Test
    public void testEvaluateGreaterThan() {
        Evaluator evaluator = new Evaluator();
        assertTrue((Boolean) evaluator.evaluate(List.of(">", 3, 2)));
        assertFalse((Boolean) evaluator.evaluate(List.of(">", 2, 3)));
    }
}
