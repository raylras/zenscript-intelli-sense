package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * 1 .. 2
 * 1 to 2
 */
public class IntRangeExpressionNode extends ASTNode implements ExpressionNode {

    private ExpressionNode from;
    private ExpressionNode to;

    public IntRangeExpressionNode() {
    }

    public ExpressionNode getFrom() {
        return from;
    }

    public ExpressionNode getTo() {
        return to;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof ExpressionNode) {
            if (from == null) {
                from = (ExpressionNode) node;
            } else if (to == null) {
                to = (ExpressionNode) node;
            }
        }
    }

    @Override
    public String toString() {
        return from + " .. " + to;
    }

}
