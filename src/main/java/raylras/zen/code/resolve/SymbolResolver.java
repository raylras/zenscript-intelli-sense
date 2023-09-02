package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.MemberProvider;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.MemberAccessExprContext;
import raylras.zen.code.parser.ZenScriptParser.SimpleNameExprContext;
import raylras.zen.code.parser.ZenScriptParser.StatementContext;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.Ranges;

import java.util.List;
import java.util.function.Predicate;

public class SymbolResolver {

    public static List<Symbol> getSymbol(ParseTree cst, CompilationUnit unit) {
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

    private static class SymbolVisitor extends Visitor<MemberProvider> {
        private final CompilationUnit unit;
        private final ParseTree cst;
        List<Symbol> result = List.of();

        public SymbolVisitor(CompilationUnit unit, ParseTree cst) {
            this.unit = unit;
            this.cst = cst;
        }

        private static Predicate<Symbol> isSymbolNameEquals(ParseTree name) {
            return symbol -> name.getText().equals(symbol.getName());
        }

        private static Predicate<Symbol> isSymbolNameEquals(String name) {
            return symbol -> name.equals(symbol.getName());
        }

        @Override
        public MemberProvider visitSimpleNameExpr(SimpleNameExprContext ctx) {
            MemberProvider possibles = lookupSymbol(ctx, ctx.simpleName().getText());
            if (Ranges.contains(cst, ctx.simpleName())) {
                result = possibles.getMembers();
            }
            return possibles;
        }

        @Override
        public MemberProvider visitMemberAccessExpr(MemberAccessExprContext ctx) {
            MemberProvider leftPossibles = visit(ctx.expression());
            if (leftPossibles.size() != 1) {
                return MemberProvider.EMPTY;
            }

            Symbol leftSymbol = leftPossibles.getMember(0);
            MemberProvider foundResults;
            if (leftSymbol instanceof ClassSymbol classSymbol) {
                foundResults = classSymbol
                        .filter(Symbol::isStatic)
                        .filter(isSymbolNameEquals(ctx.simpleName()));
            } else if (leftSymbol.getType() instanceof MemberProvider type) {
                foundResults = type.withExpandMembers(unit.getEnv()).filter(isSymbolNameEquals(ctx.simpleName()));
            } else {
                foundResults = MemberProvider.EMPTY;
            }
            if (Ranges.contains(cst, ctx.simpleName())) {
                result = foundResults.getMembers();
            }
            return foundResults;
        }

        @Override
        protected MemberProvider defaultResult() {
            return MemberProvider.EMPTY;
        }

        private MemberProvider lookupSymbol(ParseTree cst, String name) {
            Scope scope = unit.lookupScope(cst);
            if (scope != null) {
                return scope.filter(isSymbolNameEquals(name));
            } else {
                return MemberProvider.EMPTY;
            }
        }
    }

}
