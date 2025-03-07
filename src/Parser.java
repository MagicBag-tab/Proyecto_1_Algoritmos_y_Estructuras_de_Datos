/*
 * Parser utiliza los tockens generados en lexter y los ordena y estructura para que luego pasen a evaluator
 * (3/03/2025) (6/03/2025)
 * @authors
 */

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

}
