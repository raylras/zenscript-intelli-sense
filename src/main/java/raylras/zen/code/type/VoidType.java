package raylras.zen.code.type;

public class VoidType extends ClassType {

    public static final VoidType INSTANCE = new VoidType();

    private VoidType() {
        super("void");
    }

    @Override
    public Kind getKind() {
        return Kind.VOID;
    }

}
