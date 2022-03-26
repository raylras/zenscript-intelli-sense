package raylras.zen.ast.type;

public class TypeByte extends TypeNumber {

    public static final TypeByte INSTANCE = new TypeByte();

    private TypeByte() {}

    @Override
    public String toString() {
        return "byte";
    }

}
