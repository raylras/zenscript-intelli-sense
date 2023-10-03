package raylras.zen.model.symbol;

import raylras.zen.model.type.Type;

public interface Symbol {

    String getName();

    Kind getKind();

    Type getType();

    Modifier getModifier();

    default boolean isStatic() {
        return switch (getModifier()) {
            case STATIC, GLOBAL, IMPLICIT_STATIC -> true;
            default -> false;
        };
    }

    default boolean isGlobal() {
        return getModifier() == Modifier.GLOBAL;
    }

    default boolean isReadonly() {
        return switch (getModifier()) {
            case VAL, STATIC, GLOBAL, IMPLICIT_VAL, IMPLICIT_STATIC -> true;
            default -> false;
        };
    }

    enum Kind {
        IMPORT, PACKAGE, CLASS, VARIABLE, PARAMETER, FUNCTION, OPERATOR, NONE
    }

    enum Modifier {
        VAR, VAL, STATIC, GLOBAL, EXPAND,
        IMPLICIT_VAR, IMPLICIT_VAL, IMPLICIT_STATIC,
        NONE
    }

}
