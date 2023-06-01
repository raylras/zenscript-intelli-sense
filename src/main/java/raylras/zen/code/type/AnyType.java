package raylras.zen.code.type;

public class AnyType extends Type {

    public static final AnyType INSTANCE = new AnyType();

    @Override
    public Kind getKind() {
        return Kind.ANY;
    }

}
