package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.util.Operators;

public abstract class Type {

    public final boolean isAssignableTo(Type type, CompilationEnvironment env) {
        return this.testSubtypeOf(type, env).matched();
    }

    public final SubtypeResult testSubtypeOf(Type type, CompilationEnvironment env) {
        if (this.equals(type)) {
            return SubtypeResult.SELF;
        }
        if (this.isInheritedFrom(type)) {
            return SubtypeResult.INHERIT;
        }
        if (this.isCastableTo(type, env)) {
            return SubtypeResult.CASTER;
        }
        return SubtypeResult.MISMATCH;
    }

    public boolean isInheritedFrom(Type type) {
        return type == AnyType.INSTANCE || this.equals(type);
    }

    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        return Operators.hasCaster(this, type, env) || this.isInheritedFrom(type);
    }

    @Override
    public abstract String toString();

}
