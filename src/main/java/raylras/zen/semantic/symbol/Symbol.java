package raylras.zen.semantic.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.semantic.scope.Scope;
import raylras.zen.semantic.type.Type;

public abstract class Symbol {

    private String name;
    private Type type;
    private Scope parentScope;
    private ParseTree node;

    public Symbol(ParseTree node, String name) {
        this.node = node;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

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
