package raylras.zen.code.type;

public class ArrayType extends Type {

    public Type elementType;

    public ArrayType(Type elementType) {
        this.elementType = elementType;
    }

    @Override
    public Kind getKind() {
        return Kind.ARRAY;
    }

    @Override
    public String toString() {
        return elementType + "[]";
    }

}
