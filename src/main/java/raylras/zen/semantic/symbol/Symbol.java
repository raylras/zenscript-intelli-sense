package raylras.zen.semantic.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.semantic.scope.Scope;
import raylras.zen.semantic.type.Type;

public abstract class Symbol<T extends Type> {

    protected Scope parentScope;
    protected ParseTree node;

    public Symbol(Scope parentScope, ParseTree node) {
        this.parentScope = parentScope;
        this.node = node;
    }

    public abstract String getName();

    public abstract void setName(String name);

    public abstract T getType();

    public abstract void setType(T type);

    public Scope getParentScope() {
        return parentScope;
    }

    public void setParentScope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    public ParseTree getNode() {
        return node;
    }

    public void setNode(ParseTree node) {
        this.node = node;
    }

}
