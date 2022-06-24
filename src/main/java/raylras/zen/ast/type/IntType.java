package raylras.zen.ast.type;

public record IntType() implements Type {

    @Override
    public boolean equivalent(Type type) {
        return type instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

}
