package raylras.zen.ast.type;

public record VoidType() implements Type {

    @Override
    public boolean equivalent(Type type) {
        return type instanceof VoidType;
    }

    @Override
    public String toString() {
        return "void";
    }
    
}
