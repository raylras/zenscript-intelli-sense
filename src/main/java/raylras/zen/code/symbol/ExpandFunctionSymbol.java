package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.type.Type;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class ExpandFunctionSymbol extends FunctionSymbol {
    public ExpandFunctionSymbol(ParseTree owner, CompilationUnit unit, boolean isConstructor) {
        super(owner, unit, isConstructor);
    }


    public ClassSymbol getExpandTarget() {
        // TODO: is it able to expand no class?(eg: array)
        Type firstType = super.getParams().get(0).getType();
        return (ClassSymbol) firstType.lookupSymbol(unit);
    }

    @Override
    public ZenSymbolKind getKind() {
        return ZenSymbolKind.EXPAND_FUNCTION;
    }

    @Override
    public List<VariableSymbol> getParams() {
        List<VariableSymbol> allParams = super.getParams();
        return allParams.subList(1, allParams.size());
    }
}
