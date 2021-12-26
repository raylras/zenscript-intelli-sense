package raylras.zen.type;

public class TypeVoid implements Type {

    public static final TypeVoid INSTANCE = new TypeVoid();

    private TypeVoid() {}

    @Override
    public String toString() {
        return "void";
    }

}
