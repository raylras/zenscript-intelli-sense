package raylras.zen.code.type;

import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;

import java.util.List;
import java.util.Optional;

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

    @Override
    public String toString() {
        return symbol.getSimpleName();
    }

}
