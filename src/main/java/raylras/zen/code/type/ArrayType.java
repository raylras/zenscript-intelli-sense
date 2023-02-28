package raylras.zen.code.type;

public class ArrayType extends Type {

    public Type elementType;

    public ArrayType(Type elementType) {
        this.elementType = elementType;
    }

    @Override
    public Tag getTag() {
        return Tag.ARRAY;
    }

    @Override
    public String toString() {
        return elementType + "[]";
    }

}
