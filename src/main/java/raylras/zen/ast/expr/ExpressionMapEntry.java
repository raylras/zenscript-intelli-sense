package raylras.zen.ast.expr;

public class ExpressionMapEntry extends Expression {

    private Expression key;
    private Expression value;

    public Expression getKey() {
        return key;
    }

    public void setKey(Expression key) {
        this.key = key;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

}
