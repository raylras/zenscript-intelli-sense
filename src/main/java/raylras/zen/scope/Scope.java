package raylras.zen.scope;

import raylras.zen.symbol.Symbol;
import raylras.zen.type.Type;

import java.util.Map;

public interface Scope {

    Scope getParent();

    void setParent(Scope parent);

    String getScopeName();

    void setScopeName(String scopeName);

    Map<String, Symbol<? extends Type>> getSymbols();

    <T extends Type> void define(Symbol<T> symbol);

    <T extends Type> void define(String signature, Symbol<T> symbol);

    Symbol<? extends Type> resolve(String name);

}
