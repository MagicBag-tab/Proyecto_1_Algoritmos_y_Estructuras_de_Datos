import java.util.*;

public class Evaluator {
    private final Enviroment env;

    public Evaluator() {
        this.env = new Enviroment();
    }

    public Object evaluate(Object expr) {
        if (expr instanceof Integer) {
            return expr; // Si es un número, devolverlo directamente
        } else if (expr instanceof String) {
            // Si es una cadena entre comillas, devolverla como literal
            if (((String) expr).startsWith("\"") && ((String) expr).endsWith("\"")) {
                return ((String) expr).substring(1, ((String) expr).length() - 1); // Eliminar las comillas
            }
            // Si no, es una variable
            Object value = env.getVariable((String) expr);
            if (value.equals("UNDEFINED")) {
                throw new IllegalArgumentException("¡Advertencia! Tu Variable no ha sido definida: " + expr);
            }
            return value;
        } else if (expr instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<Object> exprList = (List<Object>) expr;
            if (exprList.isEmpty()) {
                return new ArrayList<>(); // Si la lista está vacía, devolver una lista vacía
            }

            Object firstElement = exprList.get(0);
            if (!(firstElement instanceof String)) {
                throw new IllegalArgumentException("¡Espera! El operador es inválido: " + expr);
            }

            String op = (String) firstElement;
            List<Object> args = new ArrayList<>(exprList.subList(1, exprList.size()));
            return apply(op, args); // Aplicar la operación
        }
        return null; // Si no es ninguno de los casos anteriores, devolver null
    }

    private Object apply(String op, List<Object> args) {
        switch (op) {
            case "QUOTE":
                return args.get(0); // Devolver el primer argumento sin evaluar
            case "SETQ":
                String varName = (String) args.get(0);
                Object evaluatedValue = evaluate(args.get(1));
                if (evaluatedValue instanceof String) {
                    try {
                        evaluatedValue = Integer.parseInt((String) evaluatedValue);
                    } catch (NumberFormatException e) {
                        // Si no se puede convertir a número, se deja como cadena
                    }
                }
                env.setVariable(varName, evaluatedValue);
                return evaluatedValue;
            case "DEFUN":
                if (args.get(1) instanceof List<?>) {
                    List<?> rawList = (List<?>) args.get(1);
                    List<String> paramList = new ArrayList<>();
                    for (Object item : rawList) {
                        if (item instanceof String) {
                            paramList.add((String) item);
                        } else {
                            throw new IllegalArgumentException("DEFUN necesita que los parámetros sean strings.");
                        }
                    }
                    env.defineFunction((String) args.get(0),
                            new LispFunction(paramList, new ArrayList<>(args.subList(2, args.size()))));
                } else {
                    throw new IllegalArgumentException("DEFUN espera una lista de parámetros.");
                }
                return null;
            case "+":
            case "-":
            case "*":
            case "/":
                List<Object> evaluatedArgs = new ArrayList<>();
                for (Object arg : args) {
                    evaluatedArgs.add(evaluate(arg));
                }
                return Arithmetics.apply(op, evaluatedArgs);
            case "EQUAL":
            case "ATOM":
            case "LIST":
                return Predicates.apply(op, args);
            case ">":
            case "<":
            case ">=":
            case "<=":
                // Validar que los argumentos sean números antes de comparar
                List<Object> numericArgs = new ArrayList<>();
                for (Object arg : args) {
                    Object evaluatedArg = evaluate(arg);
                    if (!(evaluatedArg instanceof Number)) {
                        throw new IllegalArgumentException("Los argumentos para " + op + " deben ser números.");
                    }
                    numericArgs.add(evaluatedArg);
                }
                return Predicates.apply(op, numericArgs);
            case "COND":
                return evalCond(args);
            case "PRINT":
                Object valueToPrint = evaluate(args.get(0));
                System.out.println(valueToPrint);
                return valueToPrint;
            default:
                if (env.hasFunction(op)) {
                    return applyFunction(env.getFunction(op), args);
                }
                throw new IllegalArgumentException("¡Que curioso! Tu operador esta desconocido: " + op);
        }
    }

    private Object evalCond(List<Object> conditions) {
        for (Object condition : conditions) {
            if (!(condition instanceof List<?>)) {
                throw new IllegalArgumentException("COND espera una lista de condiciones.");
            }
            @SuppressWarnings("unchecked")
            List<Object> condPair = (List<Object>) condition;
            if (condPair.size() != 2) {
                throw new IllegalArgumentException("Cada condición en COND debe tener exactamente dos elementos.");
            }
            Object test = evaluate(condPair.get(0));
            if (isTrue(test)) {
                return evaluate(condPair.get(1));
            }
        }
        return null; // Si ninguna condición es verdadera, retorna null
    }

    private boolean isTrue(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Integer) {
            return ((Integer) value) != 0;
        } else if (value instanceof String) {
            return !((String) value).isEmpty();
        } else if (value instanceof List<?>) {
            return !((List<?>) value).isEmpty();
        } else if ("T".equals(value)) {
            return true; // Manejar el caso de T como verdadero
        }
        return value != null;
    }

    /**
     * Aplica una función definida por el usuario.
     */
    private Object applyFunction(LispFunction func, List<Object> args) {
        Enviroment localEnv = new Enviroment();

        List<String> parameters = func.getParams();
        List<Object> body = func.getBody();

        if (parameters.size() != args.size()) {
            throw new IllegalArgumentException(
                    "He identificado que el número es incorrecto en cuanto a los argumentos para esta función.");
        }

        // Asignar los valores sin evaluar primero
        for (int i = 0; i < parameters.size(); i++) {
            localEnv.setVariable(parameters.get(i), args.get(i));
        }

        Object result = null;
        for (Object expr : body) {
            result = evaluateExpressionWithEnv(expr, localEnv);
        }
        return result;
    }

    /**
     * Evalúa una expresión dentro de un entorno específico.
     */
    private Object evaluateExpressionWithEnv(Object expr, Enviroment env) {
        if (expr instanceof String) {
            Object value = env.getVariable((String) expr);
            if (value == null) {
                throw new IllegalArgumentException("¡Advertencia! Tu Variable no ha sido definida " + expr);
            }
            return evaluate(value);
        } else if (expr instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<Object> exprList = (List<Object>) expr;
            if (exprList.isEmpty()) {
                return null;
            }
            String op = (String) exprList.get(0);
            List<Object> args = new ArrayList<>();

            // Evaluar todos los argumentos antes de la operación
            for (int i = 1; i < exprList.size(); i++) {
                args.add(evaluateExpressionWithEnv(exprList.get(i), env)); // Evaluar cada argumento dentro del entorno
            }

            return apply(op, args); // Aplicar la operación con los valores evaluados
        }
        return expr;
    }
}