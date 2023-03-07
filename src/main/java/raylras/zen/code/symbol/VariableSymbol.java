package raylras.zen.code.symbol;

import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.tree.TreeNode;

public class VariableSymbol extends Symbol {

    public VariableSymbol(String name, LocalScope enclScope, TreeNode owner) {
        super(name, enclScope, owner);
    }

}
