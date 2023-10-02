package raylras.zen.model.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.Visitor;
import raylras.zen.model.parser.ZenScriptParser.*;
import raylras.zen.model.scope.Scope;
import raylras.zen.model.symbol.*;
import raylras.zen.util.Ranges;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public final class SymbolResolver {

    private static final List<Class<?>> ROOT_EXPRESSION_PARENTS = List.of(
            ImportDeclarationContext.class,
            ForeachStatementContext.class,
            ForeachVariableContext.class,
            WhileStatementContext.class,
            IfStatementContext.class,
            ExpressionStatementContext.class,
            ReturnStatementContext.class
    );

    private SymbolResolver() {
    }

    public static Collection<Symbol> lookupSymbol(ParseTree cst, CompilationUnit unit) {
        ParseTree expr = findRootExpression(cst);
        if (expr == null) {
            return Collections.emptyList();
        }
        SymbolVisitor visitor = new SymbolVisitor(unit, cst);
        visitor.visit(expr);
        return Collections.unmodifiableCollection(visitor.result);
    }

    private static ParseTree findRootExpression(ParseTree cst) {
        ParseTree current = cst;
        while (current != null) {
            ParseTree parent = current.getParent();
            if (parent != null && ROOT_EXPRESSION_PARENTS.contains(parent.getClass())) {
                return current;
            }
            current = parent;
        }
        return null;
    }

    private static class SymbolVisitor extends Visitor<SymbolProvider> {
        final CompilationUnit unit;
        final ParseTree cst;
        Collection<Symbol> result = Collections.emptyList();

        SymbolVisitor(CompilationUnit unit, ParseTree cst) {
            this.unit = unit;
            this.cst = cst;
        }

        @Override
        public SymbolProvider visitQualifiedName(QualifiedNameContext ctx) {
            List<SimpleNameContext> simpleNames = ctx.simpleName();
            if (simpleNames.isEmpty()) {
                return SymbolProvider.empty();
            }

            Collection<? extends Symbol> symbols = lookupToplevelPackageSymbol(simpleNames.get(0).getText());
            updateResult(symbols);
            for (int i = 1; i < simpleNames.size(); i++) {
                symbols = accessMember(symbols, simpleNames.get(i).getText());
                if (Ranges.contains(cst, simpleNames.get(i))) {
                    updateResult(symbols);
                }
            }
            return SymbolProvider.of(symbols);
        }

        @Override
        public SymbolProvider visitSimpleNameExpr(SimpleNameExprContext ctx) {
            Collection<Symbol> symbols = lookupSymbol(ctx, ctx.simpleName().getText());
            if (Ranges.contains(cst, ctx.simpleName())) {
                updateResult(symbols);
            }
            return SymbolProvider.of(symbols);
        }

        @Override
        public SymbolProvider visitMemberAccessExpr(MemberAccessExprContext ctx) {
            SymbolProvider provider = visit(ctx.expression());
            if (provider.getSymbols().size() != 1) {
                return SymbolProvider.empty();
            }

            Symbol symbol = provider.getSymbols().stream().findFirst().orElse(null);
            Collection<Symbol> symbols;
            if (symbol instanceof ClassSymbol classSymbol) {
                symbols = classSymbol.getSymbols().stream()
                        .filter(Symbol::isStatic)
                        .filter(isSymbolNameEquals(ctx.simpleName().getText()))
                        .toList();
            } else if (symbol.getType() instanceof SymbolProvider type) {
                symbols = type.withExpands(unit.getEnv()).getSymbols().stream()
                        .filter(isSymbolNameEquals(ctx.simpleName().getText()))
                        .toList();
            } else {
                symbols = Collections.emptyList();
            }
            if (Ranges.contains(cst, ctx.simpleName())) {
                updateResult(symbols);
            }
            return SymbolProvider.of(symbols);
        }

        @Override
        protected SymbolProvider defaultResult() {
            return SymbolProvider.empty();
        }

        void updateResult(Collection<? extends Symbol> result) {
            this.result = Collections.unmodifiableCollection(result);
        }

        Collection<Symbol> lookupSymbol(ParseTree cst, String name) {
            Collection<? extends Symbol> result;

            result = lookupLocalSymbol(cst, name);
            if (!result.isEmpty()) {
                return Collections.unmodifiableCollection(result);
            }

            result = lookupImportSymbol(name);
            if (!result.isEmpty()) {
                return Collections.unmodifiableCollection(result);
            }

            result = lookupGlobalSymbol(name);
            if (!result.isEmpty()) {
                return Collections.unmodifiableCollection(result);
            }

            return Collections.emptyList();
        }

        Collection<Symbol> lookupLocalSymbol(ParseTree cst, String name) {
            Scope scope = unit.lookupScope(cst);
            if (scope != null) {
                return scope.getSymbols().stream()
                        .filter(isSymbolNameEquals(name))
                        .toList();
            } else {
                return Collections.emptyList();
            }
        }

        Collection<ImportSymbol> lookupImportSymbol(String name) {
            return unit.getImports().stream()
                    .filter(isSymbolNameEquals(name))
                    .toList();
        }

        Collection<Symbol> lookupGlobalSymbol(String name) {
            return unit.getEnv().getGlobalSymbols().stream()
                    .filter(isSymbolNameEquals(name))
                    .toList();
        }

        Collection<PackageSymbol> lookupToplevelPackageSymbol(String name) {
            return unit.getEnv().getToplevelPackageSymbol().stream()
                    .filter(isSymbolNameEquals(name))
                    .toList();
        }

        Collection<Symbol> accessMember(Collection<? extends Symbol> symbolSpace, String memberName) {
            return symbolSpace.stream()
                    .filter(isSymbolNameEquals(memberName))
                    .map(Symbol.class::cast)
                    .toList();
        }

        <T extends Symbol> Predicate<T> isSymbolNameEquals(String name) {
            return symbol -> name.equals(symbol.getName());
        }
    }

}
