package raylras.zen.ast.type;

public class TypeBracketHandler extends Type {

    public static final TypeBracketHandler INSTANCE = new TypeBracketHandler();

    private TypeBracketHandler() {}

    @Override
    public String toString() {
        return "<BracketHandler>";
    }

}
