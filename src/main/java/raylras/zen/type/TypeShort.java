package raylras.zen.type;

public class TypeShort implements Type {

    public static final TypeShort INSTANCE = new TypeShort();

    private TypeShort() {}

    @Override
    public String toString() {
        return "short";
    }

}
