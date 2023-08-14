package raylras.zen.code.type;

import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.TypeMatchingResult;

import java.util.Collections;
import java.util.List;

public abstract class Type {

    public List<Symbol> getMembers() {
        return Collections.emptyList();
    }

    public TypeMatchingResult canCastTo(Type to) {
        if (this.equals(to)) {
            return TypeMatchingResult.EQUALS;
        }
        if (to == AnyType.INSTANCE) {
            return TypeMatchingResult.CASTER;
        }
        if (this instanceof IDataCastable && to.toString().equals("crafttweaker.data.IData")) {
            return TypeMatchingResult.CASTER;
        }
        return applyCastRules(to);
    }

    protected TypeMatchingResult applyCastRules(Type to) {
        return TypeMatchingResult.INVALID;
    }

    public abstract String toString();
}
