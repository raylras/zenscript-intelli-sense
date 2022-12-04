package raylras.zen.semantic.symbol;

import org.antlr.v4.runtime.tree.ParseTree;

public class ClassSymbol extends Symbol {

    public ClassSymbol(ParseTree node, String name) {
        super(node, name);
    }

}
