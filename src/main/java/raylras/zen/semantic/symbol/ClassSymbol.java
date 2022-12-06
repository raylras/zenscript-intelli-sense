package raylras.zen.semantic.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.semantic.scope.BaseScope;
import raylras.zen.semantic.scope.Scope;
import raylras.zen.semantic.type.ClassType;

import java.util.List;
import java.util.Objects;

public class ClassSymbol extends Symbol<ClassType> implements Scope {

    private String className;
    private ClassSymbol superClass;
    private ClassType type;
    private Scope scope;

    public ClassSymbol(String name, Scope parentScope, ParseTree parseTree) {
        super(parentScope, parseTree);
        Objects.requireNonNull(name);
        this.className = name;
        this.scope = new BaseScope(name, parentScope, parseTree);
    }

    @Override
    public void addSymbol(Symbol<?> symbol) {
        scope.addSymbol(symbol);
    }

    @Override
    public Symbol<?> findSymbol(String name) {
        return scope.findSymbol(name);
    }

    @Override
    public List<Symbol<?>> getSymbols() {
        return scope.getSymbols();
    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public void setName(String name) {
        Objects.requireNonNull(name);
        this.className = name;
    }

    @Override
    public ClassType getType() {
        return type;
    }

    @Override
    public void setType(ClassType type) {
        Objects.requireNonNull(type);
        this.type = type;
    }

    public ClassSymbol getSuperClass() {
        return superClass;
    }

    public void setSuperClass(ClassSymbol superClass) {
        Objects.requireNonNull(superClass);
        this.superClass = superClass;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "(symbol (class " + className + "))";
    }

}
