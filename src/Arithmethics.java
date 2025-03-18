import java.util.Objects;

public class Arithmetics{
    public static Object apply(String op, List<Object> args) {
        switch (op) {
            case "+":
            return (int) args.get(0) + (int) args.get(1);
            case "-":
            return (int) args.get(0) - (int) args.get(1);
            case "*":
            return (int) args.get(0) * (int) args.get(1);
            case "/":
            return (int) args.get(0) * (int) args.get(1);
}
    }
}
