package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.NonRecursionVisitor;
import raylras.zen.code.common.MemberProvider;
import raylras.zen.util.ResolveUtils;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.symbol.*;
import raylras.zen.code.type.Type;
import raylras.zen.util.*;

import java.util.*;
import java.util.stream.Collectors;

public final class SymbolResolver {

    public static Optional<Symbol> lookupSimpleSymbol(ParseTree cst, CompilationUnit unit, boolean expandImport) {
        return lookupSymbols(cst, unit, expandImport)
                .stream().filter(it -> it.getKind() != Symbol.Kind.FUNCTION)
                .findFirst();
    }

    public static Optional<Symbol> lookupSimpleExpression(ZenScriptParser.ExpressionContext expression, CompilationUnit unit, boolean expandImport) {
        if (expression instanceof ZenScriptParser.MemberAccessExprContext memberAccessExpr) {
            return SymbolResolver.lookupSimpleSymbol(memberAccessExpr.simpleName(), unit, true);
        } else if (expression instanceof ZenScriptParser.SimpleNameExprContext simpleNameExpr) {
            return SymbolResolver.lookupSimpleSymbol(simpleNameExpr.simpleName(), unit, true);
        }
        return Optional.empty();
    }


    public static List<Symbol> lookupSymbols(ParseTree cst, CompilationUnit unit, boolean expandImport) {
        List<Symbol> result = lookupSymbols(cst, unit);
        if (expandImport) {
            Optional<ImportSymbol> importSymbol = result.stream()
                    .filter(it -> it.getKind() == Symbol.Kind.IMPORT && it instanceof ImportSymbol)
                    .map(it -> (ImportSymbol) it).findFirst();
            if (importSymbol.isPresent()) {
                return importSymbol.get().getTargets();
            }
        }
        return result;
    }

    public static List<Symbol> lookupSymbols(ParseTree cst, CompilationUnit unit) {
        if (cst.getParent() == null) {
            return Collections.emptyList();
        }
        SymbolResolverVisitor visitor = new SymbolResolverVisitor(unit, cst);
        return cst.getParent().accept(visitor);
    }

    private static class SymbolResolverVisitor extends NonRecursionVisitor<List<Symbol>> {

        private final CompilationUnit unit;

        private final ParseTree cst;

        public SymbolResolverVisitor(CompilationUnit unit, ParseTree cst) {
            this.unit = unit;
            this.cst = cst;
        }


        private List<Symbol> doResolveQualifiedName(List<ZenScriptParser.SimpleNameContext> names, int lastNameIndex) {
            // first
            Symbol symbol = ResolveUtils.getRootPackage(unit, names.get(0).getText());
            // center
            for (int i = 1; i < lastNameIndex; i++) {
                if ((!(symbol instanceof MemberProvider memberProvider))) {
                    return Collections.emptyList();
                }
                String simpleName = names.get(i).getText();
                Optional<Symbol> found = memberProvider.getMembers()
                        .stream()
                        .filter(it -> it.getName().equals(simpleName))
                        .findFirst();
                symbol = found.orElse(null);
            }
            // last

            String simpleName = names.get(lastNameIndex).getText();

            if ((!(symbol instanceof MemberProvider memberProvider))) {
                return Collections.emptyList();
            }
            return memberProvider.getMembers()
                    .stream()
                    .filter(it -> it.getName().equals(simpleName))
                    .collect(Collectors.toList());

        }

        @Override
        public List<Symbol> visitDefault(ParseTree tree) {
            return Collections.emptyList();
        }

        @Override
        public List<Symbol> visitQualifiedName(ZenScriptParser.QualifiedNameContext ctx) {
            List<ZenScriptParser.SimpleNameContext> names = ctx.simpleName();
            if (names.isEmpty()) {
                return Collections.emptyList();
            }
            if (names.size() == 1) {
                // resolve directly
                return ResolveUtils.lookupSymbols(unit, ctx, names.get(0).getText());
            }


            for (int i = 0; i < names.size(); i++) {
                if (names.get(i) == cst) {
                    return doResolveQualifiedName(names, i + 1);
                }
            }
            return Collections.emptyList();
        }

        @Override
        public List<Symbol> visitSimpleNameExpr(ZenScriptParser.SimpleNameExprContext ctx) {
            // data.foo();
            // ¯¯¯¯
            return ResolveUtils.lookupSymbols(unit, ctx, ctx.simpleName().getText());
        }

