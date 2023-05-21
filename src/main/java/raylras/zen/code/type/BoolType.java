package raylras.zen.code.type;

public class BoolType extends NamedType {

    public static final BoolType INSTANCE = new BoolType();

    private BoolType() {
        super("bool");
    }

    @Override
    public Kind getKind() {
        return Kind.BOOL;
    }

}
