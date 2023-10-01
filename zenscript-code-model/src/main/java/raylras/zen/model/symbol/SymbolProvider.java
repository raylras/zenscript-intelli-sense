package raylras.zen.model.symbol;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.type.Type;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface SymbolProvider<T extends Symbol> extends Iterable<T> {

    Collection<T> getSymbols();

    SymbolProvider<Symbol> EMPTY = Collections::emptyList;

    static <T extends Symbol> SymbolProvider<T> of(Collection<T> symbols) {
        return () -> symbols;
    }

    default Symbol getFirst() {
        if (isNotEmpty()) {
            return iterator().next();
        } else {
            return null;
        }
    }

    default SymbolProvider<T> filter(Predicate<T> predicate) {
        return () -> getSymbols().stream().filter(predicate).toList();
    }

    default SymbolProvider<Symbol> addAll(Collection<? extends Symbol> others) {
        Collection<Symbol> symbols = new ArrayList<>(getSymbols());
        symbols.addAll(others);
        return of(symbols);
    }

    default SymbolProvider<T> orElse(Collection<T> others) {
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

    default Stream<T> stream() {
        return getSymbols().stream();
    }

    default SymbolProvider<Symbol> withExpands(CompilationEnvironment env) {
        Collection<Symbol> result = new ArrayList<>(getSymbols());
        if (this instanceof Type type) {
            result.addAll(env.getExpands(type));
        }
        return of(result);
    }

    @Override
    default Iterator<T> iterator() {
        return getSymbols().iterator();
    }

}
