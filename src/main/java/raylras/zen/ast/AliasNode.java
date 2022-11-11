package raylras.zen.ast;

public class AliasNode extends ASTNode {

    private IdentifierNode identifier;

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
        if (node.getClass() == IdentifierNode.class) {
            identifier = (IdentifierNode) node;
        }
    }

    @Override
    public String toString() {
        return identifier.toString();
    }

}
