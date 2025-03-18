import java.util.*;

public class Predicates{
    public static Object apply(String op, List<Object> args) {
        switch (op) {
            case "EQUAL":
            return EQUAL(args.get(0), args.get(1));
            case "ATOM":
            return ATOM(args.get(0));
            case "LIST":
            return LIST(args.get(0));
            case "<":
            return LESS_THAN(args.get(0), args.get(1));
            case ">":
            return GREATER_THAN(args.get(0), args.get(1));

        }

        }
}
