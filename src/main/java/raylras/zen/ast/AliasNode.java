package raylras.zen.ast;

public class AliasNode extends ASTNode {

    private final IdentifierNode idNode;

    public AliasNode(IdentifierNode idNode) {
        this.idNode = idNode;
    }

    public IdentifierNode getIdNode() {
        return idNode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitAlias(this);
    }

    @Override
    public String toString() {
        return idNode.toString();
    }

}
