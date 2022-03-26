package raylras.zen.ast.type;

public class TypeDouble extends TypeNumber {

    public static final TypeDouble INSTANCE = new TypeDouble();

    private TypeDouble() {}

    @Override
    public String toString() {
        return "double";
    }

}
