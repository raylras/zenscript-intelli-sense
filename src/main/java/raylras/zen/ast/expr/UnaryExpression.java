package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class UnaryExpression extends Expression {

    private final Expression expr;
    private final Operator.Unary operator;

    public UnaryExpression(Expression expr, Operator.Unary operator) {
        this.expr = expr;
        this.operator = operator;
    }

    public Expression getExpr() {
        return expr;
    }

    public Operator.Unary getOperator() {
        return operator;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitUnaryExpression(this);
        expr.accept(visitor);
    }

}