        @Override
        public List<Symbol> visitMemberAccessExpr(ZenScriptParser.MemberAccessExprContext ctx) {
            // data.foo;
            //      ¯¯¯
            String simpleName = null;
            if (ctx.simpleName() != null) {
                simpleName = ctx.simpleName().getText();
            } else if (ctx.STRING_LITERAL() != null) {
                String literalString = ctx.STRING_LITERAL().getText();
                if (literalString.length() >= 2) {
                    simpleName = literalString.substring(1, literalString.length() - 1);
                }
            }
            if (simpleName == null) {
                return Collections.emptyList();
            }
            List<Symbol> symbols = new ArrayList<>();
            Optional<Symbol> leftSymbol = lookupSimpleExpression(ctx.expression(), unit, true);
            Type leftType = TypeResolver.getType(ctx.expression(), unit);
            if (leftSymbol.isPresent() && leftSymbol.get() instanceof MemberProvider memberProvider) {
                symbols.addAll(memberProvider.getMembers());
                // kind == class means static access
                if(leftSymbol.get().getKind() == Symbol.Kind.CLASS) {
                    return symbols.stream().filter(it -> it.isModifiedBy(Symbol.Modifier.STATIC))
                            .collect(Collectors.toList());
                }
            }
            if (leftType != null) {
                if (leftSymbol.isEmpty()) {
                    List<Symbol> members = Symbols.getMembersByName(leftType, simpleName, Symbol.class);
                    symbols.addAll(members);
                }
                List<OperatorFunctionSymbol> operators = Operators.find(leftType, Operator.MEMBER_GET, Operator.MEMBER_SET);
                symbols.addAll(operators);
            }


            return symbols;

        }

        @Override
        public List<Symbol> visitMemberIndexExpr(ZenScriptParser.MemberIndexExprContext ctx) {
            // data["foo"];
            //       ¯¯¯
            ZenScriptParser.ExpressionContext indexExpr = ctx.index;
            // only process with literal index
            if (!(indexExpr instanceof ZenScriptParser.LiteralExprContext literal)) {
                return Collections.emptyList();
            }
            Type leftType = TypeResolver.getType(ctx.left, unit);
            if (leftType == null) {
                return Collections.emptyList();
            }

            List<OperatorFunctionSymbol> operators = Operators.find(leftType, Operator.INDEX_GET, Operator.INDEX_SET);
            return new ArrayList<>(operators);
        }


        // below are for operators, all range are for operator
        @Override
        public List<Symbol> visitIntRangeExpr(ZenScriptParser.IntRangeExprContext ctx) {
            Type leftType = TypeResolver.getType(ctx.expression(0), unit);
            if (leftType == null) {
                return Collections.emptyList();
            }
            List<OperatorFunctionSymbol> operators = Operators.find(leftType, Operator.RANGE);
            return new ArrayList<>(operators);
        }

        @Override
        public List<Symbol> visitBinaryExpr(ZenScriptParser.BinaryExprContext ctx) {
            Type leftType = TypeResolver.getType(ctx.left, unit);
            if (leftType == null) {
                return Collections.emptyList();
            }
            Operator operator = Operators.literal(ctx.op.getText(), 2);
            List<OperatorFunctionSymbol> operators = Operators.find(leftType, operator);
            return new ArrayList<>(operators);
        }

        @Override
        public List<Symbol> visitCompareExpr(ZenScriptParser.CompareExprContext ctx) {
            Type leftType = TypeResolver.getType(ctx.left, unit);

            if (leftType == null) {
                return Collections.emptyList();
            }
            List<OperatorFunctionSymbol> operators = Operators.find(leftType, Operator.COMPARE);
            List<Symbol> symbols = new ArrayList<>(operators);

            if (ctx.EQUAL() != null || ctx.NOT_EQUAL() != null) {
                symbols.addAll(Operators.find(leftType, Operator.EQUALS));
            }
            return symbols;
        }

        @Override
        public List<Symbol> visitTypeCastExpr(ZenScriptParser.TypeCastExprContext ctx) {
            Type leftType = TypeResolver.getType(ctx.expression(), unit);
            if (leftType == null) {
                return Collections.emptyList();
            }
            List<OperatorFunctionSymbol> operators = Operators.find(leftType, Operator.AS);
            return new ArrayList<>(operators);
        }

        @Override
        public List<Symbol> visitInstanceOfExpr(ZenScriptParser.InstanceOfExprContext ctx) {
            Type leftType = TypeResolver.getType(ctx.left, unit);
            if (leftType == null) {
                return Collections.emptyList();
            }
            List<OperatorFunctionSymbol> operators = Operators.find(leftType, Operator.AS);
            return new ArrayList<>(operators);
        }

        @Override
        public List<Symbol> visitUnaryExpr(ZenScriptParser.UnaryExprContext ctx) {
            Type leftType = TypeResolver.getType(ctx.expression(), unit);
            if (leftType == null) {
                return Collections.emptyList();
            }
            if (ctx.NOT() != null) {
                return new ArrayList<>(Operators.find(leftType, Operator.NOT));
            } else if (ctx.SUB() != null) {
                return new ArrayList<>(Operators.find(leftType, Operator.NEG));
            }

            return Collections.emptyList();
        }

        @Override
        public List<Symbol> visitForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
            Type iterableType = TypeResolver.getType(ctx.expression(), unit);
            if (iterableType == null) {
                return Collections.emptyList();
            }
            List<OperatorFunctionSymbol> operators = Operators.find(iterableType, Operator.ITERATOR);
            return new ArrayList<>(operators);
        }


    }

}
