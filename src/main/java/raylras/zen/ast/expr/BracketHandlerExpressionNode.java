package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * &lt;item:dirt&gt;
 */
public class BracketHandlerExpressionNode extends ASTNode implements ExpressionNode {

    private final String literal;

    public BracketHandlerExpressionNode(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
    }

    @Override
    public String toString() {
        return literal;
    }

}
