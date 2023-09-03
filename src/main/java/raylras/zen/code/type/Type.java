package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.util.Operators;

public abstract class Type {

    public final boolean isAssignableTo(Type type, CompilationEnvironment env) {
        return isSubtypeOf(type, env).matched();
    }

    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        return Operators.hasCaster(this, type, env);
    }

    public boolean isInheritedFrom(Type type) {
        return type == AnyType.INSTANCE;
    }

    public final SubtypeResult isSubtypeOf(Type type, CompilationEnvironment env) {
        if (this.equals(type)) {
            return SubtypeResult.SELF;
        }
        if (isInheritedFrom(type)) {
            return SubtypeResult.INHERIT;
        }
        if (isCastableTo(type, env)) {
            return SubtypeResult.CASTER;
        }
        return SubtypeResult.MISMATCH;
    }

    @Override
    public abstract String toString();

}
