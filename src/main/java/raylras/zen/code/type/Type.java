package raylras.zen.code.type;

public abstract class Type {

    public boolean isAssignableTo(Type type) {
        return isSubtypeOf(type).matched();
    }

    public SubtypeResult isSubtypeOf(Type type) {
        if (this.equals(type)) {
            return SubtypeResult.SELF;
        }
        if (type.equals(AnyType.INSTANCE)) {
            return SubtypeResult.INHERIT;
        }
        return SubtypeResult.MISMATCH;
    }

    public abstract String toString();

}
