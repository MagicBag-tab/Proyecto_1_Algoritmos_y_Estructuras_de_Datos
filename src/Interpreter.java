import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Clase `Interpreter` que ejecuta el intérprete de Lisp utilizando `Parser`, `Evaluator` y `Enviroment`.
 */
public class Interpreter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Evaluator evaluator = new Evaluator();

        System.out.println("---------------------------------------- Interprete de Lisp ---------------------------------------------------------");
        System.out.println("Escribe exit para salir de este interprete.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("¡Has salido de este interprete.");
                System.out.println("¡Hasta la próxima :D !");
                break;
            }

            try {
                // Tokenización por espacios
                List<String> tokens = tokenize(input);
                // Parseo de los tokens a una estructura de datos en List<Object>
                Parser parser = new Parser(tokens);
                Object parsedExpression = parser.parse();
                // Evaluación de la expresión Lisp
                Object result = evaluator.evaluate(parsedExpression);
                // Mostrar resultado
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Ha ocurrido un error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * Método para tokenizar la entrada dividiendo en espacios y preservando los paréntesis.
     * @param input Línea de entrada del usuario.
     * @return Lista de tokens.
     */
    public static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (c == '(' || c == ')') {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else if (Character.isWhitespace(c)) {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
            } else {
                token.append(c);
            }
        }
        
        if (token.length() > 0) {
            tokens.add(token.toString());
        }

        return tokens;
    }
}
