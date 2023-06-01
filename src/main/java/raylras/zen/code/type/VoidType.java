package raylras.zen.code.type;

public class VoidType extends Type {

    public static final VoidType INSTANCE = new VoidType();

    @Override
    public Kind getKind() {
        return Kind.VOID;
    }

}
