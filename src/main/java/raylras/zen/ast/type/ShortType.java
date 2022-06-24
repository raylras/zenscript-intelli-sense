package raylras.zen.ast.type;

public record ShortType() implements Type {

    @Override
    public boolean equivalent(Type type) {
        return type instanceof ShortType;
    }

    @Override
    public String toString() {
        return "short";
    }

}
