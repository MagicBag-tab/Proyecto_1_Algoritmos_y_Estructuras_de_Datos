import java.util.HashMap;
import java.util.Map;

public class Enviroment {
    private Map<String, Object> variables = new HashMap<>();
    private Map<String, LispFunction> functions = new HashMap<>();

    // Asignación de un valor a una variable
    public void setVariable(String nombre, Object valor) {
        variables.put(nombre, valor);
    }

    // Obtiene el valor de una variable
    public Object getVariable(String nombre) {
        return variables.getOrDefault(nombre, "UNDEFINED");
    }

    // Verifica si una variable existe
    public boolean hasVariable(String nombre) {
        return variables.containsKey(nombre);
    }

    // Define una nueva función en el entorno
    public void defineFunction(String nombre, LispFunction funcion) {
        functions.put(nombre, funcion);
    }

    // Obtiene la función guardada
    public LispFunction getFunction(String nombre) {
        return functions.get(nombre);
    }

    // Verifica si la función está definida
    public boolean hasFunction(String nombre) {
        return functions.containsKey(nombre);
    }
}