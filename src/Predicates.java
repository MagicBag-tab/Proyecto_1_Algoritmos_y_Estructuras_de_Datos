import java.util.List;

public class Predicates {
    public static Object apply(String op, List<Object> args) {
        if (args.size() < 2) {
            throw new IllegalArgumentException("Número insuficiente de argumentos para " + op);
        }

        return switch (op) {
            case "EQUAL" -> EQUAL(args.get(0), args.get(1));
            case "ATOM" -> ATOM(args.get(0));
            case "LIST" -> LIST(args.get(0));
            case "<" -> LESS_THAN(args.get(0), args.get(1));
            case ">" -> GREATER_THAN(args.get(0), args.get(1));
            default -> throw new IllegalArgumentException("Operador desconocido: " + op);
        };
    }

    private static boolean EQUAL(Object a, Object b) {
        return a.equals(b);
    }

    private static boolean ATOM(Object obj) {
        return !(obj instanceof List);
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
}
