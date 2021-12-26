package raylras.zen.type;

public class TypeBracketHandler implements Type {

    public static final TypeBracketHandler INSTANCE = new TypeBracketHandler();

    private TypeBracketHandler() {}

    @Override
    public String toString() {
        return "BracketHandler";
    }

}
