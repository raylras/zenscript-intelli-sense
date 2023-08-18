package raylras.zen.code.type;

public class AnyType extends Type {

    public static final AnyType INSTANCE = new AnyType();

    @Override
    public SubtypeResult isSubtypeOf(Type type) {
        return SubtypeResult.INHERIT;
    }

    @Override
    public String toString() {
        return "any";
    }

}
