import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class ParserTest {

    @Test
    public void testAtomWithInteger() {
        Parser parser = new Parser(List.of());
        assertEquals(42, parser.Atom("42"));
    }

    @Test
    public void testAtomWithNegativeInteger() {
        Parser parser = new Parser(List.of());
        assertEquals(-42, parser.Atom("-42"));
    }

    @Test
    public void testAtomWithSymbol() {
        Parser parser = new Parser(List.of());
        assertEquals("x", parser.Atom("x"));
    }

    @Test
    public void testAtomWithEmptyList() {
        Parser parser = new Parser(List.of());
        assertEquals(new ArrayList<>(), parser.Atom("()"));
    }

    @Test
    public void testParseSimpleExpression() {
        List<String> tokens = List.of("(", "+", "1", "2", ")");
        Parser parser = new Parser(tokens);
        List<Object> expected = List.of("+", 1, 2);
        assertEquals(expected, parser.parse());
    }

    @Test
    public void testParseNestedExpression() {
        List<String> tokens = List.of("(", "+", "1", "(", "*", "2", "3", ")", ")");
        Parser parser = new Parser(tokens);
        List<Object> expected = List.of("+", 1, List.of("*", 2, 3));
        assertEquals(expected, parser.parse());
    }

    @Test
    public void testParseEmptyExpression() {
        List<String> tokens = List.of("(", ")");
        Parser parser = new Parser(tokens);
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, parser.parse());
    }

    @Test
    public void testParseMultipleExpressions() {
        List<String> tokens = List.of("(", "+", "1", "2", ")", "(", "*", "3", "4", ")");
        Parser parser = new Parser(tokens);
        List<Object> expected = List.of(List.of("+", 1, 2), List.of("*", 3, 4));
        assertEquals(expected, parser.parse());
    }

    @Test
    public void testParseWithUnbalancedParentheses() {
        List<String> tokens = List.of("(", "+", "1", "2");
        Parser parser = new Parser(tokens);
        assertThrows(RuntimeException.class, parser::parse);
    }

    @Test
    public void testParseWithExtraClosingParenthesis() {
        List<String> tokens = List.of("(", "+", "1", "2", ")", ")");
        Parser parser = new Parser(tokens);
        assertThrows(RuntimeException.class, parser::parse);
    }
}