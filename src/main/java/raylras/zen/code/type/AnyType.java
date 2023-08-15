package raylras.zen.code.type;

public class AnyType extends Type implements IDataCastable {

    public static final AnyType INSTANCE = new AnyType();

    @Override
    public String toString() {
        return "any";
    }

    @Override
    public SubtypeResult isSubtypeOf(Type type) {
        return SubtypeResult.INHERIT;
    }

}
