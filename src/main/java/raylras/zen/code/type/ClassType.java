package raylras.zen.code.type;

import raylras.zen.code.SymbolProvider;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolGroup;

import java.util.List;
import java.util.Objects;

public class ClassType extends Type implements SymbolProvider {

    private final ClassSymbol symbol;

    public ClassType(ClassSymbol symbol) {
        this.symbol = symbol;
    }

    public ClassSymbol getSymbol() {
        return symbol;
    }

    @Override
    public List<Symbol> getSymbols() {
        return symbol.getMembers();
    }

    @Override
    public boolean isInheritedFrom(Type type) {
        if (type instanceof ClassType) {
            boolean matchedInterface = symbol.getInterfaces().stream()
                    .flatMap(classType -> classType.getSymbol().getInterfaces().stream())
                    .anyMatch(classType -> classType.isInheritedFrom(type));
            if (matchedInterface) {
                return true;
            }
        }
        return super.isInheritedFrom(type);
    }

    @Override
    public String toString() {
        return symbol.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassType type = (ClassType) o;
        return Objects.equals(symbol, type.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }
}
