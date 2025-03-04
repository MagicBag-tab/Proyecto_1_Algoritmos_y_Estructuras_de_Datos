/*
 * Agarra el toString del que genero la clse Reader y lo tokeniza. (3/08/2025)
 * @author 
 */

import java.util.ArrayList;

public class Lexer {

    private String file; // lisp .txt
    private ArrayList<String> tokens; // guarda los tokens en un arraylist.
    private int count;

    /*
     * Constructor de Lexer.
     * Llamamos a la clase de llama Tokenizer.
     */
    public Lexer(String file, int count) {
        this.file = file;
        this.tokens = new ArrayList<>();
        this.count = count;

    }

}