package raylras.zen.ast;

public class ImportNode extends ASTNode {

    private ReferenceNode referenceNode;
    private AliasNode aliasNode;

    public ReferenceNode getClassNameNode() {
        return referenceNode;
    }

    public void setClassNameNode(ReferenceNode referenceNode) {
        this.referenceNode = referenceNode;
    }

    public AliasNode getAliasNode() {
        return aliasNode;
    }

    public void setAliasNode(AliasNode aliasNode) {
        this.aliasNode = aliasNode;
    }

    @Override
    public void accept(ASTVisitor<?> visitor) {
        visitor.visitReference(referenceNode);
        visitor.visitAlias(aliasNode);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(referenceNode.getReference());
        if (aliasNode != null) {
            builder.append(" as ");
            builder.append(aliasNode.getAlias());
        }
        return builder.toString();
    }

}
