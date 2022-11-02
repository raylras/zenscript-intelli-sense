package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

public class StringLiteralNode extends ASTNode implements LiteralExpressionNode {

    private final String literal;

    public StringLiteralNode(String literal) {
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
    public String toString() {
        return literal;
    }

}
