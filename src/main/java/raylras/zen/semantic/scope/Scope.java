package raylras.zen.semantic.scope;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.semantic.symbol.Symbol;

import java.util.List;

public interface Scope {

    void addSymbol(Symbol<?> symbol);

    Symbol<?> findSymbol(String name);

    List<Symbol<?>> getSymbols();

    Scope getParentScope();

    void setParentScope(Scope parentScope);

    ParseTree getNode();

    void setNode(ParseTree node);

    String getName();

    void setName(String name);

}
