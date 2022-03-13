package raylras.zen.ast.type;

public class TypeBool extends Type {

    public static final TypeBool INSTANCE = new TypeBool();

    private TypeBool() {}

    @Override
    public String toString() {
        return "bool";
    }

}
