package raylras.zen.ast.expr;

import raylras.zen.ast.VariableNode;
import raylras.zen.ast.type.Type;

public class ExpressionVariable extends Expression {

    private VariableNode variable; // where is it defined

    public VariableNode getVariable() {
        return variable;
    }

    public void setVariable(VariableNode variable) {
        this.variable = variable;
    }

    @Override
    public Type getType() {
        return variable.getType();
    }

    @Override
    public void setType(Type type) {
        variable.setType(type);
    }

}
