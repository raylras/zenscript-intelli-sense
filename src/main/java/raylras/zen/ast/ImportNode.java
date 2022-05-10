package raylras.zen.ast;

import org.jetbrains.annotations.Nullable;

public class ImportNode extends ASTNode {

    private final ReferenceNode referenceNode;
    @Nullable
    private final AliasNode aliasNode;

    public ImportNode(ReferenceNode referenceNode, @Nullable AliasNode aliasNode) {
        this.referenceNode = referenceNode;
        this.aliasNode = aliasNode;
    }

    public ReferenceNode getReferenceNode() {
        return referenceNode;
    }

    @Nullable
    public AliasNode getAliasNode() {
        return aliasNode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitImport(this);
        if (referenceNode != null) {
            referenceNode.accept(visitor);
        }
        if (aliasNode != null) {
            aliasNode.accept(visitor);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(referenceNode.getReference());
        if (aliasNode != null) {
            builder.append(" as ").append(aliasNode.getIdNode());
        }
        return builder.toString();
    }

}
