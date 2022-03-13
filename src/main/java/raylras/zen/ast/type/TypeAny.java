package raylras.zen.ast.type;

public class TypeAny extends Type {

    public static final TypeAny INSTANCE = new TypeAny();

    private TypeAny() {}

    @Override
    public String toString() {
        return "any";
    }

}
