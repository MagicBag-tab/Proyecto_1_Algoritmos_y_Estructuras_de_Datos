import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class QuoteTest {

    @Test
    public void testQuoteWithInteger() {
        Quote quote = new Quote();
        assertEquals(42, quote.quote(42));
    }

    @Test
    public void testQuoteWithString() {
        Quote quote = new Quote();
        assertEquals("hello", quote.quote("hello"));
    }

    @Test
    public void testQuoteWithList() {
        Quote quote = new Quote();
        List<Object> list = List.of(1, 2, 3);
        assertEquals(list, quote.quote(list));
    }

    @Test
    public void testQuoteWithNestedList() {
        Quote quote = new Quote();
        List<Object> nestedList = List.of(1, List.of(2, 3));
        assertEquals(nestedList, quote.quote(nestedList));
    }

    @Test
    public void testQuoteWithNull() {
        Quote quote = new Quote();
        assertNull(quote.quote(null));
    }

    @Test
    public void testQuoteWithBoolean() {
        Quote quote = new Quote();
        assertEquals(true, quote.quote(true));
        assertEquals(false, quote.quote(false));
    }
}