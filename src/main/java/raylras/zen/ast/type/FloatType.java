package raylras.zen.ast.type;

public record FloatType() implements Type {

    @Override
    public boolean equivalent(Type type) {
        return type instanceof FloatType;
    }

    @Override
    public String toString() {
        return "float";
    }

}
