package raylras.zen.ast.type;

public record BoolType() implements Type {

    @Override
    public boolean equivalent(Type type) {
        return type instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

}
