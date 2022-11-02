package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * expr ? thenExpr : elseExpr
 */
public class TernaryExpressionNode extends ASTNode implements ExpressionNode {

    private final ExpressionNode expr;
    private final ExpressionNode thenExpr;
    private final ExpressionNode elseExpr;

    public TernaryExpressionNode(ExpressionNode expr,
                                 ExpressionNode thenExpr,
                                 ExpressionNode elseExpr) {
        this.expr = expr;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
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
    public String toString() {
        return expr + " ? " + thenExpr + " : " + elseExpr;
    }

}
