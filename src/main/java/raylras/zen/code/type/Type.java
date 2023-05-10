package raylras.zen.code.type;

import raylras.zen.code.symbol.Symbol;

public abstract class Type {

    public abstract Kind getKind();

    public Symbol lookupSymbol() {
        return null;
    }

    public enum Kind {
        ANY, FUNCTION, NUMBER, STRING, ARRAY, LIST, MAP, BOOL,
        CLASS, PACKAGE, VOID,
        NONE
    }

}
