package raylras.zen.code;

import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.SymbolGroup;
import raylras.zen.code.type.Type;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface SymbolProvider extends Iterable<Symbol> {

    SymbolGroup getSymbols();

    SymbolProvider EMPTY = SymbolGroup::new;

    static SymbolProvider of(Collection<Symbol> members) {
        return () -> SymbolGroup.of(members);
    }

    default Symbol getFirst() {
        return getSymbols().iterator().next();
    }

    default SymbolProvider filter(Predicate<Symbol> predicate) {
        return () -> SymbolGroup.of(getSymbols().stream().filter(predicate).toList());
    }

    default SymbolProvider limit(long maxSize) {
        return () -> SymbolGroup.of(getSymbols().stream().limit(maxSize).toList());
    }

    default SymbolProvider merge(SymbolProvider other) {
        return () -> SymbolGroup.of(this).addAll(other);
    }

    default SymbolProvider orElse(SymbolProvider other) {
        SymbolGroup symbols = getSymbols();
        if (symbols.isEmpty()) {
            return other;
        }
        return this;
    }

    default int size() {
        return getSymbols().size();
    }

    default Stream<Symbol> stream() {
        return getSymbols().stream();
    }

    default SymbolProvider withExpands(CompilationEnvironment env) {
        if (this instanceof Type type) {
            return merge(() -> SymbolGroup.of(env.getExpandMembers(type)));
        } else {
            return this;
        }
    }

    @Override
    default Iterator<Symbol> iterator() {
        return getSymbols().iterator();
    }

}
