package raylras.zen.ast.stmt;

import raylras.zen.ast.VariableNode;
import raylras.zen.ast.expr.Expression;

public class StatementFor extends Statement {

    private VariableNode firstVariableNode;
    private VariableNode secondVariableNode;
    private Expression expr;

    public VariableNode getFirstVariableNode() {
        return firstVariableNode;
    }

    public void setFirstVariableNode(VariableNode firstVariableNode) {
        this.firstVariableNode = firstVariableNode;
    }

    public VariableNode getSecondVariableNode() {
        return secondVariableNode;
    }

    public void setSecondVariableNode(VariableNode secondVariableNode) {
        this.secondVariableNode = secondVariableNode;
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

}
