package raylras.zen.verify.type;

public class ArrayType extends AbstractType {

    private final Type baseType;

    public ArrayType(Type baseType) {
        this.baseType = baseType;
    }

    public Type getBaseType() {
        return baseType;
    }

    @Override
    public String toString() {
        return baseType.toString() + "[]";
    }

}
