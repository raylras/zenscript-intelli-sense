package raylras.zen.ast.stmt;

import raylras.zen.ast.VariableNode;
import raylras.zen.ast.expr.Expression;

public class StatementVar extends Statement {

    private VariableNode variableNode;
    private Expression expression;

    public VariableNode getVariableNode() {
        return variableNode;
    }

    public void setVariableNode(VariableNode variableNode) {
        this.variableNode = variableNode;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

}
