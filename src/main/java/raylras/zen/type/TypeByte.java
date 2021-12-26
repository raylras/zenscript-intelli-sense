package raylras.zen.type;

public class TypeByte implements Type {

    public static final TypeByte INSTANCE = new TypeByte();

    private TypeByte() {}

    @Override
    public String toString() {
        return "byte";
    }

}
