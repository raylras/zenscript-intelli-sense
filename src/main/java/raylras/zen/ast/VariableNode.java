package raylras.zen.ast;

import org.jetbrains.annotations.Nullable;
import raylras.zen.verify.type.Type;

public class VariableNode extends ASTNode {

    private final IdentifierNode idNode;
    @Nullable
    private final TypeNode typeNode;

    private Type type;
    private boolean isFinal;
    private boolean isStatic;

    private boolean isGlobal;

    public VariableNode(IdentifierNode idNode, @Nullable TypeNode typeNode) {
        this.idNode = idNode;
        this.typeNode = typeNode;
    }

    public IdentifierNode getIdNode() {
        return idNode;
    }

    @Nullable
    public TypeNode getTypeNode() {
        return typeNode;
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

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitVariable(this);
        idNode.accept(visitor);
        if (typeNode != null) {
            typeNode.accept(visitor);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(idNode);
        if (type != null) {
            builder.append(" as ").append(type);
        }
        return builder.toString();
    }

}
