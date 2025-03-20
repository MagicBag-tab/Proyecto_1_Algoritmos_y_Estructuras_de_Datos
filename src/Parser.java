/*
 * Parser utiliza los tokens generados en Lexer y los ordena y estructura para que luego pasen a Evaluator.
 * (3/03/2025) (6/03/2025)
 * @authors
 */

 import java.util.ArrayList;
 import java.util.List;
 
 public class Parser {
     private List<String> tokens; 
     private int position;
 
     /*
      * Constructor de Parser (6/03/2024)
      */
     public Parser(List<String> tokens) {
         this.tokens = tokens;
         this.position = 0;
     }
 
     /*
      * Verifica si el token es un entero o si es un
      * símbolo y lo devuelve para ser utilizado en el método de parse.
      */
     public Object Atom(String token) {
         if (token.matches("-?\\d+")) { // Verifica si es un número entero
             return Integer.parseInt(token);
         }
         return token; // Devuelve el símbolo si no es un número
     }
 
     /*
      * Convierte los tokens en una estructura de listas utilizando los
      * paréntesis para definir la estructura correcta y verifica que
      * estén colocados correctamente sin que haya un error de sintaxis
      * en el código enviado.
      */
 
     @SuppressWarnings("unchecked")
     public Object parse() {
         List<Object> stack = new ArrayList<>();
         List<Object> current = new ArrayList<>();
         List<Object> root = current; 
 
         for (position = 0; position < tokens.size(); position++) {
             String token = tokens.get(position);
 
             if (token.equals("(")) {
                 stack.add(current);
                 current = new ArrayList<>();
             } else if (token.equals(")")) {
                 if (stack.isEmpty()) {
                     throw new RuntimeException("¡Espera! Hubo un Error de sintaxis debido a paréntesis desbalanceados.");
                 }
                 List<Object> complete = new ArrayList<>(current);
                 current = (List<Object>) stack.remove(stack.size() - 1);
                 current.add(complete);
             } else {
                 current.add(Atom(token));
             }
         }
 
         if (!stack.isEmpty()) {
             throw new RuntimeException("¡Cuidado! Hubo un error de sintaxis al parecer faltan cerrar paréntesis.");
         }
 
         return root.size() == 1 ? root.get(0) : root; 
     }
 }
 