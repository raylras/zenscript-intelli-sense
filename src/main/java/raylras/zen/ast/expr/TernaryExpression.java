package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class TernaryExpression extends Expression {

    private final Expression condition;
    private final Expression thenExpr;
    private final Expression elseExpr;

    public TernaryExpression(Expression condition, Expression thenExpr, Expression elseExpr) {
        this.condition = condition;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getThenExpr() {
        return thenExpr;
    }

    public Expression getElseExpr() {
        return elseExpr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitTernaryExpression(this);
        condition.accept(visitor);
        thenExpr.accept(visitor);
        elseExpr.accept(visitor);
    }

}
