package raylras.zen.semantic.symbol;

import org.antlr.v4.runtime.tree.ParseTree;

public class VariableSymbol extends Symbol {

    private int modifier;

    public VariableSymbol(ParseTree node, String name) {
        super(node, name);
    }

    public VariableSymbol(ParseTree node, String name,  int modifier) {
        super(node, name);
        this.modifier = modifier;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

}
