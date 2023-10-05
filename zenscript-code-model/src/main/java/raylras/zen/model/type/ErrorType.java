package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;

public enum ErrorType implements Type {

    INSTANCE;

    @Override
    public String getTypeName() {
        return "ERROR";
    }

    @Override
    public boolean isSuperclassTo(Type type) {
        return false;
    }

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        return false;
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
