package raylras.zen.code.type;

import raylras.zen.code.symbol.Symbol;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Type {

    public List<Symbol> getMembers() {
        return Collections.emptyList();
    }

    public boolean isAssignableTo(Type type) {
        return isSubtypeOf(type).matched();
    }

    public SubtypeResult isSubtypeOf(Type type) {
        if (this == type) {
            return SubtypeResult.SELF;
        }
        if (type == AnyType.INSTANCE) {
            return SubtypeResult.INHERIT;
        }
        return SubtypeResult.MISMATCH;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(toString());
    }

}
