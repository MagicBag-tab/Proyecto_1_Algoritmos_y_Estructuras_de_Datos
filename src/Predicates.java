import java.util.List;

public class Predicates {
    public static Object apply(String op, List<Object> args) {
        if (args.size() < 1) {
            throw new IllegalArgumentException("Número insuficiente de argumentos para " + op);
        }

        switch (op) {
            case "EQUAL":
                return toLispBoolean(EQUAL(args.get(0), args.get(1)));
            case "ATOM":
                return ATOM(args.get(0)); // ATOM ya devuelve T/NIL
            case "LIST":
                return toLispBoolean(LIST(args.get(0)));
            case "<":
                return toLispBoolean(LESS_THAN(args.get(0), args.get(1)));
            case ">":
                return toLispBoolean(GREATER_THAN(args.get(0), args.get(1)));
            default:
                throw new IllegalArgumentException("Operador desconocido: " + op);
        }
    }

    private static boolean EQUAL(Object a, Object b) {
        return a.equals(b);
    }

    private static String ATOM(Object obj) {
        if (obj == null) { return "T"; }
        if (obj instanceof List && ((List<?>) obj).isEmpty()) { return "NIL"; }
        if (obj instanceof List) { return "NIL"; }
        return "T";
    }

    private static boolean LIST(Object obj) {
        return obj instanceof List;
    }

    private static boolean LESS_THAN(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) {
            return (Integer) a < (Integer) b;
        }
        throw new IllegalArgumentException("Los argumentos deben ser enteros para '<'");
    }

    private static boolean GREATER_THAN(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) {
            return (Integer) a > (Integer) b;
        }
        throw new IllegalArgumentException("Los argumentos deben ser enteros para '>'");
    }

    // Convierte booleanos a valores Lisp (T/NIL)
    private static String toLispBoolean(boolean value) {
        return value ? "T" : "NIL";
    }
}
