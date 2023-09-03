package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.SymbolProvider;
import raylras.zen.code.symbol.Symbol;

import java.util.List;
import java.util.stream.Collectors;

public class IntersectionType extends Type implements SymbolProvider {

    private final List<Type> typeList;

    public IntersectionType(List<Type> typeList) {
        this.typeList = typeList;
    }

    @Override
    public List<Symbol> getSymbols() {
        return typeList.stream()
                .filter(SymbolProvider.class::isInstance)
                .map(SymbolProvider.class::cast)
                .flatMap(it -> it.getSymbols().stream())
                .collect(Collectors.toList());
    }

    @Override
    public boolean isInheritedFrom(Type type) {
        return typeList.stream().anyMatch(it -> it.isInheritedFrom(type));
    }

    @Override
    public boolean isCastableTo(Type type, CompilationEnvironment env) {
        return typeList.stream().anyMatch(it -> it.isCastableTo(type, env));
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    @Override
    public String toString() {
        return typeList.stream().map(Object::toString).collect(Collectors.joining(" & "));
    }

}
