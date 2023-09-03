package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.SymbolProvider;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;

import java.util.List;

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
                    .anyMatch(classType -> classType.isInheritedFrom(type) || classType.equals(type));
            if (matchedInterface) {
                return true;
            }
        }
        return super.isInheritedFrom(type);
    }

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        boolean interfacesCastable = symbol.getInterfaces().stream()
                .flatMap(classType -> classType.getSymbol().getInterfaces().stream())
                .anyMatch(classType -> classType.isCastableTo(type, env));
        if (interfacesCastable) {
            return true;
        }
        return super.isInheritedFrom(type);
    }

    @Override
    public String toString() {
        return symbol.getName();
    }

}
