package raylras.zen.code.type;

public class ListType extends Type {

    public Type elementType;

    public ListType(Type elementType) {
        this.elementType = elementType;
    }

    @Override
    public Tag getTag() {
        return Tag.LIST;
    }

    @Override
    public String toString() {
        return "[" + elementType + "]";
    }

}
