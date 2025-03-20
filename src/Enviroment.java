import java.util.HashMap;
import java.util.Map;

public class Enviroment { 
    private Map<String, Object> variables = new HashMap<>();
    private Map<String, LispFunction> functions;

    public Enviroment(){
        this.functions = new HashMap<>();
    }

    //--------------------------------------------------------------Implementación SETQ -----------------------------------------------------
    // Asignación de un valor a una variable
    public void setVariable(String nombre, Object valor){
        variables.put(nombre, valor); // Guardar en el entorno actual
    }

    // Obtiene el valor, o bien retorna "UNDEFINED" si no existe la variable
    public Object getVariable(String nombre){
        return variables.getOrDefault(nombre, "UNDEFINED"); // Buscar en el entorno actual
    }

    // Verificación de la variable
    public boolean hasVariable(String nombre){
        return variables.containsKey(nombre);
    }

    //----------------------------------------------------------- Implementación DEFUN -------------------------------------------------------

    // Define una nueva función en el entorno
    public void defineFunction(String nombre, LispFunction funcion){
        functions.put(nombre, funcion);
    }

    // Obtiene la función guardada
    public LispFunction getFunction(String nombre){
        return functions.get(nombre);
    }

    // Verifica si la función está definida
    public boolean hasFunction(String nombre){
        return functions.containsKey(nombre);
    }
}
