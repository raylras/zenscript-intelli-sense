package raylras.zen.code.type.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.parser.ZenScriptParser.ConstructorDeclarationContext;
import raylras.zen.code.parser.ZenScriptParser.FunctionDeclarationContext;
import raylras.zen.code.symbol.VariableSymbol;

import java.util.List;
import java.util.stream.Collectors;

public class ParamsResolver extends Visitor<List<VariableSymbol>> {

    private final CompilationUnit unit;

    public ParamsResolver(CompilationUnit unit) {
        this.unit = unit;
    }

    public List<VariableSymbol> resolve(ParseTree node) {
        if (node == null)
            return null;
        return node.accept(this);
    }

    @Override
    public List<VariableSymbol> visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        return ctx.parameter().stream()
            .map(unit::<VariableSymbol>getSymbol)
            .collect(Collectors.toList());
    }

    @Override
    public List<VariableSymbol> visitExpandFunctionDeclaration(ZenScriptParser.ExpandFunctionDeclarationContext ctx) {
        return ctx.parameter().stream()
            .map(unit::<VariableSymbol>getSymbol)
            .collect(Collectors.toList());
    }

    @Override
    public List<VariableSymbol> visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
        return ctx.parameter().stream()
            .map(unit::<VariableSymbol>getSymbol)
            .collect(Collectors.toList());
    }

    @Override
    public List<VariableSymbol> visitFunctionExpr(ZenScriptParser.FunctionExprContext ctx) {
        return ctx.parameter().stream()
            .map(unit::<VariableSymbol>getSymbol)
            .collect(Collectors.toList());
    }
}
