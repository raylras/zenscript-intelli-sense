package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * 1 .. 2
 * 1 to 2
 */
public class IntRangeExpressionNode extends ASTNode implements ExpressionNode {

    private final ExpressionNode from;
    private final ExpressionNode to;

    public IntRangeExpressionNode(ExpressionNode from, ExpressionNode to) {
        this.from = from;
        this.to = to;
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
    public String toString() {
        return from + " .. " + to;
    }

}
