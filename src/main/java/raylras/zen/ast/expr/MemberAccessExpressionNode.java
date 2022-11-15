package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;

/**
 * A.b
 * a.b.c()
 */
public class MemberAccessExpressionNode extends ASTNode implements Expression {

    private Expression left;
    private Expression right;

    public MemberAccessExpressionNode() {
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Expression) {
            if (left == null) {
                left = (Expression) node;
            } else if (right == null) {
                right = (Expression) node;
            }
        }
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
