package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class AssignmentExpression extends Expression {

    private final Expression left;
    private final Expression right;
    private final Operator.Assignment operator;

    public AssignmentExpression(Expression left, Expression right, Operator.Assignment operator) {
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

    public Operator.Assignment getOperator() {
        return operator;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitAssignmentExpression(this);
        left.accept(visitor);
        right.accept(visitor);
    }

}
