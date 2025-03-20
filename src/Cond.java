import java.util.*;


public class Cond {
    public static Object eval(List<List<Object>> conditions, Map<String, Object> env) {
        for (List<Object> condition : conditions) {
            if (condition.size() < 2) {
                throw new IllegalArgumentException("Cada condición debe tener al menos dos elementos: " + condition);
            }

            Object evaluatedCondition = evaluate(condition.get(0), env);
            if (evaluatedCondition instanceof Boolean && (Boolean) evaluatedCondition) {
                return evaluate(condition.get(1), env);
            }
        }
        return null;
    }

    private static Object evaluate(Object expr, Map<String, Object> env) {
        if (expr instanceof String key && env.containsKey(key)) {
            expr = env.get(key);
        }

        if (expr instanceof List<?> list && !list.isEmpty()) {
            String op = list.get(0).toString();
            List<Object> args = list.subList(1, list.size()).stream()
                                    .map(arg -> evaluate(arg, env))
                                    .toList();

            if (args.size() >= 2) {
                return Predicates.apply(op, args);
            } else {
                throw new IllegalArgumentException("Operador '" + op + "' requiere al menos dos argumentos: " + args);
            }
        }

        return expr instanceof Number || expr instanceof Boolean ? expr : null;
    }
}