import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class CondTest {

    @Test
    void testEvalConditionTrue() {
        List<List<Object>> conditions = List.of(
            List.of(true, "Success"),   // Si la condición es `true`, devuelve "Success"
            List.of(false, "Failure")
        );
        Map<String, Object> env = new HashMap<>();
        
        Object result = Cond.eval(conditions, env);
        assertEquals("Success", result);
    }

    @Test
    void testEvalConditionFalse() {
        List<List<Object>> conditions = List.of(
            List.of(false, "Should not happen"),
            List.of(true, "This happens")
        );
        Map<String, Object> env = new HashMap<>();
        
        Object result = Cond.eval(conditions, env);
        assertEquals("This happens", result);
    }

    @Test
    void testEvalWithVariableInEnv() {
        List<List<Object>> conditions = List.of(
            List.of("x", "Variable is true"),
            List.of(false, "Should not happen")
        );
        Map<String, Object> env = new HashMap<>();
        env.put("x", true);  // x es true en el entorno
        
        Object result = Cond.eval(conditions, env);
        assertEquals("Variable is true", result);
    }

    @Test
    void testEvalReturnsNullWhenNoConditionIsTrue() {
        List<List<Object>> conditions = List.of(
            List.of(false, "Not this"),
            List.of(false, "Not this either")
        );
        Map<String, Object> env = new HashMap<>();
        
        Object result = Cond.eval(conditions, env);
        assertNull(result);  // Si ninguna condición se cumple, debería devolver null
    }
}
