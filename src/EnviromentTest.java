import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class EnviromentTest {

    @Test
    public void testSetAndGetVariable() {
        Enviroment env = new Enviroment();
        env.setVariable("x", 42);
        assertEquals(42, env.getVariable("x"));
    }

    @Test
    public void testGetVariableNotSet() {
        Enviroment env = new Enviroment();
        assertEquals("UNDEFINED", env.getVariable("y"));
    }

    @Test
    public void testHasVariable() {
        Enviroment env = new Enviroment();
        env.setVariable("x", 42);
        assertTrue(env.hasVariable("x"));
        assertFalse(env.hasVariable("y"));
    }

    @Test
    public void testDefineAndGetFunction() {
        Enviroment env = new Enviroment();
        LispFunction func = new LispFunction(List.of("a", "b"), List.of("+", "a", "b"));
        env.defineFunction("add", func);
        assertEquals(func, env.getFunction("add"));
    }

    @Test
    public void testGetFunctionNotDefined() {
        Enviroment env = new Enviroment();
        assertNull(env.getFunction("subtract"));
    }

    @Test
    public void testHasFunction() {
        Enviroment env = new Enviroment();
        LispFunction func = new LispFunction(List.of("a", "b"), List.of("+", "a", "b"));
        env.defineFunction("add", func);
        assertTrue(env.hasFunction("add"));
        assertFalse(env.hasFunction("subtract"));
    }
}
