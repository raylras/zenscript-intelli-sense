package raylras.zen.code.type;

import raylras.zen.code.symbol.Symbol;

import java.util.Collections;
import java.util.List;

public abstract class Type {

    public abstract Kind getKind();

    public List<Symbol> getInstanceMembers() {
        return Collections.emptyList();
    }

    public List<Symbol> getStaticMembers() {
        return Collections.emptyList();
    }

    public List<Symbol> getMembers() {
        return Collections.emptyList();
    }

    public enum Kind {
        ANY, CLASS, FUNCTION, NUMBER, STRING, ARRAY, LIST, MAP, BOOL, VOID, NONE
    }

}
