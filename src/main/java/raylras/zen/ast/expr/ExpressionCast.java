package raylras.zen.ast.expr;

import raylras.zen.ast.TypeNode;

public class ExpressionCast extends Expression {

    private Expression expr;
    private TypeNode typeNode;

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    public TypeNode getTypeNode() {
        return typeNode;
    }

    public void setTypeNode(TypeNode typeNode) {
        this.typeNode = typeNode;
    }

}
