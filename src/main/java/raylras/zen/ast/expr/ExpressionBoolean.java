package raylras.zen.ast.expr;

import raylras.zen.ast.type.TypeBool;

public class ExpressionBoolean extends Expression {

    private boolean value;

    public ExpressionBoolean(boolean value) {
        this.value = value;
        this.setType(TypeBool.INSTANCE);
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

}
