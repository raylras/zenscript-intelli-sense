package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class ParensExpression extends Expression {

    private final Expression expr;

    public ParensExpression(Expression expr) {
        this.expr = expr;
    }

    public Expression getExpr() {
        return expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitParensExpression(this);
        expr.accept(visitor);
    }

}
