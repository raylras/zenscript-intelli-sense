package raylras.zen.ast.type;

public class TypeInt extends TypeNumber {

    public static final TypeInt INSTANCE = new TypeInt();

    private TypeInt() {}

    @Override
    public String toString() {
        return "int";
    }

}
