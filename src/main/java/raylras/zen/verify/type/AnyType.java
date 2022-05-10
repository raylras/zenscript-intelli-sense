package raylras.zen.verify.type;

public class AnyType extends AbstractType {

    public static final AnyType INSTANCE = new AnyType();

    private AnyType() {}

    @Override
    public String toString() {
        return "any";
    }

}
