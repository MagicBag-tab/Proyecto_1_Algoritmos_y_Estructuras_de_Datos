import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;



public class CondTest {

    @Test
    public void testEval_TrueCondition() {
        Map<String, Object> env = new HashMap<>();
        List<List<Object>> conditions = List.of(
            List.of(List.of(">", 10, 5), "Mayor"),
            List.of(List.of("=", 10, 5), "Igual"),
            List.of(List.of("<", 10, 5), "Menor")
        );

        Object result = Cond.eval(conditions, env);
        assertEquals("Mayor", result);
    }

    @Test
    public void testEval_FalseConditions() {
        Map<String, Object> env = new HashMap<>();
        List<List<Object>> conditions = List.of(
            List.of(List.of("<", 10, 5), "Menor"),
            List.of(List.of("EQUAL", 10, 5), "Igual") // Cambiado de "=" a "EQUAL"
        );

        Object result = Cond.eval(conditions, env);
        assertNull(result); // No se cumple ninguna condición
    }
    @Test
    public void testEval_VariableInEnvironment() {
        Map<String, Object> env = new HashMap<>();
        env.put("x", 10);
        env.put("y", 20);

        List<List<Object>> conditions = List.of(
            List.of(List.of("<", "x", "y"), "x es menor que y"),
            List.of(List.of("=", "x", "y"), "x es igual a y")
        );

        Object result = Cond.eval(conditions, env);
        assertEquals("x es menor que y", result);
    }

    @Test
    public void testEval_NoMatchingCondition() {
        Map<String, Object> env = new HashMap<>();
        List<List<Object>> conditions = List.of(
            List.of(List.of(">", 2, 5), "Mayor")
        );

        Object result = Cond.eval(conditions, env);
        assertNull(result); // No se cumple ninguna condición
    }

    @Test
    public void testEval_InvalidArgumentTypes() {
        Map<String, Object> env = new HashMap<>();
        List<List<Object>> conditions = List.of(
            List.of(List.of(">", "hola", "mundo"), "Error")
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Cond.eval(conditions, env);
        });

        assertTrue(exception.getMessage().contains("Los argumentos para '>' deben ser enteros"));
    }
}
