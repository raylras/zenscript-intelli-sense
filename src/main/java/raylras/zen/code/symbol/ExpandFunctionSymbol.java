package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ExpandFunctionSymbol extends FunctionSymbol {
    public ExpandFunctionSymbol(ParseTree owner, CompilationUnit unit, boolean isConstructor) {
        super(owner, unit, isConstructor);
    }


    public ClassSymbol getExpandTarget() {
        throw new NotImplementedException();
    }
}
