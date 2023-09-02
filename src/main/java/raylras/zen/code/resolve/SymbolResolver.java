package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.SymbolProvider;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.MemberAccessExprContext;
import raylras.zen.code.parser.ZenScriptParser.SimpleNameExprContext;
import raylras.zen.code.parser.ZenScriptParser.StatementContext;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.Ranges;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

public class SymbolResolver {

    public static Collection<Symbol> lookupSymbol(ParseTree cst, CompilationUnit unit) {
        ParseTree statement = findCurrentStatement(cst);
        SymbolVisitor visitor = new SymbolVisitor(unit, cst);
        visitor.visit(statement);
        return visitor.result;
    }

    private static ParseTree findCurrentStatement(ParseTree cst) {
        ParseTree current = cst;
        while (current != null) {
            if (current instanceof StatementContext) {
                return current;
            } else {
                current = current.getParent();
            }
        }
        return null;
    }

    private static class SymbolVisitor extends Visitor<SymbolProvider> {
        private final CompilationUnit unit;
        private final ParseTree cst;
        private Collection<Symbol> result = Collections.emptyList();

        public SymbolVisitor(CompilationUnit unit, ParseTree cst) {
            this.unit = unit;
            this.cst = cst;
        }

        @Override
        public SymbolProvider visitSimpleNameExpr(SimpleNameExprContext ctx) {
            SymbolProvider possibles = lookupSymbol(ctx, ctx.simpleName());
            if (Ranges.contains(cst, ctx.simpleName())) {
                result = possibles.getSymbols();
            }
            return possibles;
        }

        @Override
        public SymbolProvider visitMemberAccessExpr(MemberAccessExprContext ctx) {
            SymbolProvider leftPossibles = visit(ctx.expression());
            if (leftPossibles.size() != 1) {
                return SymbolProvider.EMPTY;
            }

            Symbol leftSymbol = leftPossibles.getFirst();
            SymbolProvider foundResults;
            if (leftSymbol instanceof ClassSymbol classSymbol) {
                foundResults = classSymbol
                        .filter(Symbol::isStatic)
                        .filter(isSymbolNameEquals(ctx.simpleName()));
            } else if (leftSymbol.getType() instanceof SymbolProvider type) {
                foundResults = type.withExpands(unit.getEnv()).filter(isSymbolNameEquals(ctx.simpleName()));
            } else {
                foundResults = SymbolProvider.EMPTY;
            }
            if (Ranges.contains(cst, ctx.simpleName())) {
                result = foundResults.getSymbols();
            }
            return foundResults;
        }

        @Override
        protected SymbolProvider defaultResult() {
            return SymbolProvider.EMPTY;
        }

        private SymbolProvider lookupSymbol(ParseTree cst, ParseTree name) {
            return lookupSymbol(cst, name.getText());
        }

        private SymbolProvider lookupSymbol(ParseTree cst, String name) {
            Scope scope = unit.lookupScope(cst);
            if (scope != null) {
                return scope.filter(isSymbolNameEquals(name));
            } else {
                return SymbolProvider.EMPTY;
            }
        }

        private static Predicate<Symbol> isSymbolNameEquals(ParseTree name) {
            return symbol -> name.getText().equals(symbol.getName());
        }

        private static Predicate<Symbol> isSymbolNameEquals(String name) {
            return symbol -> name.equals(symbol.getName());
        }

    }

}
