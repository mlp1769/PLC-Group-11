package src.parserclasses;

public class VarValue {
    private final String type;
    private Object value;
    public VarValue(String type) {
        this.type = type;
        this.value = null;
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
