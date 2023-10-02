package raylras.zen.model.symbol;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.type.Type;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface SymbolProvider extends Iterable<Symbol> {

    Collection<Symbol> getSymbols();

    SymbolProvider EMPTY = Collections::emptyList;

    static <T extends Symbol> SymbolProvider of(Collection<T> symbols) {
        return () -> Collections.unmodifiableCollection(symbols);
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
        Collection<Symbol> result = new ArrayList<>(getSymbols());
        if (this instanceof Type type) {
            result.addAll(env.getExpands(type));
        }
        return of(result);
    }

    @Override
    default Iterator<Symbol> iterator() {
        return getSymbols().iterator();
    }

}
