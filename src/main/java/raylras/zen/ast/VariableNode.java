package raylras.zen.ast;

import org.antlr.v4.runtime.Token;
import raylras.zen.ast.type.Type;

public class VariableNode extends ASTNode {

    private IDNode nameNode;
    private TypeNode typeNode;
    private Type type;
    private boolean isFinal;

    public VariableNode(Token token) {
        this.nameNode = new IDNode(token);
        this.setSourcePosition(token);
    }

    public IDNode getNameNode() {
        return nameNode;
    }

    public void setNameNode(IDNode nameNode) {
        this.nameNode = nameNode;
    }

    public TypeNode getAsTypeNode() {
        return typeNode;
    }

    public void setAsTypeNode(TypeNode typeNode) {
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

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
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
        }
        return builder.toString();
    }

}
