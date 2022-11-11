package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * (a + b)
 */
public class ParensExpressionNode extends ASTNode implements ExpressionNode {

    private ExpressionNode expr;

    public ParensExpressionNode() {
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof ExpressionNode) {
            if (expr == null) {
                expr = (ExpressionNode) node;
            }
        }
    }

    @Override
    public String toString() {
        return "(" + expr + ")";
    }

}
