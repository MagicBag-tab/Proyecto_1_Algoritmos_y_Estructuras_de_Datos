import java.util.List;
import java.util.ArrayList;

public class Arithmetics {
    public static Object apply(String op, List<Object> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("No se proporcionaron los argumentos necesarios.");
        }

        // Evaluar cada argumento antes de realizar la operación
        List<Integer> numbers = new ArrayList<>();
        Evaluator evaluator = new Evaluator();
        
        for (Object arg : args) {
            if (arg instanceof Integer) {
                numbers.add((Integer) arg);
            } else if (arg instanceof String) {
                Object evaluated = evaluator.evaluate(arg);
                if (evaluated instanceof Integer) {
                    numbers.add((Integer) evaluated);
                } else {
                    throw new IllegalArgumentException("Ha ocurrido un error y es que se esperaba un número, pero se obtuvo: " + evaluated);
                }
            } else {
                throw new IllegalArgumentException("Ha ocurrido un error ya que el tipo de dato  es inesperado en operación aritmética: " + arg);
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
                        throw new ArithmeticException("¡OJO! No se puede dividir por cero.");
                    }
                    result /= numbers.get(i);
                }
                return result;

            default:
                throw new IllegalArgumentException("Espera! El operador es inválido:  " + op);
        }
    }
}
