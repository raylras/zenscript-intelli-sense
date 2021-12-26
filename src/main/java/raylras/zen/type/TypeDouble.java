package raylras.zen.type;

public class TypeDouble implements Type {

    public static final TypeDouble INSTANCE = new TypeDouble();

    private TypeDouble() {}

    @Override
    public String toString() {
        return "double";
    }

}
