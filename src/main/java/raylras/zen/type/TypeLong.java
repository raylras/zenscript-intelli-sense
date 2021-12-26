package raylras.zen.type;

public class TypeLong implements Type {

    public static final TypeLong INSTANCE = new TypeLong();

    private TypeLong() {}

    @Override
    public String toString() {
        return "long";
    }

}
