package raylras.zen.symbol;

import org.antlr.v4.runtime.Token;
import raylras.zen.scope.Scope;
import raylras.zen.type.Type;

// Variable, Parameter and Argument

public class SymbolVariable<T extends Type> extends SymbolBase<T> {

    public SymbolVariable(Scope parent, T type, Token token) {
        super(parent, type, token);
    }

}
