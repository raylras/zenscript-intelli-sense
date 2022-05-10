package raylras.zen.ast;

import raylras.zen.verify.type.Type;

public class TypeNode extends ASTNode {

    private final Type type;

    public TypeNode(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitType(this);
    }

    @Override
    public String toString() {
        return type == null ? null : type.toString();
    }

}
