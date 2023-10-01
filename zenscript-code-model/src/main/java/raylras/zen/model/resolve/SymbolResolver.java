package raylras.zen.model.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.Visitor;
import raylras.zen.model.parser.ZenScriptParser.*;
import raylras.zen.model.scope.Scope;
import raylras.zen.model.symbol.ClassSymbol;
import raylras.zen.model.symbol.ImportSymbol;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolProvider;
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

    private static class SymbolVisitor extends Visitor<SymbolProvider<? extends Symbol>> {
        final CompilationUnit unit;
        final ParseTree cst;
        Collection<? extends Symbol> result = Collections.emptyList();

        SymbolVisitor(CompilationUnit unit, ParseTree cst) {
            this.unit = unit;
            this.cst = cst;
        }

        @Override
        public SymbolProvider<? extends Symbol> visitQualifiedName(QualifiedNameContext ctx) {
            List<SimpleNameContext> simpleNames = ctx.simpleName();
            if (simpleNames.isEmpty()) {
                return SymbolProvider.EMPTY;
            }
            SymbolProvider<? extends Symbol> provider = lookupSymbol(ctx, simpleNames.get(0).getText());
            for (int i = 1; i < simpleNames.size(); i++) {
                provider = accessMember(provider, simpleNames.get(i).getText());
                if (Ranges.contains(cst, simpleNames.get(i))) {
                    result = provider.getSymbols();
                }
            }
            return provider;
        }

        @Override
        public SymbolProvider<? extends Symbol> visitSimpleNameExpr(SimpleNameExprContext ctx) {
            SymbolProvider<? extends Symbol> provider = lookupSymbol(ctx, ctx.simpleName().getText());
            if (Ranges.contains(cst, ctx.simpleName())) {
                result = provider.getSymbols();
            }
            return provider;
        }

        @Override
        public SymbolProvider<? extends Symbol> visitMemberAccessExpr(MemberAccessExprContext ctx) {
            SymbolProvider<? extends Symbol> provider = visit(ctx.expression());
            if (provider.size() != 1) {
                return SymbolProvider.EMPTY;
            }

            Symbol symbol = provider.getFirst();
            if (symbol instanceof ClassSymbol classSymbol) {
                provider = classSymbol
                        .filter(Symbol::isStatic)
                        .filter(isSymbolNameEquals(ctx.simpleName().getText()));
            } else if (symbol.getType() instanceof SymbolProvider<?> type) {
                provider = type.withExpands(unit.getEnv())
                        .filter(isSymbolNameEquals(ctx.simpleName().getText()));
            } else {
                provider = SymbolProvider.EMPTY;
            }
            if (Ranges.contains(cst, ctx.simpleName())) {
                result = provider.getSymbols();
            }
            return provider;
        }

        @Override
        protected SymbolProvider<Symbol> defaultResult() {
            return SymbolProvider.EMPTY;
        }

        SymbolProvider<? extends Symbol> lookupSymbol(ParseTree cst, String name) {
            SymbolProvider<? extends Symbol> result;

            result = lookupLocalSymbol(cst, name);
            if (result.isNotEmpty()) {
                return result;
            }

            result = lookupImportSymbol(name);
            if (result.isNotEmpty()) {
                return result;
            }

            result = lookupGlobalSymbol(name);
            if (result.isNotEmpty()) {
                return result;
            }

            return SymbolProvider.EMPTY;
        }

        SymbolProvider<Symbol> lookupLocalSymbol(ParseTree cst, String name) {
            Scope scope = unit.lookupScope(cst);
            if (scope != null) {
                return scope.filter(isSymbolNameEquals(name));
            } else {
                return SymbolProvider.EMPTY;
            }
        }

        SymbolProvider<ImportSymbol> lookupImportSymbol(String name) {
            return SymbolProvider.of(unit.getImports())
                    .filter(isSymbolNameEquals(name));
        }

        SymbolProvider<Symbol> lookupGlobalSymbol(String name) {
            return SymbolProvider.of(unit.getEnv().getGlobalSymbols())
                    .filter(isSymbolNameEquals(name));
        }

        SymbolProvider<Symbol> accessMember(SymbolProvider<? extends Symbol> left, String memberName) {
            return left.withExpands(unit.getEnv())
                    .filter(isSymbolNameEquals(memberName));
        }

        <T extends Symbol> Predicate<T> isSymbolNameEquals(String name) {
            return symbol -> name.equals(symbol.getName());
        }
    }

}
