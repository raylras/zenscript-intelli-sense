package raylras.zen.code.type;

public class BoolType extends Type {

    public static final BoolType INSTANCE = new BoolType();

    @Override
    public Kind getKind() {
        return Kind.BOOL;
    }

}
