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
        if (node == null)
            return null;
        return node.accept(this);
    }

    private List<VariableSymbol> map(List<ParameterContext> list) {
        return list.stream()
                .map(unit::getSymbol)
                .map(VariableSymbol.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<VariableSymbol> visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        return map(ctx.parameter());
    }

    @Override
    public List<VariableSymbol> visitExpandFunctionDeclaration(ZenScriptParser.ExpandFunctionDeclarationContext ctx) {
        return map(ctx.parameter());
    }

    @Override
    public List<VariableSymbol> visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
        return map(ctx.parameter());
    }

}
