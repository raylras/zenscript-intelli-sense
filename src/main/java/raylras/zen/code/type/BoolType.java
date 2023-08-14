package raylras.zen.code.type;

public class BoolType extends Type implements IDataCastable {

    public static final BoolType INSTANCE = new BoolType();

    @Override
    public String toString() {
        return "bool";
    }

}
