import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class ArithmeticsTest {

    @Test
    public void testAddition() {
        List<Object> args = Arrays.asList(1, 2, 3);
        assertEquals(6, Arithmetics.apply("+", args));
    }

    @Test
    public void testSubtraction() {
        List<Object> args = Arrays.asList(10, 3, 2);
        assertEquals(5, Arithmetics.apply("-", args));
    }

    @Test
    public void testMultiplication() {
        List<Object> args = Arrays.asList(2, 3, 4);
        assertEquals(24, Arithmetics.apply("*", args));
    }

    @Test
    public void testDivision() {
        List<Object> args = Arrays.asList(20, 2, 2);
        assertEquals(5, Arithmetics.apply("/", args));
    }

    @Test
    public void testDivisionByZero() {
        List<Object> args = Arrays.asList(10, 0);
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            Arithmetics.apply("/", args);
        });
        assertEquals("No se puede dividir por cero.", exception.getMessage());
    }

    @Test
    public void testInvalidOperator() {
        List<Object> args = Arrays.asList(1, 2, 3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Arithmetics.apply("%", args);
        });
        assertEquals("Operador desconocido: %", exception.getMessage());
    }

    @Test
    public void testEmptyArguments() {
        List<Object> args = Arrays.asList();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Arithmetics.apply("+", args);
        });
        assertEquals("No se proporcionaron argumentos.", exception.getMessage());
    }

    @Test
    public void testNonIntegerArgument() {
        List<Object> args = Arrays.asList(1, "two", 3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Arithmetics.apply("+", args);
        });
        assertEquals("Todos los argumentos deben ser enteros.", exception.getMessage());
    }
}
