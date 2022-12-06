package raylras.zen.semantic.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.semantic.scope.BaseScope;
import raylras.zen.semantic.scope.Scope;
import raylras.zen.semantic.type.FunctionType;

import java.util.List;
import java.util.Objects;

public class FunctionSymbol extends Symbol<FunctionType> implements Scope {

    private String funcName;
    private List<VariableSymbol> parameters;
    private FunctionType type;
    private Scope scope;

    public FunctionSymbol(String name, Scope parentScope, ParseTree parseTree) {
        super(parentScope, parseTree);
        Objects.requireNonNull(name);
        this.funcName = name;
        this.scope = new BaseScope(name, parentScope, parseTree);
    }

    public List<VariableSymbol> getParameters() {
        return parameters;
    }

    public void setParameters(List<VariableSymbol> parameters) {
        Objects.requireNonNull(parameters);
        this.parameters = parameters;
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
        return funcName;
    }

    @Override
    public void setName(String name) {
        Objects.requireNonNull(name);
        this.funcName = name;
    }

    @Override
    public FunctionType getType() {
        return type;
    }

    @Override
    public void setType(FunctionType type) {
        Objects.requireNonNull(type);
        this.type = type;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        Objects.requireNonNull(scope);
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "(symbol (function " + funcName + "))";
    }

}
