package raylras.zen.ast.type;

public class TypeVoid extends Type {

    public static final TypeVoid INSTANCE = new TypeVoid();

    private TypeVoid() {}

    @Override
    public String toString() {
        return "void";
    }

}
