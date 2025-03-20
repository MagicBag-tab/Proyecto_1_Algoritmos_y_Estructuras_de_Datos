import java.util.List;
import java.util.ArrayList;

public class Arithmetics {
    public static Object apply(String op, List<Object> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("No se proporcionaron argumentos.");
        }

        // Evaluar cada argumento antes de realizar la operación
        List<Integer> numbers = new ArrayList<>();

        for (Object arg : args) {
            if (arg instanceof Integer) {
                numbers.add((Integer) arg);
            } else {
                throw new IllegalArgumentException("Todos los argumentos deben ser enteros.");
            }
        }

        int result = numbers.get(0);

        switch (op) {
            case "+":
                for (int i = 1; i < numbers.size(); i++) {
                    result += numbers.get(i);
                }
                return result;

            case "-":
                for (int i = 1; i < numbers.size(); i++) {
                    result -= numbers.get(i);
                }
                return result;

            case "*":
                for (int i = 1; i < numbers.size(); i++) {
                    result *= numbers.get(i);
                }
                return result;

            case "/":
                for (int i = 1; i < numbers.size(); i++) {
                    if (numbers.get(i) == 0) {
                        throw new ArithmeticException("No se puede dividir por cero.");
                    }
                    result /= numbers.get(i);
                }
                return result;

            default:
                throw new IllegalArgumentException("Operador desconocido: " + op);
        }
    }
}
