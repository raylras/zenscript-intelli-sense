package raylras.zen.code.type;

import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.Operators;

import java.util.List;

public class ClassType extends Type {

    private final ClassSymbol symbol;

    public ClassType(ClassSymbol symbol) {
        this.symbol = symbol;
    }

    public ClassSymbol getSymbol() {
        return symbol;
    }

    @Override
    public List<Symbol> getMembers() {
        return symbol.getMembers();
    }

    @Override
    public SubtypeResult isSubtypeOf(Type type) {
        if (this.equals(type)) {
            return SubtypeResult.SELF;
        }
        if (type instanceof ClassType) {
            boolean matchedInterface = symbol.getInterfaces().stream()
                    .flatMap(classType -> classType.getSymbol().getInterfaces().stream())
                    .anyMatch(classType -> classType.isSubtypeOf(type).matched());
            if (matchedInterface) {
                return SubtypeResult.INHERIT;
            }
        }
        if (Operators.hasCaster(this, type)) {
            return SubtypeResult.CASTER;
        }
        return super.isSubtypeOf(type);
    }

    @Override
    public String toString() {
        return symbol.getName();
    }

}
