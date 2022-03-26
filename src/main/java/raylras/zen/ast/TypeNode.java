package raylras.zen.ast;

import raylras.zen.ast.type.Type;

public class TypeNode extends ASTNode {

    private String typeName;
    private Type type;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type == null ? typeName : type.getTypeName();
    }

}
