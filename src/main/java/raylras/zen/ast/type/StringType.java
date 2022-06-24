package raylras.zen.ast.type;

public record StringType() implements Type {

    @Override
    public boolean equivalent(Type type) {
        return type instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

}
