package raylras.zen.code.type;

public class NoType extends Type {

    public static final NoType INSTANCE = new NoType();

    @Override
    public Tag getTag() {
        return Tag.NO_TAG;
    }

    @Override
    public String toString() {
        return "none";
    }

}
