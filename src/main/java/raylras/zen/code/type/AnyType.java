package raylras.zen.code.type;

public class AnyType extends Type {

    @Override
    public Kind getKind() {
        return Kind.ANY;
    }

    @Override
    public String toString() {
        return "any";
    }

}
