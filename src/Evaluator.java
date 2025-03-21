import java.util.*;

public class Evaluator {
    private final Enviroment env;

    public Evaluator() {
        this.env = new Enviroment();
        env.setVariable("T", "T");
        env.setVariable("NIL", "NIL");
    }

    public Object evaluate(Object expr) {
        return evaluate(expr, env); // Default to global environment
    }

    private Object evaluate(Object expr, Enviroment currentEnv) {
        if (expr instanceof Integer) {
            return expr;
        } else if (expr instanceof String) {
            String strExpr = (String) expr;
            // Handle T and NIL as special cases
            if ("T".equals(strExpr)) {
                return "T";
            } else if ("NIL".equals(strExpr)) {
                return "NIL";
            } else if (strExpr.startsWith("\"") && strExpr.endsWith("\"")) {
                return strExpr.substring(1, strExpr.length() - 1);
            }
            Object value = currentEnv.getVariable(strExpr);
            if (value.equals("UNDEFINED")) {
                throw new IllegalArgumentException("¡Advertencia! Tu Variable no ha sido definida: " + expr);
            }
            return value;
        } else if (expr instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<Object> exprList = (List<Object>) expr;
            if (exprList.isEmpty()) {
                return new ArrayList<>();
            }
            Object firstElement = exprList.get(0);
            if (!(firstElement instanceof String)) {
                throw new IllegalArgumentException("¡Espera! El operador es inválido: " + expr);
            }
            String op = (String) firstElement;
            List<Object> args = new ArrayList<>(exprList.subList(1, exprList.size()));
            return apply(op, args, currentEnv);
        }
        return null;
    }

    private Object apply(String op, List<Object> args, Enviroment currentEnv) {
        switch (op) {
            case "QUOTE": return args.get(0);
            case "SETQ":
                String varName = (String) args.get(0);
                Object evaluatedValue = evaluate(args.get(1), currentEnv);
                currentEnv.setVariable(varName, evaluatedValue);
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
                    evaluatedArgs.add(evaluate(arg, currentEnv));
                }
                return Arithmetics.apply(op, evaluatedArgs);
            case "=":
                if (args.size() != 2) {
                    throw new IllegalArgumentException("= requiere exactamente 2 argumentos.");
                }
                Object arg1 = evaluate(args.get(0), currentEnv);
                Object arg2 = evaluate(args.get(1), currentEnv);
                if (!(arg1 instanceof Integer) || !(arg2 instanceof Integer)) {
                    throw new IllegalArgumentException("= requiere argumentos numéricos.");
                }
                return ((Integer) arg1).equals((Integer) arg2) ? "T" : "NIL";
            case "EQUAL":
            case "ATOM":
            case "LIST":
                return Predicates.apply(op, args);
            case ">":
            case "<":
            case ">=":
            case "<=":
                List<Object> numericArgs = new ArrayList<>();
                for (Object arg : args) {
                    Object evaluatedArg = evaluate(arg, currentEnv);
                    if (!(evaluatedArg instanceof Number)) {
                        throw new IllegalArgumentException("Los argumentos para " + op + " deben ser números.");
                    }
                    numericArgs.add(evaluatedArg);
                }
                return Predicates.apply(op, numericArgs);
            case "COND":
                return evalCond(args, currentEnv);
            case "PRINT":
                Object valueToPrint = evaluate(args.get(0), currentEnv);
                System.out.println(valueToPrint);
                return valueToPrint;
            default:
                if (currentEnv.hasFunction(op)) {
                    return applyFunction(currentEnv.getFunction(op), args, currentEnv);
                } else if (env.hasFunction(op)) {
                    return applyFunction(env.getFunction(op), args, currentEnv);
                }
                throw new IllegalArgumentException("¡Que curioso! Tu operador esta desconocido: " + op);
        }
    }

    private Object evalCond(List<Object> conditions, Enviroment currentEnv) {
        for (Object condition : conditions) {
            if (!(condition instanceof List<?>)) {
                throw new IllegalArgumentException("COND espera una lista de condiciones.");
            }
            @SuppressWarnings("unchecked")
            List<Object> condPair = (List<Object>) condition;
            if (condPair.size() != 2) {
                throw new IllegalArgumentException("Cada condición en COND debe tener exactamente dos elementos.");
            }
            Object test = evaluate(condPair.get(0), currentEnv);
            if (isTrue(test)) {
                return evaluate(condPair.get(1), currentEnv);
            }
        }
        return null;
    }

    private boolean isTrue(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Integer) {
            return ((Integer) value) != 0;
        } else if (value instanceof String) {
            return "T".equals(value);
        } else if (value instanceof List<?>) {
            return !((List<?>) value).isEmpty();
        }
        return value != null;
    }

    private Object applyFunction(LispFunction func, List<Object> args, Enviroment parentEnv) {
        Enviroment localEnv = new Enviroment();
        List<String> parameters = func.getParams();
        List<Object> body = func.getBody();

        if (parameters.size() != args.size()) {
            throw new IllegalArgumentException(
                    "He identificado que el número es incorrecto en cuanto a los argumentos para esta función.");
        }

        for (int i = 0; i < parameters.size(); i++) {
            Object evaluatedArg = evaluate(args.get(i), parentEnv);
            localEnv.setVariable(parameters.get(i), evaluatedArg);
        }

        Object result = null;
        for (Object expr : body) {
            result = evaluate(expr, localEnv);
        }
        return result;
    }
}