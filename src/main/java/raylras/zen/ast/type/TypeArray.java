package raylras.zen.ast.type;

public class TypeArray extends Type {

    private final Type baseType;
    private final String typeName;

    public TypeArray(Type baseType) {
        this.baseType = baseType;
        this.typeName = baseType.getTypeName() + "[]";
    }

    public Type getBaseType() {
        return baseType;
    }

    @Override
    public String toString() {
        return typeName;
    }

}
