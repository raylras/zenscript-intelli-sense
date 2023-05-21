package raylras.zen.code.type;

public class NullType extends ClassType {

    public static final NullType INSTANCE = new NullType();

    private NullType() {
        super("null");
    }

    @Override
    public Kind getKind() {
        return Kind.NULL;
    }

}
