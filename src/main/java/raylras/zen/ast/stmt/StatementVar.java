package raylras.zen.ast.stmt;

import raylras.zen.ast.VariableNode;

public class StatementVar extends Statement {

    private VariableNode variable;

    public VariableNode getVariable() {
        return variable;
    }

    public void setVariable(VariableNode variable) {
        this.variable = variable;
    }

}
