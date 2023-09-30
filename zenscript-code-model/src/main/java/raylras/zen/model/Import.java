package raylras.zen.model;

import raylras.zen.model.symbol.Symbol;

import java.util.List;

public record Import(String qualifiedName) {
    public List<Symbol> targets(CompilationEnvironment env) {
        return env.getSymbolTree().get(qualifiedName());
    }
}
