package src.parserclasses;

public class VarValue {
    private String type;
    private Object value;
    public VarValue(String type) {
        this.type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    public Object getValue() {
        return value;
    }
    public String getType() {
        return type;
    }
}
