package raylras.zen.ast.type;

public class TypeBoolean extends Type {

    public static final TypeBoolean INSTANCE = new TypeBoolean();

    private TypeBoolean() {}

    @Override
    public String toString() {
        return "bool";
    }

}
