package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * (a + b)
 */
public class ParensExpressionNode extends ASTNode implements ExpressionNode {

    private final ExpressionNode expr;

    public ParensExpressionNode(ExpressionNode expr) {
        this.expr = expr;
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "(" + expr + ")";
    }

}
