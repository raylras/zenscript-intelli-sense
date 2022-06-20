package raylras.zen.ast.type;

public final class ClassType implements Type {

    private final String className;

    public ClassType(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public String getSimpleName() {
        return className.substring(className.lastIndexOf('.'));
    }

    @Override
    public boolean equivalent(Type that) {
        if (that != null && that.getClass() == ClassType.class) {
            return this.className.equals(((ClassType) that).className);
        }
        return false;
    }

    @Override
    public String toString() {
        return className;
    }

}
