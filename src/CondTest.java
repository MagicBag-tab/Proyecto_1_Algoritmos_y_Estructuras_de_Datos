import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class CondTest {

    @Test
    public void testSingleConditionTrue() {
        List<List<Object>> conditions = List.of(
            List.of(true, "result")
        );
        Map<String, Object> env = new HashMap<>();
        assertEquals("result", Cond.eval(conditions, env));
    }

    @Test
    public void testSingleConditionFalse() {
        List<List<Object>> conditions = List.of(
            List.of(false, "result")
        );
        Map<String, Object> env = new HashMap<>();
        assertNull(Cond.eval(conditions, env));
    }

    @Test
    public void testMultipleConditionsFirstTrue() {
        List<List<Object>> conditions = List.of(
            List.of(true, "first"),
            List.of(false, "second")
        );
        Map<String, Object> env = new HashMap<>();
        assertEquals("first", Cond.eval(conditions, env));
    }

    @Test
    public void testMultipleConditionsSecondTrue() {
        List<List<Object>> conditions = List.of(
            List.of(false, "first"),
            List.of(true, "second")
        );
        Map<String, Object> env = new HashMap<>();
        assertEquals("second", Cond.eval(conditions, env));
    }

    @Test
    public void testConditionWithVariableTrue() {
        List<List<Object>> conditions = List.of(
            List.of("var", "result")
        );
        Map<String, Object> env = new HashMap<>();
        env.put("var", true);
        assertEquals("result", Cond.eval(conditions, env));
    }

    @Test
    public void testConditionWithVariableFalse() {
        List<List<Object>> conditions = List.of(
            List.of("var", "result")
        );
        Map<String, Object> env = new HashMap<>();
        env.put("var", false);
        assertNull(Cond.eval(conditions, env));
    }

    @Test
    public void testConditionWithNumericValueTrue() {
        List<List<Object>> conditions = List.of(
            List.of(1, "result")
        );
        Map<String, Object> env = new HashMap<>();
        assertEquals("result", Cond.eval(conditions, env));
    }

    @Test
    public void testConditionWithNumericValueFalse() {
        List<List<Object>> conditions = List.of(
            List.of(0, "result")
        );
        Map<String, Object> env = new HashMap<>();
        assertNull(Cond.eval(conditions, env));
    }

    @Test
    public void testConditionWithBooleanValueTrue() {
        List<List<Object>> conditions = List.of(
            List.of(true, "result")
        );
        Map<String, Object> env = new HashMap<>();
        assertEquals("result", Cond.eval(conditions, env));
    }

    @Test
    public void testConditionWithBooleanValueFalse() {
        List<List<Object>> conditions = List.of(
            List.of(false, "result")
        );
        Map<String, Object> env = new HashMap<>();
        assertNull(Cond.eval(conditions, env));
    }

    @Test
    public void testEmptyConditions() {
        List<List<Object>> conditions = List.of();
        Map<String, Object> env = new HashMap<>();
        assertNull(Cond.eval(conditions, env));
    }

    @Test
    public void testConditionWithNullValue() {
        List<List<Object>> conditions = new ArrayList<>();
        conditions.add(Arrays.asList(null, "result"));
        Map<String, Object> env = new HashMap<>();
        assertNull(Cond.eval(conditions, env));
    }

    @Test
    public void testMulipleVariable() {
        List<List<Object>> conditions = List.of(
            List.of("var1", "result1"),
            List.of(0, "result2"),
            List.of(true, "result3")
        );
        Map<String, Object> env = new HashMap<>();
        env.put("var1", false);
        assertEquals("result3", Cond.eval(conditions, env));
    }

    @Test
    public void testMulipleVariableCon2Verdaderas() {
        List<List<Object>> conditions = List.of(
            List.of("var1", "result1"),
            List.of(1, "result2"),
            List.of(true, "result3")
        );
        Map<String, Object> env = new HashMap<>();
        env.put("var1", false);
        assertEquals("result2", Cond.eval(conditions, env));
    }

    @Test
    public void testMulipleVariableCon2VerdaderasParaFallar() {
        List<List<Object>> conditions = List.of(
            List.of("var1", "result1"),
            List.of(1, "result2"),
            List.of(true, "result3")
        );
        Map<String, Object> env = new HashMap<>();
        env.put("var1", false);
        assertEquals("result3", Cond.eval(conditions, env));
    } //Debe de fallar
}