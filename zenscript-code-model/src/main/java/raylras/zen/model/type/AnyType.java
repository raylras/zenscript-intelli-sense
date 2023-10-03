package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;

public final class AnyType implements Type {

    public static final AnyType INSTANCE = new AnyType();

    @Override
    public String getTypeName() {
        return "any";
    }

    @Override
    public boolean isSuperclassTo(Type type) {
        return true;
    }

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        return true;
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
