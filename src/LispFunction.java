import java.util.*;

public class LispFunction {
    private final List<String> params;
    private final List<Object> body;

    public LispFunction(List<String> params, List<Object> body) {
        this.params = new ArrayList<>(params);
        this.body = new ArrayList<>(body); 
    }

    public List<String> getParams() {
        return params;
    }

    public List<Object> getBody() {
        return body;
    }
}
