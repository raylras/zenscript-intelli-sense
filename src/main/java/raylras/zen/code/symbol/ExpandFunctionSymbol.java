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


    @Override
    protected List<VariableSymbol> resolveParams() {
        List<VariableSymbol> params = super.resolveParams();
        return params.subList(1, params.size());
    }

    public Type getExpandTarget() {
        return super.resolveParams().get(0).getType();
    }

    @Override
    public ZenSymbolKind getKind() {
        return ZenSymbolKind.EXPAND_FUNCTION;
    }

}
