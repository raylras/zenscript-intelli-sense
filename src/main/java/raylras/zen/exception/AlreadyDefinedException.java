package raylras.zen.exception;

import raylras.zen.scope.Scope;
import raylras.zen.symbol.Symbol;
import raylras.zen.type.Type;

public class AlreadyDefinedException extends RuntimeException {

    private final Scope scope;
    private final Symbol<? extends Type> oldSymbol;
    private final Symbol<? extends Type> newSymbol;

    public AlreadyDefinedException(String message, Scope scope, Symbol<? extends Type> oldSymbol, Symbol<? extends Type> newSymbol) {
        super(message);
        this.scope = scope;
        this.oldSymbol = oldSymbol;
        this.newSymbol = newSymbol;
    }

    public AlreadyDefinedException(String message, Throwable cause, Scope scope, Symbol<? extends Type> oldSymbol, Symbol<? extends Type> newSymbol) {
        super(message, cause);
        this.scope = scope;
        this.oldSymbol = oldSymbol;
        this.newSymbol = newSymbol;

    }

    public Scope getScope() {
        return scope;
    }

    public Symbol<? extends Type> getOldSymbol() {
        return oldSymbol;
    }

    public Symbol<? extends Type> getNewSymbol() {
        return newSymbol;
    }

}
