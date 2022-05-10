package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;
import raylras.zen.ast.TypeNode;

public class TypeCastExpression extends Expression {

    private final Expression expr;
    private final TypeNode typeNode;

    public TypeCastExpression(Expression expr, TypeNode typeNode) {
        this.expr = expr;
        this.typeNode = typeNode;
    }

    public Expression getExpr() {
        return expr;
    }

    public TypeNode getTypeNode() {
        return typeNode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitTypeCastExpression(this);
        expr.accept(visitor);
        typeNode.accept(visitor);
    }

}
