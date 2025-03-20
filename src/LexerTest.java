import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class LexerTest {

    @Test
    public void testTokenizerSimpleExpression() {
        Lexer lexer = new Lexer("(+ 1 2)");
        lexer.Tokenizer();
        List<String> expectedTokens = List.of("(", "+", "1", "2", ")");
        assertEquals(expectedTokens, lexer.getTokens());
    }

    @Test
    public void testTokenizerNestedExpression() {
        Lexer lexer = new Lexer("(+ 1 (* 2 3))");
        lexer.Tokenizer();
        List<String> expectedTokens = List.of("(", "+", "1", "(", "*", "2", "3", ")", ")");
        assertEquals(expectedTokens, lexer.getTokens());
    }

    @Test
    public void testTokenizerWithWhitespace() {
        Lexer lexer = new Lexer("   (   +   1   2   )   ");
        lexer.Tokenizer();
        List<String> expectedTokens = List.of("(", "+", "1", "2", ")");
        assertEquals(expectedTokens, lexer.getTokens());
    }

    @Test
    public void testTokenizerEmptyExpression() {
        Lexer lexer = new Lexer("()");
        lexer.Tokenizer();
        List<String> expectedTokens = List.of("(", ")");
        assertEquals(expectedTokens, lexer.getTokens());
    }

    @Test
    public void testTokenizerMultipleExpressions() {
        Lexer lexer = new Lexer("(+ 1 2) (* 3 4)");
        lexer.Tokenizer();
        List<String> expectedTokens = List.of("(", "+", "1", "2", ")", "(", "*", "3", "4", ")");
        assertEquals(expectedTokens, lexer.getTokens());
    }

    @Test
    public void testTokenizerWithSymbols() {
        Lexer lexer = new Lexer("(setq x 10)");
        lexer.Tokenizer();
        List<String> expectedTokens = List.of("(", "setq", "x", "10", ")");
        assertEquals(expectedTokens, lexer.getTokens());
    }

    @Test
    public void testTokenizerWithQuotes() {
        Lexer lexer = new Lexer("(quote (1 2 3))");
        lexer.Tokenizer();
        List<String> expectedTokens = List.of("(", "quote", "(", "1", "2", "3", ")", ")");
        assertEquals(expectedTokens, lexer.getTokens());
    }
}