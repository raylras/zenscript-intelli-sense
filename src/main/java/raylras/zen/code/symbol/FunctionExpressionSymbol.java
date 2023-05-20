package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.type.Type;
import raylras.zen.util.SymbolUtils;

public class FunctionExpressionSymbol extends FunctionSymbol {
    public FunctionExpressionSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit, false);
    }

    @Override
    public String getName() {
        String inferredName = SymbolUtils.inferExpressionName((ZenScriptParser.ExpressionContext) getOwner());
        if (inferredName == null) {
            return "<anonymous function>";
        }
        return inferredName;
    }

    @Override
    public ZenSymbolKind getKind() {
        return ZenSymbolKind.FUNCTION_EXPRESSION;
    }
}
