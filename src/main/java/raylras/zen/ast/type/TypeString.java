package raylras.zen.ast.type;

public class TypeString extends Type {

    public static final TypeString INSTANCE = new TypeString();

    private TypeString() {}

    @Override
    public String toString() {
        return "string";
    }

}
