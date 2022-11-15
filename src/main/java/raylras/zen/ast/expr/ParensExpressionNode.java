package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;

/**
 * (a + b)
 */
public class ParensExpressionNode extends ASTNode implements Expression {

    private Expression expr;

    public ParensExpressionNode() {
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Expression) {
            expr = (Expression) node;
        }
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
