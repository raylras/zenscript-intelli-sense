package raylras.zen.ast;

public class TypeAnnotationNode extends ASTNode {

    private TypeNameNode typeName;

    public TypeAnnotationNode() {
    }

    public TypeNameNode getTypeName() {
        return typeName;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
        if (node.getClass() == TypeNameNode.class) {
            if (typeName == null) {
                typeName = (TypeNameNode) node;
            }
        }
    }

    @Override
    public String toString() {
        return typeName.toString();
    }

}
