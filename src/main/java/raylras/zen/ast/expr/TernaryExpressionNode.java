package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * expr ? thenExpr : elseExpr
 */
public class TernaryExpressionNode extends ASTNode implements ExpressionNode {

    private ExpressionNode expr;
    private ExpressionNode thenExpr;
    private ExpressionNode elseExpr;

    public TernaryExpressionNode() {
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    public ExpressionNode getThenExpr() {
        return thenExpr;
    }

    public ExpressionNode getElseExpr() {
        return elseExpr;
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
            } else if (thenExpr == null) {
                thenExpr = (ExpressionNode) node;
            } else if (elseExpr == null) {
                elseExpr = (ExpressionNode) node;
            }
        }
    }

    @Override
    public String toString() {
        return expr + " ? " + thenExpr + " : " + elseExpr;
    }

}
