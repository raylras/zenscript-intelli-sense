package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.parser.ZenScriptParser.ConstructorDeclarationContext;
import raylras.zen.code.parser.ZenScriptParser.FunctionDeclarationContext;
import raylras.zen.code.parser.ZenScriptParser.ParameterContext;
import raylras.zen.code.symbol.VariableSymbol;

import java.util.List;
import java.util.stream.Collectors;

public class ParameterResolver extends Visitor<List<VariableSymbol>> {

    private final CompilationUnit unit;

    public ParameterResolver(CompilationUnit unit) {
        this.unit = unit;
    }

    public List<VariableSymbol> resolve(ParseTree node) {
        if (node != null) {
            return node.accept(this);
        } else {
            return null;
        }
    }

    @Override
    public List<VariableSymbol> visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        return toSymbols(ctx.parameter());
    }

    @Override
    public List<VariableSymbol> visitExpandFunctionDeclaration(ZenScriptParser.ExpandFunctionDeclarationContext ctx) {
        return toSymbols(ctx.parameter());
    }

    @Override
    public List<VariableSymbol> visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
        return toSymbols(ctx.parameter());
    }

    private List<VariableSymbol> toSymbols(List<ParameterContext> list) {
        return list.stream()
                .map(unit::getSymbol)
                .map(VariableSymbol.class::cast)
                .collect(Collectors.toList());
    }

}
