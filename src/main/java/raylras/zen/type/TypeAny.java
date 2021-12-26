package raylras.zen.type;

public class TypeAny implements Type {

    public static final TypeAny INSTANCE = new TypeAny();

    private TypeAny() {}

    @Override
    public String toString() {
        return "any";
    }

}
