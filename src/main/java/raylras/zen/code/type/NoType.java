package raylras.zen.code.type;

public class NoType extends Type {

    public static final NoType INSTANCE = new NoType();

    @Override
    public Kind getKind() {
        return Kind.NO_KIND;
    }

    @Override
    public String toString() {
        return "none";
    }

}
