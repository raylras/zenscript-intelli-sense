package raylras.zen.ast;

import raylras.zen.ast.expr.ExpressionNode;

public class IdentifierNode extends ASTNode implements ExpressionNode {

    private final String id;

    public IdentifierNode(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return null;
    }

}
