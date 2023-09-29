package raylras.zen.model.symbol;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.type.Type;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface SymbolProvider extends Iterable<Symbol> {

    Collection<Symbol> getSymbols();

    SymbolProvider EMPTY = Collections::emptyList;

    static SymbolProvider of(Collection<Symbol> symbols) {
        return () -> symbols;
    }

    default Symbol getFirst() {
        if (isNotEmpty()) {
            return iterator().next();
        } else {
            return null;
        }
    }

    default SymbolProvider filter(Predicate<Symbol> predicate) {
        return () -> getSymbols().stream().filter(predicate).toList();
    }

    default SymbolProvider addAll(Collection<? extends Symbol> others) {
        Collection<Symbol> symbols = new ArrayList<>(getSymbols());
        symbols.addAll(others);
        return of(symbols);
    }

    default SymbolProvider orElse(Collection<Symbol> others) {
        return isNotEmpty() ? this : of(others);
    }

    default int size() {
        return getSymbols().size();
    }

    default boolean isEmpty() {
        return getSymbols().isEmpty();
    }

    default boolean isNotEmpty() {
        return !isEmpty();
    }

    default Stream<Symbol> stream() {
        return getSymbols().stream();
    }

    default SymbolProvider withExpands(CompilationEnvironment env) {
        if (this instanceof Type type) {
            return addAll(env.getExpands(type));
        } else {
            return this;
        }
    }

    @Override
    default Iterator<Symbol> iterator() {
        return getSymbols().iterator();
    }

}
