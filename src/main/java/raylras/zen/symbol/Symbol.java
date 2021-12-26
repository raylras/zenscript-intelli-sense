package raylras.zen.symbol;

import org.antlr.v4.runtime.Token;
import raylras.zen.scope.Scope;
import raylras.zen.type.Type;

public interface Symbol<T extends Type> extends Scope {

    T getType();

    void setType(T type);

    Token getToken();

    void setToken(Token token);

    String getSymbolName();

}
