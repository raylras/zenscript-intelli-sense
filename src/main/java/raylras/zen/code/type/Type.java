package raylras.zen.code.type;

import raylras.zen.code.symbol.Symbol;

import java.util.Collections;
import java.util.List;

public abstract class Type {

    public List<Symbol> getMembers() {
        return Collections.emptyList();
    }

    public boolean isAssignableTo(Type type) {
        return isSubtypeOf(type).matched();
    }

    public SubtypeResult isSubtypeOf(Type type) {
        if (this.equals(type)) {
            return SubtypeResult.SELF;
        }
        if (type.equals(AnyType.INSTANCE)) {
            return SubtypeResult.INHERIT;
        }
        return SubtypeResult.MISMATCH;
    }

}
