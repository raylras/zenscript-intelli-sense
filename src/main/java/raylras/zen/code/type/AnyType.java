package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;

public class AnyType extends Type {

    public static final AnyType INSTANCE = new AnyType();

    @Override
    public SubtypeResult isSubtypeOf(Type type, CompilationEnvironment env) {
        return SubtypeResult.INHERIT;
    }

    @Override
    public String toString() {
        return "any";
    }

}
