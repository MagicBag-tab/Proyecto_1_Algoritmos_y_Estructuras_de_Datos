import java.util.*;

public class Cond {
    public static Object eval(List<List<Object>> conditions, Map<String, Object> env) {
        for (List<Object> condition : conditions) {
            if ((boolean) evaluate(condition.get(0), env)) {
                return evaluate(condition.get(1), env);
            }
        }
        return null;
    }

    private static Object evaluate(Object expr, Map<String, Object> env) {
        if (expr instanceof String key && env.containsKey(key)) {
            expr = env.get(key); // Busca el valor de la variable en el entorno
        }
        if (expr instanceof Number num) {
            return num.doubleValue() != 0; // Considera 0 como falso y otros valores como verdaderos
        }
        if (expr == null) {
            return false; // Considera null como falso
        }
        if (expr instanceof Boolean) {
            return expr; // Evalúa expresiones booleanas
        }
        return expr; // Retorna el valor de la expresión
    }
}
