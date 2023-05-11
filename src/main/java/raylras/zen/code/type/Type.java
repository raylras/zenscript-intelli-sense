package raylras.zen.code.type;

import raylras.zen.code.symbol.Symbol;

public abstract class Type {

    public abstract Kind getKind();

    public Symbol lookupSymbol() {
        return null;
    }

}
