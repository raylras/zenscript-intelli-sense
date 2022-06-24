package raylras.zen.ast.type;

public record ClassType(String className) implements Type {

    public String getSimpleName() {
        return className.substring(className.lastIndexOf('.'));
    }

    @Override
    public boolean equivalent(Type type) {
        if (type instanceof ClassType that) {
            return this.className.equals(that.className);
        }
        return false;
    }

    @Override
    public String toString() {
        return className;
    }

}
