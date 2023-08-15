package raylras.zen.code.type;

import raylras.zen.code.TypeMatchingResult;

public abstract class NumberType extends Type implements IDataCastable {

    @Override
    protected TypeMatchingResult applyCastRules(Type to) {
        return to instanceof NumberType ? TypeMatchingResult.CASTER : TypeMatchingResult.INVALID;
    }

    @Override
    public SubtypeResult isSubtypeOf(Type type) {
        if (type instanceof NumberType) {
            return SubtypeResult.INHERIT;
        }
        return super.isSubtypeOf(type);
    }

}
