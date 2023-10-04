package raylras.zen.model.symbol;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.type.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public interface SymbolProvider {

    Collection<Symbol> getSymbols();

    static SymbolProvider of(Collection<? extends Symbol> symbols) {
        return () -> Collections.unmodifiableCollection(symbols);
    }

    static SymbolProvider empty() {
        return Collections::emptyList;
    }

    default SymbolProvider withExpands(CompilationEnvironment env) {
        if (this instanceof Type expandingType) {
            Collection<Symbol> result = new ArrayList<>(getSymbols());
            env.getExpands(expandingType).forEach(result::add);
            return of(result);
        } else {
            return this;
        }
    }

}
