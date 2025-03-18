import java.util.List;

public class Arithmetics {
    public static Object apply(String op, List<Object> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("No se proporcionaron argumentos.");
        }

        List<Integer> numbers = args.stream()
                .map(obj -> {
                    if (obj instanceof Integer) {
                        return (Integer) obj;
                    } else {
                        throw new IllegalArgumentException("Todos los argumentos deben ser enteros.");
                    }
                }).toList();

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
