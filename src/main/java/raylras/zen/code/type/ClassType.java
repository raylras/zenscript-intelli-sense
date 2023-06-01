package raylras.zen.code.type;

import raylras.zen.code.Declarator;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;

import java.util.List;
import java.util.stream.Collectors;

public class ClassType extends Type {

    private final ClassSymbol symbol;

    public ClassType(ClassSymbol symbol) {
        this.symbol = symbol;
    }

    public ClassSymbol getSymbol() {
        return symbol;
    }

    @Override
    public Kind getKind() {
        return Kind.CLASS;
    }

    @Override
    public List<Symbol> getInstanceMembers() {
        return symbol.getMembers().stream()
                .filter(symbol -> !symbol.isDeclaredBy(Declarator.STATIC))
                .collect(Collectors.toList());
    }

    @Override
    public List<Symbol> getStaticMembers() {
        return symbol.getMembers().stream()
                .filter(symbol -> symbol.isDeclaredBy(Declarator.STATIC))
                .collect(Collectors.toList());
    }

    @Override
    public List<Symbol> getMembers() {
        return symbol.getMembers();
    }

    @Override
    public String toString() {
        return symbol.getDeclaredName();
    }

}
