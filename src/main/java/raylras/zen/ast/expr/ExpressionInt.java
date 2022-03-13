package raylras.zen.ast.expr;

import raylras.zen.ast.type.TypeInt;

public class ExpressionInt extends Expression {

    private int value;

    public ExpressionInt(int value) {
        this.value = value;
        this.setType(TypeInt.INSTANCE);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
