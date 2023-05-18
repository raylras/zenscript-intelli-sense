package raylras.zen.code.type;

public class VoidType extends Type {

    @Override
    public Kind getKind() {
        return Kind.VOID;
    }

    @Override
    public String toString() {
        return "void";
    }

}