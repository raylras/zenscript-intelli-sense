package raylras.zen.code.type;

public class IntType extends NumberType {

    public static final IntType INSTANCE = new IntType();

    @Override
    public String toString() {
        return "int";
    }

}
