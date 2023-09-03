package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.SymbolProvider;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.parser.ZenScriptParser.MemberAccessExprContext;
import raylras.zen.code.parser.ZenScriptParser.SimpleNameExprContext;
import raylras.zen.code.parser.ZenScriptParser.StatementContext;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.PackageSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Ranges;
import raylras.zen.util.Symbols;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class SymbolResolver {

    public static Collection<Symbol> lookupSymbol(ParseTree cst, CompilationUnit unit) {
        ParseTree owner = CSTNodes.findParentOfTypes(cst,
                StatementContext.class,
                ZenScriptParser.QualifiedNameContext.class,
                ZenScriptParser.InitializerContext.class,
                ZenScriptParser.DefaultValueContext.class
        );

        if (owner == null) {
            return Collections.emptyList();
        }
        SymbolVisitor visitor = new SymbolVisitor(unit, cst);
        visitor.visit(owner);
        return visitor.result;
    }
    public static Collection<Symbol> getSymbol(ParseTree cst, CompilationUnit unit) {
        SymbolVisitor visitor = new SymbolVisitor(unit, null);
        return visitor.visit(cst).getSymbols();
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
            SymbolProvider foundResults = findMembers(ctx.simpleName(), leftPossibles);
            if (Ranges.contains(cst, ctx.simpleName())) {
                result = foundResults.getSymbols();
            }
            return findMembers(ctx.simpleName(), leftPossibles);
        }

        private SymbolProvider findMembers(ZenScriptParser.SimpleNameContext simpleName, SymbolProvider leftPossibles) {
            if (leftPossibles.size() != 1) {
                return SymbolProvider.EMPTY;
            }
            Symbol leftSymbol = leftPossibles.getFirst();
            SymbolProvider foundResults;
            if (leftSymbol instanceof PackageSymbol packageSymbol) {
                foundResults = packageSymbol
                        .filter(isSymbolNameEquals(simpleName));
            } else if (leftSymbol instanceof ClassSymbol classSymbol) {
                foundResults = classSymbol
                        .filter(Symbol::isStatic)
                        .filter(isSymbolNameEquals(simpleName));
            } else if (leftSymbol.getType() instanceof SymbolProvider type) {
                foundResults = type.withExpands(unit.getEnv()).filter(isSymbolNameEquals(simpleName));
            } else {
                foundResults = SymbolProvider.EMPTY;
            }
            return foundResults;
        }

        @Override
        public SymbolProvider visitQualifiedName(ZenScriptParser.QualifiedNameContext ctx) {
            List<ZenScriptParser.SimpleNameContext> simpleNames = ctx.simpleName();
            if (simpleNames.isEmpty()) {
                return SymbolProvider.EMPTY;
            }

            SymbolProvider possibles = lookupSymbol(ctx, simpleNames.get(0));
            if (Ranges.contains(cst, simpleNames.get(0))) {
                result = possibles.getSymbols();
            }
            for (int i = 1; i < simpleNames.size(); i++) {
                possibles = findMembers(simpleNames.get(i), possibles);
                if (Ranges.contains(cst, simpleNames.get(i))) {
                    result = possibles.getSymbols();
                }
            }
            return possibles;
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

            SymbolProvider result = SymbolProvider.EMPTY;
            while (scope != null) {
                result = result.orElse(scope.filter(isSymbolNameEquals(name)));
                scope = scope.getParent();
            }
            return result.orElse(() -> Symbols.lookupGlobalSymbols(unit, name));
        }


        private static Predicate<Symbol> isSymbolNameEquals(ParseTree name) {
            return symbol -> name.getText().equals(symbol.getName());
        }

        private static Predicate<Symbol> isSymbolNameEquals(String name) {
            return symbol -> name.equals(symbol.getName());
        }

    }

}
