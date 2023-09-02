package raylras.zen.code.type;

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
    public SubtypeResult isSubtypeOf(Type type) {
        return typeList.stream()
                .map(it -> it.isSubtypeOf(type))
                .min(SubtypeResult.PRIORITY_COMPARATOR)
                .orElse(SubtypeResult.MISMATCH);
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    @Override
    public String toString() {
        return typeList.stream().map(Object::toString).collect(Collectors.joining(" & "));
    }

}
