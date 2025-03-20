import java.util.HashMap;
import java.util.Map;

public class SetQ {
    public Map<String, Object> variables;

    // Define el parámetro de la variable para que se cree una nueva "Key" y "Value"
    // por función
    public SetQ() {
        this.variables = new HashMap<>();
    }

    // Método para asignar un valor en específico a una variable
    public void setVariables(String nombre, Object valor) {
        if (valor instanceof String && variables.containsKey(valor)) {
            variables.put(nombre, variables.get(valor));
        } else {
            variables.put(nombre, valor);
        }
    }

    // Método para obtener el valor de una variable
    public Object getVariavles(String nombre) {
        return variables.getOrDefault(nombre, null);
    }

    // Método de verificación para identificar si una variable existe
    public boolean ExistVariable(String nombre) {
        return variables.containsKey(nombre);
    }
}
