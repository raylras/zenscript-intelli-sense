package raylras.zen.code.type;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.symbol.Symbol;

public abstract class Type {


    public abstract Kind getKind();

    public enum Kind {
        ANY, CLASS, FUNCTION, NUMBER, STRING, ARRAY, LIST, MAP, BOOL, VOID, NULL, INT_RANGE, NONE
    }

}
