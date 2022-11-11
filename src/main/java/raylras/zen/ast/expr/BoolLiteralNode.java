package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

public class BoolLiteralNode extends ASTNode implements LiteralExpressionNode {

    private final String literal;

    public BoolLiteralNode(String literal) {
        this.literal = literal;
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
