package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.parser.ZenScriptParser.ConstructorDeclarationContext;
import raylras.zen.code.parser.ZenScriptParser.FunctionDeclarationContext;
import raylras.zen.code.symbol.ParameterSymbol;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class FormalParameterResolver {

    private FormalParameterResolver() {}

    public static List<ParameterSymbol> getFormalParameterList(ParseTree cst, CompilationUnit unit) {
        Objects.requireNonNull(cst);
        Objects.requireNonNull(unit);
        return cst.accept(new FormalParameterVisitor(unit));
    }

    private static final class FormalParameterVisitor extends Visitor<List<ParameterSymbol>> {
        private final CompilationUnit unit;

        public FormalParameterVisitor(CompilationUnit unit) {
            this.unit = unit;
        }

        @Override
        public List<ParameterSymbol> visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            return visitFormalParameterList(ctx.formalParameterList());
        }

        @Override
        public List<ParameterSymbol> visitExpandFunctionDeclaration(ZenScriptParser.ExpandFunctionDeclarationContext ctx) {
            return visitFormalParameterList(ctx.formalParameterList());
        }

        @Override
        public List<ParameterSymbol> visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            return visitFormalParameterList(ctx.formalParameterList());
        }

        @Override
        public List<ParameterSymbol> visitFormalParameterList(ZenScriptParser.FormalParameterListContext ctx) {
            return ctx.formalParameter().stream()
                    .map(unit::getSymbol)
                    .map(ParameterSymbol.class::cast)
                    .collect(Collectors.toList());
        }
    }

}
