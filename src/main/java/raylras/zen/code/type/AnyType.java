package raylras.zen.code.type;

import raylras.zen.code.TypeMatchingResult;

public class AnyType extends Type implements IDataCastable {

    public static final AnyType INSTANCE = new AnyType();

    @Override
    public String toString() {
        return "any";
    }

    @Override
    protected TypeMatchingResult applyCastRules(Type to) {
        return TypeMatchingResult.CASTER;
    }
}
