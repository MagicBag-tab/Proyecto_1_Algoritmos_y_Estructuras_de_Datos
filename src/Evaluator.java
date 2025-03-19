/**
 * The `Evaluator` class in Java provides functionality to evaluate Lisp expressions by handling basic
 * arithmetic operations, variable assignments, function definitions, and function calls.
 */

import java.util.*;

class LispFunction {
    List<String> params;
    List<Object> body;

    public LispFunction(List<String> params, List<Object> body) {
        this.params = params;
        this.body = body;
    }
}

public class Evaluator {
    private final Enviroment env;  

    public Evaluator() {
        this.env = new Enviroment();
    }

    public Object evaluate(Object expr) {
        if (expr instanceof Integer) { 
            return expr;
        } else if (expr instanceof String) {
            return env.getVariable((String) expr);
        } else if (expr instanceof List<?>){ 
            List<Object> exprList = (List<Object>) expr;
            if (exprList.isEmpty()) {
                return null;
            }
            String op = (String) exprList.get(0);
            List<Object> args = exprList.subList(1, exprList.size());
            return apply(op, args);
        }
        return null;
    }

    private Object apply(String op, List<Object> args){
        switch (op) {
            case "QUOTE":
                return args.get(0);
            case "SETQ": 
                env.setVariable((String) args.get(0), evaluate(args.get(1)));
                return null;
            case "DEFUN": 
                if (args.get(1) instanceof List<?>) { 
                    List<?> rawList = (List<?>) args.get(1);
                    List<String> paramList = new ArrayList<>();

                    for (Object item : rawList){ 
                        if (item instanceof String){ 
                            paramList.add((String) item);
                        } else { 
                            throw new IllegalArgumentException("DEFUN necesita que los parámetros sean strings.");
                        }
                    }

                    env.defineFunction((String) args.get(0), new LispFunction(paramList, args.subList(2, args.size())));
                } else { 
                    throw new IllegalArgumentException("DEFUN espera una lista parámetros.");
                }
                return null;
            case "+": case "-": case "*": case "/":
                return Arithmetics.apply(op, args);
            case "EQUAL": case "ATOM": case "LIST": case "<": case ">":
                return Predicates.apply(op, args);
            default:
                if (env.hasFunction(op)) {
                    return applyFunction(env.getFunction(op), args);
                }
                return null;
        }
    }

    private Object applyFunction(LispFunction func, List<Object> args){
        Enviroment localEnv = new Enviroment();
        for (int i = 0; i < func.params.size(); i++) {
            localEnv.setVariable(func.params.get(i), evaluate(args.get(i)));
        }
        Object result = null;
        for (Object expr : func.body) {
            result = evaluate(expr);
        }
        return result;
    }
}