package raylras.zen.code.type;

import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;

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
        if (type instanceof ClassType) {
            boolean matchedInterface = symbol.getInterfaces().stream()
                    .flatMap(classType -> classType.getSymbol().getInterfaces().stream())
                    .anyMatch(classType -> classType.isSubtypeOf(type).matched());
            if (matchedInterface) {
                return SubtypeResult.INHERIT;
            }
        }
        return super.isSubtypeOf(type);
    }

    @Override
    public String toString() {
        return symbol.getName();
    }

}
