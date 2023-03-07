package raylras.zen.code.symbol;

import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.tree.TreeNode;

public abstract class Symbol {

    public String name;
    public LocalScope enclScope;
    public TreeNode owner;

    public Symbol(String name, LocalScope enclScope, TreeNode owner) {
        this.name = name;
        this.enclScope = enclScope;
        this.owner = owner;
    }

    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitSymbol(this);
    }

    public interface Visitor<R> {
        R visitClassSymbol(ClassSymbol symbol);
        R visitFunctionSymbol(FunctionSymbol symbol);
        R visitVariableSymbol(VariableSymbol symbol);
        R visitSymbol(Symbol symbol);
    }

}
