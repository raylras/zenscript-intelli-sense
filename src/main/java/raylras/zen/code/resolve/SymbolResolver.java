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
import raylras.zen.code.symbol.*;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Ranges;
import raylras.zen.util.Symbols;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SymbolResolver {


    public static Collection<Symbol> lookupSymbol(ParseTree cst, CompilationUnit unit) {
        return lookupSymbol(cst, unit, true);
    }

    public static Collection<Symbol> lookupSymbol(ParseTree cst, CompilationUnit unit, boolean expandImport) {
        ParseTree owner = CSTNodes.findParentOfTypes(cst,
                StatementContext.class,
                ZenScriptParser.QualifiedNameContext.class,
                ZenScriptParser.InitializerContext.class,
                ZenScriptParser.DefaultValueContext.class
        );

        if (owner == null) {
            return Collections.emptyList();
        }
        SymbolVisitor visitor = new SymbolVisitor(unit, cst, expandImport);
        visitor.visit(owner);
        return visitor.result;
    }

    private static class SymbolVisitor extends Visitor<SymbolProvider> {
        private final CompilationUnit unit;
        private final ParseTree cst;
        private final boolean expandImport;
        private Collection<Symbol> result = Collections.emptyList();

        public SymbolVisitor(CompilationUnit unit, ParseTree cst, boolean expandImport) {
            this.unit = unit;
            this.cst = cst;
            this.expandImport = expandImport;
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
            if(scope == null) {
                return SymbolProvider.EMPTY;
            }
            List<Symbol> result = scope.lookupSymbols(name);

            if (result.isEmpty()) {
                return SymbolProvider.of(Symbols.lookupGlobalSymbols(unit, name));
            }
            if (expandImport) {
                result = result.stream().flatMap(it -> {
                    // try to expand import
                    Collection<Symbol> importTargets = tryExpandImport(it);
                    if (!importTargets.isEmpty()) {
                        return importTargets.stream();
                    }
                    return Stream.of(it);
                }).collect(Collectors.toList());
            }
            return SymbolProvider.of(result);
        }

        private Collection<Symbol> tryExpandImport(Symbol symbol) {
            if (symbol instanceof ImportSymbol && symbol instanceof ParseTreeLocatable locatable && locatable.getCst() instanceof ZenScriptParser.ImportDeclarationContext importCtx) {
                Collection<Symbol> importTargets = this.visit(importCtx.qualifiedName()).getSymbols();
                if (!importTargets.isEmpty()) {
                    return importTargets;
                }
            }
            return Collections.emptyList();
        }

        private static Predicate<Symbol> isSymbolNameEquals(ParseTree name) {
            return symbol -> name.getText().equals(symbol.getName());
        }

        private static Predicate<Symbol> isSymbolNameEquals(String name) {
            return symbol -> name.equals(symbol.getName());
        }

    }

}
