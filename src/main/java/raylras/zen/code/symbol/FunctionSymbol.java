package raylras.zen.code.symbol;

import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.tree.TreeNode;
import raylras.zen.code.type.Type;

import java.util.List;

public class FunctionSymbol extends Symbol {

    public List<VariableSymbol> params;
    public Type returnType;
    public List<VariableSymbol> captures;

    public FunctionSymbol(String name, LocalScope enclScope, TreeNode owner) {
        super(name, enclScope, owner);
    }

}
