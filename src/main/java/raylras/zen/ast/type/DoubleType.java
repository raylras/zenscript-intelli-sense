package raylras.zen.ast.type;

public record DoubleType() implements Type {

    @Override
    public boolean equivalent(Type type) {
        return type instanceof DoubleType;
    }

    @Override
    public String toString() {
        return "double";
    }

}
