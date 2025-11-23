package src.parserclasses;

public class FunctionValue {
    private String type;
    private FBodyNode body;
    public FunctionValue() {}

    public String getType() {
        return type;
    }
    public FBodyNode getBody() {
        return body;
    }
    public void setBody(FBodyNode body) {
        this.body = body;
    }
    public void setType(String type) {
        this.type = type;
    }
}
