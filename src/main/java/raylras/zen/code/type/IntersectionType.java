package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.MemberProvider;
import raylras.zen.code.symbol.Symbol;

import java.util.List;
import java.util.stream.Collectors;

public class IntersectionType extends Type implements MemberProvider {

    private final List<Type> typeList;

    public IntersectionType(List<Type> typeList) {
        this.typeList = typeList;
    }

    @Override
    public List<Symbol> getMembers() {
        return typeList.stream()
                .filter(MemberProvider.class::isInstance)
                .map(MemberProvider.class::cast)
                .flatMap(it -> it.getMembers().stream())
                .collect(Collectors.toList());
    }

    @Override
    public SubtypeResult isSubtypeOf(Type type, CompilationEnvironment env) {
        return typeList.stream()
                .map(it -> it.isSubtypeOf(type, env))
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
