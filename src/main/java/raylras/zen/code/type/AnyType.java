package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;

public class AnyType extends Type {

    public static final AnyType INSTANCE = new AnyType();

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        return true;
    }

    @Override
    public String toString() {
        return "any";
    }

}
