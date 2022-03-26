package raylras.zen.ast.type;

public class TypeShort extends TypeNumber {

    public static final TypeShort INSTANCE = new TypeShort();

    private TypeShort() {}

    @Override
    public String toString() {
        return "short";
    }

}
