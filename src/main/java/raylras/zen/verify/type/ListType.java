package raylras.zen.verify.type;

public class ListType extends AbstractType {

    private final Type baseType;

    public ListType(Type baseType) {
        this.baseType = baseType;
    }

    public Type getBaseType() {
        return baseType;
    }

    @Override
    public String toString() {
        return "[" + baseType.toString() + "]";
    }

}
