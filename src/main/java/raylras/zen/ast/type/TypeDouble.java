package raylras.zen.ast.type;

public class TypeDouble extends Type {

    public static final TypeDouble INSTANCE = new TypeDouble();

    private TypeDouble() {}

    @Override
    public String toString() {
        return "double";
    }

}
