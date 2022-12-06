package raylras.zen.semantic.type;

import java.util.Objects;

public class ClassType implements Type {

    private String className;

    public ClassType(String className) {
        Objects.requireNonNull(className);
        this.className = className;
    }

    public void setClassName(String className) {
        Objects.requireNonNull(className);
        this.className = className;
    }

    @Override
    public String typeName() {
        return className;
    }

    @Override
    public boolean isType(Type type) {
        if (this == type) {
            return true;
        }
        if (type instanceof ClassType) {
            ClassType that = (ClassType) type;
            return this.className.equals(that.className);
        }
        return false;
    }

    @Override
    public String toString() {
        return "(type (class (" + className + "))";
    }

}
