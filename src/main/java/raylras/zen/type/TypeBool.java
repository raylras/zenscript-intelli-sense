package raylras.zen.type;

public class TypeBool implements Type {

    public static final TypeBool INSTANCE = new TypeBool();

    private TypeBool() {}

    @Override
    public String toString() {
        return "bool";
    }

}
