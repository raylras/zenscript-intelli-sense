package raylras.zen.code.type;

import raylras.zen.code.TypeMatchingResult;
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
    protected TypeMatchingResult applyCastRules(Type to) {
        if (to instanceof ClassType) {
            Set<ClassType> subclasses = new HashSet<>();
            for (ClassType anInterface : getSymbol().getInterfaces()) {
                subclasses.addAll(anInterface.getSymbol().getInterfaces());
            }
            if (subclasses.contains(to)) {
                return TypeMatchingResult.INHERIT;
            }
        }
        boolean hasCaster = findMemberWithAnnotation("#caster")
                .map(Symbol::getType)
                .filter(FunctionType.class::isInstance)
                .map(FunctionType.class::cast)
                .map(FunctionType::getReturnType)
                .anyMatch(to::equals);
        if (hasCaster) {
            return TypeMatchingResult.CASTER;
        }
        return TypeMatchingResult.INVALID;
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
    public String toString() {
        return symbol.getSimpleName();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass() &&
                ((ClassType) obj).getSymbol().getQualifiedName().equals(this.getSymbol().getQualifiedName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSymbol().getQualifiedName());
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
