package raylras.zen.code.symbol;

import raylras.zen.code.type.Type;

public interface Symbol {

    String getName();

    Kind getKind();

    Type getType();

    Modifier getModifier();

    boolean isModifiedBy(Modifier modifier);

    enum Kind {
        IMPORT, PACKAGE, CLASS, VARIABLE, PARAMETER, FUNCTION, NONE
    }

    enum Modifier {
        VAR, VAL, STATIC, GLOBAL, EXPAND, NONE
    }

}
