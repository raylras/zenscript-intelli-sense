package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * A.b
 * a.b.c()
 */
public class MemberAccessExpressionNode extends ASTNode implements ExpressionNode {

    private ExpressionNode left;
    private ExpressionNode right;

    public MemberAccessExpressionNode() {
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
    public void addChild(ASTNode node) {
        if (node instanceof ExpressionNode) {
            if (left == null) {
                left = (ExpressionNode) node;
            } else if (right == null) {
                right = (ExpressionNode) node;
            }
        }
    }

    @Override
    public String toString() {
        return left + "." + right;
    }

}
