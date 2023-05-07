package raylras.zen.code.type;

public class BoolType extends Type {

    @Override
    public Kind getKind() {
        return Kind.BOOL;
    }

    @Override
    public String toString() {
        return "bool";
    }

}
