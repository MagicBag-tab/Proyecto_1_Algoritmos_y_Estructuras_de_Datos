/*
 * Agarra el toString del que genero la clse Reader y lo tokeniza. (3/08/2025)
 * @author 
 */

import java.util.ArrayList;

public class Lexer {

    private String file; // lisp .txt
    private ArrayList<String> tokens; // guarda los tokens en un arraylist.

    /*
     * Constructor de Lexer.
     * Llamamos a la clase de llama Tokenizer.
     */
    public Lexer(String file) {
        this.file = file;
        this.tokens = new ArrayList<>();

    }

    /*
     * Tokenizer separa la cadena que se genera a partir del file (codigo de lisp) y
     * revisa que esten los parentesis y si hay espacios que separan a los
     * operadores.
     */
    public void Tokenizer() {
        StringBuilder token = new StringBuilder(); // cadena de caracteres donde se almacenan los tockens

        for (int i = 0; i < file.length(); i++) {
            char c = file.charAt(i); // cada caracter del file

            if (c == '(' || c == ')') {
                if (token.length() > 0) {
                    tokens.add(token.toString()); // agreca el token a el arraylist de tockens
                    token.setLength(0); // reinicia el tocken ya que indican el inicio o el final de una instrucción
                }
            } else if (file.isEmpty()) {

            }
        }

    }

}