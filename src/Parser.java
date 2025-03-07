/*
 * Parser utiliza los tockens generados en lexter y los ordena y estructura para que luego pasen a evaluator
 * (3/03/2025) (6/03/2025)
 * @authors
 */

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<String> tokens; // Definimos tokens
    private int position; // utilizada para saber la posición en donde se encuentra en la lista

    /*
     * Constructor de Parse (6/03/2024)
     */
    public Parser(List<String> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    /*
     * Verifica si el token es un entero o si es un
     * símbolo y lo devuelve para ser utilizado en el metodo de parse.
     */
    public Object Atom(String token) {
        if (token.matches("-?\\d+")) {
            return Integer.parseInt(token);
        }
        return token;
    }

    /*
     * Convierte los tokens en una estructura de listas utilizando los
     * paréntesis para definir la estructura correcta y verifica que
     * este colocados correctamente sin que haya un error de sintaxis
     * en el código enviado.
     */

    @SuppressWarnings("unchecked")
    public Object parse() {
        List<Object> stack = new ArrayList<>();
        List<Object> current = new ArrayList<>();

        for (position = 0; position < tokens.size(); position++) {
            String token = tokens.get(position);

            if (token.equals("(")) {
                stack.add(current);
                current = new ArrayList<>();
            } else if (token.equals(")")) {
                if (stack.isEmpty()) {
                    throw new RuntimeException("Error de sintaxís");
                }
                List<Object> complete = current;
                current = (List<Object>) stack.remove(stack.size() - 1);
                current.add(complete);
            } else {
                current.add(Atom(token));
            }
        }
        if (!stack.isEmpty()) {
            throw new RuntimeException("Error de sintaxís");
        }

        return current;
    }

}
