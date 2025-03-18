/**
 * The `Evaluator` class in Java is designed to evaluate Lisp-like expressions by handling basic
 * arithmetic operations, variable assignments, function definitions, and function calls.
 */
import java.util.*;
import java.util.Objects;

public class Evaluator {
    private final SetQ variables;
    private final Defun functions;
    
    public Evaluator() {
        this.variables = new SetQ();
        this.functions = new Defun();
    }
    
    
    /** 
     * @param expr
     * @return Object
     */
    public Object evaluate(Object expr) {
        if (expr instanceof Integer) {
            return expr;
        } else if (expr instanceof String) {
            return variables.getVariavles((String) expr);
        } else if (expr instanceof List) {
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
    
    private Object apply(String op, List<Object> args) {
        switch (op) {
            case "QUOTE":
                return args.get(0);
            case "SETQ":
                variables.setVariables((String) args.get(0), evaluate(args.get(1)));
                return null;
            case "DEFUN":
                functions.defineFunction((String) args.get(0), (List<String>) args.get(1), args.subList(2, args.size()));
                return null;
            case "+": case "-": case "*": case "/":
                return Arithmetics.apply(op, args);
            case "EQUAL": case "ATOM": case "LIST": case "<": case ">":
                return Predicates.apply(op, args);
            default:
                LispFunction func = functions.getFunction(op);
                if (func != null) {
                    return applyFunction(func, args);
                }
                return null;
        }
    }
    
    private Object applyFunction(LispFunction func, List<Object> args) {
        Map<String, Object> localEnv = new HashMap<>();
        for (int i = 0; i < func.params.size(); i++) {
            localEnv.put(func.params.get(i), evaluate(args.get(i)));
        }
        Evaluator localEvaluator = new Evaluator();
        localEvaluator.variables.variables.putAll(localEnv);
        Object result = null;
        for (Object expr : func.body) {
            result = localEvaluator.evaluate(expr);
        }
        return result;
    }
}
