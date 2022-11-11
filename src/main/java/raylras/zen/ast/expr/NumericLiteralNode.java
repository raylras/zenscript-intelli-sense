package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * 100
 * 1.0f
 * 0xABCD
 */
public class NumericLiteralNode extends ASTNode implements LiteralExpressionNode {

    private final String literal;

    public NumericLiteralNode(String literal) {
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
