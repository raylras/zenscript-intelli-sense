package raylras.zen.ast.type;

public record ByteType() implements Type {

    @Override
    public boolean equivalent(Type type) {
        return type instanceof ByteType;
    }

    @Override
    public String toString() {
        return "byte";
    }

}
