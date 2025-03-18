import java.util.*;

public class Defun {
    private static final Map<String, LispFunction> functions = new HashMap<>();

    public void defineFunction(String name, List<String> params, List<Object> body) {
        functions.put(name, new LispFunction(params, body));
    }

    public LispFunction getFunction(String name) {
        return functions.get(name);
    }
}