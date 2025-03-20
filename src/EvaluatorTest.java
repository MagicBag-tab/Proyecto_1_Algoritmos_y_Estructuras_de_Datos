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
        assertEquals("T", evaluator.evaluate(List.of("EQUAL", 2, 2)));
        assertEquals("NIL", evaluator.evaluate(List.of("EQUAL", 2, 3)));
    }

    @Test
    public void testEvaluateAtom() {
        Evaluator evaluator = new Evaluator();
        assertEquals("T", evaluator.evaluate(List.of("ATOM", 2)));
        assertEquals("NIL", evaluator.evaluate(List.of("ATOM", List.of(1, 2))));
    }

    @Test
    public void testEvaluateList() {
        Evaluator evaluator = new Evaluator();
        assertEquals("T", evaluator.evaluate(List.of("LIST", List.of(1, 2, 3))));
    }

    @Test
    public void testEvaluateLessThan() {
        Evaluator evaluator = new Evaluator();
        assertEquals("T", evaluator.evaluate(List.of("<", 2, 3)));
        assertEquals("NIL", evaluator.evaluate(List.of("<", 3, 2)));
    }

    @Test
    public void testEvaluateGreaterThan() {
        Evaluator evaluator = new Evaluator();
        assertEquals("T", evaluator.evaluate(List.of(">", 3, 2)));
        assertEquals("NIL", evaluator.evaluate(List.of(">", 2, 3)));
    }

    @Test
    public void testEvaluateDefun() {
        Evaluator evaluator = new Evaluator();
        evaluator.evaluate(List.of("DEFUN", "square", List.of("x"), List.of("*", "x", "x")));
        assertEquals(9, evaluator.evaluate(List.of("square", 3)));
    }

    @Test
    public void testEvaluateUserDefinedFunction() {
        Evaluator evaluator = new Evaluator();
        evaluator.evaluate(List.of("DEFUN", "add", List.of("x", "y"), List.of("+", "x", "y")));
        assertEquals(5, evaluator.evaluate(List.of("add", 2, 3)));
    }

    @Test
    public void testEvaluateNestedExpressions() {
        Evaluator evaluator = new Evaluator();
        assertEquals(14, evaluator.evaluate(List.of("+", 2, List.of("*", 3, 4))));
    }

    @Test
    public void testEvaluateUndefinedVariable() {
        Evaluator evaluator = new Evaluator();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            evaluator.evaluate("undefinedVar");
        });
        assertEquals("¡Advertencia! Tu Variable no ha sido definida: undefinedVar", exception.getMessage());
    }
}
