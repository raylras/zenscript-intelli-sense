package raylras.zen.code.type;

public class AnyType extends ClassType {

    public static final AnyType INSTANCE = new AnyType();

    private AnyType() {
        super("any");
    }

    @Override
    public Kind getKind() {
        return Kind.ANY;
    }

}
