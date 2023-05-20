package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;

public class OperatorSymbol extends FunctionSymbol{
    public OperatorSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit, false);
    }
}
