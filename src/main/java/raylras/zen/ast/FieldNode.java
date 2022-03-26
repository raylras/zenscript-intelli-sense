package raylras.zen.ast;

import raylras.zen.ast.type.Type;

public class FieldNode extends ASTNode {

    private IDNode nameNode;
    private TypeNode typeNode;

    private Type type;
    private boolean isFinal;

    public IDNode getNameNode() {
        return nameNode;
    }

    public void setNameNode(IDNode nameNode) {
        this.nameNode = nameNode;
    }

    public TypeNode getAsTypeNode() {
        return typeNode;
    }

    public void setTypeNode(TypeNode typeNode) {
        this.typeNode = typeNode;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(nameNode.getName());
        if (type != null) {
            builder.append(" as ").append(type.getTypeName());
            return builder.toString();
        }
        if (typeNode != null) {
            builder.append(" as ").append(typeNode.getTypeName());
            return builder.toString();
        }
        return builder.toString();
    }

}
