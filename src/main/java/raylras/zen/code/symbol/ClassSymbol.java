package raylras.zen.code.symbol;

import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.tree.TreeNode;

import java.util.List;

public class ClassSymbol extends Symbol {

    public Symbol superClass;
    public List<Symbol> interfaces;

    public ClassSymbol(String name, LocalScope enclScope, TreeNode owner) {
        super(name, enclScope, owner);
    }

}
