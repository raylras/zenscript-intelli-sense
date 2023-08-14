package raylras.zen.code.type;

import raylras.zen.code.TypeMatchingResult;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;

import java.util.*;
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

    public Optional<Symbol> findAnnotatedMember(String header) {
        for (Symbol member : getMembers()) {
            if (member.getDeclaredAnnotation(header).isPresent()) {
                return Optional.of(member);
            }
        }
        return Optional.empty();
    }

    public Stream<Symbol> findAnnotatedMembers(String header) {
        return getMembers().stream()
                .filter(it -> it.getDeclaredAnnotation(header).isPresent());
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
        boolean hasCaster = findAnnotatedMembers("#caster")
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
}
