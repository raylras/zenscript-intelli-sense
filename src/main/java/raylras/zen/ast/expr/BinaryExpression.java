package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class BinaryExpression extends Expression {

    private final Expression left;
    private final Expression right;
    private final Operator.Binary operator;

    public BinaryExpression(Expression left, Expression right, Operator.Binary operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public Operator.Binary getOperator() {
        return operator;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitBinaryExpression(this);
        left.accept(visitor);
        right.accept(visitor);
    }

}
