package raylras.zen.model.type;

import raylras.zen.model.symbol.ClassSymbol;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolProvider;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public record ClassType(ClassSymbol symbol) implements Type, SymbolProvider {

    public String getQualifiedName() {
        return symbol.getQualifiedName();
    }

    public String getSimpleName() {
        return symbol.getSimpleName();
    }

    @Override
    public String getTypeName() {
        return getQualifiedName();
    }

    @Override
    public String getSimpleTypeName() {
        return getSimpleName();
    }

    @Override
    public boolean isSuperclassTo(Type type) {
        if (type instanceof ClassType that) {
            Deque<ClassSymbol> deque = new ArrayDeque<>();
            deque.push(that.symbol);
            while (!deque.isEmpty()) {
                ClassSymbol pop = deque.pop();
                if (pop.equals(this.symbol)) {
                    return true;
                }
                deque.addAll(pop.getInterfaces());
            }
        }
        return false;
    }

    @Override
    public Collection<Symbol> getSymbols() {
        MemberValidator validator = new MemberValidator();
        validator.addAll(symbol.getDeclaredMembers());
        Deque<ClassSymbol> interfaceDeque = new ArrayDeque<>(symbol.getInterfaces());
        while (!interfaceDeque.isEmpty()) {
            ClassSymbol pop = interfaceDeque.pop();
            validator.addAll(pop.getSymbols());
            interfaceDeque.addAll(pop.getInterfaces());
        }
        return validator.getMembers();
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
