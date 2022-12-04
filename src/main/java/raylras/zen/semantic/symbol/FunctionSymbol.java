package raylras.zen.semantic.symbol;

import org.antlr.v4.runtime.tree.ParseTree;

public class FunctionSymbol extends Symbol {

    public FunctionSymbol(ParseTree node, String name) {
        super(node, name);
    }

}
