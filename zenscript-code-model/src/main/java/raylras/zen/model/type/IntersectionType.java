package raylras.zen.model.type;

import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolProvider;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public record IntersectionType(List<Type> typeList) implements Type, SymbolProvider {

    @Override
    public String getTypeName() {
        return typeList.stream().map(Type::getTypeName).collect(Collectors.joining(" & "));
    }

    @Override
    public boolean isSuperclassTo(Type type) {
        return typeList.stream().anyMatch(it -> it.isSuperclassTo(type));
    }

    @Override
    public Collection<Symbol> getSymbols() {
        MemberValidator validator = new MemberValidator();
        for (Type type : typeList) {
            if (type instanceof SymbolProvider provider) {
                validator.addAll(provider.getSymbols());
            }
        }
        return validator.getMembers();
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
