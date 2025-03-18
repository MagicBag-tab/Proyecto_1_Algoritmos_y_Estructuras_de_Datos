import java.util.*;

class Function {
    private final List<String> params;
    private final List<Object> body;

    public Function(List<String> params, List<Object> body) {
        this.params = params;
        this.body = body;
    }

    public List<String> getParams() {
        return params;
    }

    public List<Object> getBody() {
        return body;
    }
}