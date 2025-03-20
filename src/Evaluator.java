/**
 * The `Evaluator` class in Java provides functionality to evaluate Lisp expressions by handling basic
 * arithmetic operations, variable assignments, function definitions, and function calls.
 */
import java.util.*;

public class Evaluator {
    private final Enviroment env; 

    public Evaluator() {
        this.env = new Enviroment();
    }

    public Object evaluate(Object expr) {
        if (expr instanceof Integer) { 
            return expr; 
        } else if (expr instanceof String) {
            Object value = env.getVariable((String) expr); 
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
            if (!(firstElement instanceof String)){
                throw new IllegalArgumentException("¡Espera! El operador es inválido: " + expr);
            }

            String op = (String) firstElement;
            List<Object> args = new ArrayList<>(exprList.subList(1, exprList.size())); 
            return apply(op, args);
        }
        return null;
    }

    private Object apply(String op, List<Object> args){
        switch (op) {
            case "QUOTE":
                return args.get(0);
            case "SETQ": 
                String varName = (String) args.get(0);
                Object evaluatedValue = evaluate(args.get(1)); 
                if (evaluatedValue instanceof String) {
                    try {
                        evaluatedValue = Integer.parseInt((String) evaluatedValue);
                    } catch (NumberFormatException e) {
                      
                    }
                }

                env.setVariable(varName, evaluatedValue); 
                return evaluatedValue;
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
    
                    env.defineFunction((String) args.get(0), new LispFunction(paramList, new ArrayList<>(args.subList(2, args.size()))));
                } else { 
                    throw new IllegalArgumentException("DEFUN espera una lista de parámetros.");
                }
                return null;
            case "+": case "-": case "*": case "/":
  
                List<Object> evaluatedArgs = new ArrayList<>();
                for (Object arg : args) {
                    evaluatedArgs.add(evaluate(arg)); 
                }
                return Arithmetics.apply(op, evaluatedArgs);
            case "EQUAL": case "ATOM": case "LIST": case "<": case ">":
                return Predicates.apply(op, args);
            default:
                if (env.hasFunction(op)) {
                    return applyFunction(env.getFunction(op), args);
                }
                throw new IllegalArgumentException("¡Que curioso! Tu operador esta desconocido: " + op);
        }
    }

    /**
     * Aplica una función definida por el usuario.
     */
    private Object applyFunction(LispFunction func, List<Object> args) {
        Enviroment localEnv = new Enviroment();
        
        List<String> parameters = func.getParams(); 
        List<Object> body = func.getBody(); 
        
        if (parameters.size() != args.size()) {
            throw new IllegalArgumentException("He identificado que el número es incorrecto en cuanto a los argumentos para esta función.");
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
