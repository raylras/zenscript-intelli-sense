package raylras.zen.code.type;

import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Stream<Symbol> findMemberWithAnnotation(String header) {
        return getMembers().stream().filter(it -> it.isAnnotatedBy(header));
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
        if (getCasterTypeList().contains(type)) {
            return SubtypeResult.CASTER;
        }
        return super.isSubtypeOf(type);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ClassType classType = (ClassType) object;
        return Objects.equals(symbol, classType.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(symbol.getQualifiedName());
    }

    @Override
    public String toString() {
        return symbol.getName();
    }

    private List<Type> getCasterTypeList() {
        return findMemberWithAnnotation("#caster")
                .map(Symbol::getType)
                .filter(FunctionType.class::isInstance)
                .map(FunctionType.class::cast)
                .map(FunctionType::getReturnType)
                .collect(Collectors.toList());
    }

}
