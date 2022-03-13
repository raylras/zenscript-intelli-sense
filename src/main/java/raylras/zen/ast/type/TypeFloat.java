package raylras.zen.ast.type;

public class TypeFloat extends Type {

    public static final TypeFloat INSTANCE = new TypeFloat();

    private TypeFloat() {}

    @Override
    public String toString() {
        return "float";
    }

}
