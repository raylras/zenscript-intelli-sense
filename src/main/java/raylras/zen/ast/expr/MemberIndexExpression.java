package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class MemberIndexExpression extends Expression {

    private final Expression left;
    private final Expression index;

    public MemberIndexExpression(Expression left, Expression index) {
        this.left = left;
        this.index = index;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getIndex() {
        return index;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitMemberIndexExpression(this);
        left.accept(visitor);
        index.accept(visitor);
    }

}
