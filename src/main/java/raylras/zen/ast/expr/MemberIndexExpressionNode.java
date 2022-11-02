package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * arr[1]
 */
public class MemberIndexExpressionNode extends ASTNode implements ExpressionNode {

    private final ExpressionNode left;
    private final ExpressionNode index;

    public MemberIndexExpressionNode(ExpressionNode left, ExpressionNode index) {
        this.left = left;
        this.index = index;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getIndex() {
        return index;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return left + "[" + index + "]";
    }

}
