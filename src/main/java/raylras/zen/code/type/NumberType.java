package raylras.zen.code.type;

public abstract class NumberType extends Type {

    @Override
    public SubtypeResult isSubtypeOf(Type type) {
        if (type instanceof NumberType) {
            return SubtypeResult.INHERIT;
        }
        return super.isSubtypeOf(type);
    }

}
