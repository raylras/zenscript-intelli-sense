package raylras.zen.semantic.type;

import java.util.Objects;

public class ListType implements Type {

    private Type elementType;

    public ListType(Type elementType) {
        Objects.requireNonNull(elementType);
        this.elementType = elementType;
    }

    public Type getElementType() {
        return elementType;
    }

    public void setElementType(Type elementType) {
        Objects.requireNonNull(elementType);
        this.elementType = elementType;
    }

    @Override
    public String typeName() {
        return "[" + elementType.typeName() + "]";
    }

    @Override
    public boolean isType(Type type) {
        if (type instanceof ListType) {
            ListType that = (ListType) type;
            return this.elementType.isType(that.elementType);
        }
        return false;
    }

    @Override
    public String toString() {
        return "(type (list " + elementType.typeName() + "))";
    }

}
