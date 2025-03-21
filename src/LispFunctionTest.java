import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class LispFunctionTest {

    @Test
    public void testConstructorAndGetParams() {
        List<String> params = List.of("a", "b");
        List<Object> body = List.of("+", "a", "b");
        LispFunction function = new LispFunction(params, body);
        assertEquals(params, function.getParams());
    }

    @Test
    public void testConstructorAndGetBody() {
        List<String> params = List.of("a", "b");
        List<Object> body = List.of("+", "a", "b");
        LispFunction function = new LispFunction(params, body);
        assertEquals(body, function.getBody());
    }

    @Test
    public void testConstructorWithEmptyParams() {
        List<String> params = List.of();
        List<Object> body = List.of("+", 1, 2);
        LispFunction function = new LispFunction(params, body);
        assertEquals(params, function.getParams());
        assertEquals(body, function.getBody());
    }

    @Test
    public void testConstructorWithEmptyBody() {
        List<String> params = List.of("a", "b");
        List<Object> body = List.of();
        LispFunction function = new LispFunction(params, body);
        assertEquals(params, function.getParams());
        assertEquals(body, function.getBody());
    }

    @Test
    public void testConstructorWithNullParams() {
        List<String> params = null;
        List<Object> body = List.of("+", 1, 2);
        assertThrows(NullPointerException.class, () -> {
            new LispFunction(params, body);
        });
    }

    @Test
    public void testConstructorWithNullBody() {
        List<String> params = List.of("a", "b");
        List<Object> body = null;
        assertThrows(NullPointerException.class, () -> {
            new LispFunction(params, body);
        });
    }
}