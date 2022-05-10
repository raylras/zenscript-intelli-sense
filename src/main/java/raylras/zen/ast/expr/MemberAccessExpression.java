package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class MemberAccessExpression extends Expression {

    private final Expression left;
    private final Expression right;

    public MemberAccessExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitMemberAccessExpression(this);
        left.accept(visitor);
        right.accept(visitor);
    }

}
