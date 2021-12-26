package raylras.zen.type;

public class TypeFloat implements Type {

    public static final TypeFloat INSTANCE = new TypeFloat();

    private TypeFloat() {}

    @Override
    public String toString() {
        return "float";
    }

}
