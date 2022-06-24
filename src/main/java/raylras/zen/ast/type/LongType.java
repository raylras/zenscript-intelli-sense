package raylras.zen.ast.type;

public record LongType() implements Type {

    @Override
    public boolean equivalent(Type type) {
        return type instanceof LongType;
    }

    @Override
    public String toString() {
        return "long";
    }
    
}
