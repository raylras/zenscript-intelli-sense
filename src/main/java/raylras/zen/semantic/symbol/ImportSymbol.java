package raylras.zen.semantic.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.semantic.scope.Scope;
import raylras.zen.semantic.type.Type;

import java.util.Objects;

public class ImportSymbol extends Symbol<Type> {

    private String name;
    private Type type;

    public ImportSymbol(String name, Scope parentScope, ParseTree node) {
        super(parentScope, node);
        Objects.requireNonNull(name);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        Objects.requireNonNull(type);
        this.type = type;
    }

    @Override
    public String toString() {
        return "(symbol (import " + name + "))";
    }

}
