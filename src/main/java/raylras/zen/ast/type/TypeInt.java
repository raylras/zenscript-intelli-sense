package raylras.zen.ast.type;

public class TypeInt extends Type {

    public static final TypeInt INSTANCE = new TypeInt();

    private TypeInt() {}

    @Override
    public String toString() {
        return "int";
    }

}
