package raylras.zen.model.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.Visitor;
import raylras.zen.model.parser.ZenScriptParser.*;
import raylras.zen.model.scope.Scope;
import raylras.zen.model.symbol.ClassSymbol;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolProvider;
import raylras.zen.util.Ranges;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class SymbolResolver {

    public static Collection<Symbol> lookupSymbol(ParseTree cst, CompilationUnit unit) {
        ParseTree statement = findCurrentStatement(cst);
        if (statement == null) {
            return Collections.emptyList();
        }
        SymbolVisitor visitor = new SymbolVisitor(unit, cst);
        visitor.visit(statement);
        return visitor.result;
    }

    private static ParseTree findCurrentStatement(ParseTree cst) {
        ParseTree current = cst;
        while (current != null) {
            if (current instanceof StatementContext
            || current instanceof ImportDeclarationContext) {
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
        public SymbolProvider visitImportDeclaration(ImportDeclarationContext ctx) {
            // FIXME: Not correct when import like crafttweaker.item.IItemStack.amount
            String qualifiedName = ctx.qualifiedName().getText();
            ClassSymbol classSymbol = unit.getEnv().getClassSymbolMap().get(qualifiedName);
            if (classSymbol != null) {
                result = List.of(classSymbol);
            }
            return SymbolProvider.EMPTY;
        }

        @Override
        public SymbolProvider visitSimpleNameExpr(SimpleNameExprContext ctx) {
            SymbolProvider possibles = lookupSymbol(ctx, ctx.simpleName().getText());
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
                        .filter(isSymbolNameEquals(ctx.simpleName().getText()));
            } else if (leftSymbol.getType() instanceof SymbolProvider type) {
                foundResults = type.withExpands(unit.getEnv())
                        .filter(isSymbolNameEquals(ctx.simpleName().getText()));
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

        private SymbolProvider lookupSymbol(ParseTree cst, String name) {
            SymbolProvider result;

            result= lookupLocalSymbol(cst, name);
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

        private SymbolProvider lookupLocalSymbol(ParseTree cst, String name) {
            Scope scope = unit.lookupScope(cst);
            if (scope != null) {
                return scope.filter(isSymbolNameEquals(name));
            } else {
                return SymbolProvider.EMPTY;
            }
        }

        private SymbolProvider lookupImportSymbol(String name) {
            return SymbolProvider.of(unit.getImports())
                    .filter(isSymbolNameEquals(name));
        }

        private SymbolProvider lookupGlobalSymbol(String name) {
            return SymbolProvider.of(unit.getEnv().getGlobalSymbols())
                    .filter(isSymbolNameEquals(name));
        }

        private static Predicate<Symbol> isSymbolNameEquals(String name) {
            return symbol -> name.equals(symbol.getName());
        }
    }

}
