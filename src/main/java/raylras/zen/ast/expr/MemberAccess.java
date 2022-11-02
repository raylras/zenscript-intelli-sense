package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * A.b
 * a.b.c()
 */
public class MemberAccess extends ASTNode implements ExpressionNode {

    private final ExpressionNode left;
    private final ExpressionNode right;

    public MemberAccess(ExpressionNode left, ExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return left + "." + right;
    }

}
