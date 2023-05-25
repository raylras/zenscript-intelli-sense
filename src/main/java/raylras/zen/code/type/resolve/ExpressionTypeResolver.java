package raylras.zen.code.type.resolve;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.*;
import raylras.zen.util.MemberUtils;
import raylras.zen.util.Tuple;
import raylras.zen.util.TypeUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpressionTypeResolver extends Visitor<Type> {

    private final CompilationUnit unit;

    public ExpressionTypeResolver(CompilationUnit unit) {
        this.unit = unit;
    }

    public Type resolve(ParseTree node) {
        if (node == null)
            return null;
        return node.accept(this);
    }

    @Override
    public Type visitLocalAccessExpr(LocalAccessExprContext ctx) {
        Scope scope = unit.lookupScope(ctx);
        String name = NameResolver.resolveName(ctx);
        Symbol symbol = scope.lookupSymbol(Symbol.class, name);
        if (symbol == null) {
            symbol = unit.environment().findSymbol(Symbol.class, name);
        }
        if (symbol != null) {
            return symbol.getType();
        }
        return new ErrorType(name);
    }

    @Override
    public Type visitCallExpr(CallExprContext ctx) {
        // if left expr is member or local access, it is possible to have overload.

        if (ctx.Left instanceof MemberAccessExprContext) {
            MemberAccessExprContext accessExpr = (MemberAccessExprContext) ctx.Left;

            Tuple<Boolean, Type> ownerType = MemberUtils.resolveQualifierTarget(unit, accessExpr.Left);
            if (!TypeUtils.isValidType(ownerType.second))
                return null;

            List<Type> argumentTypes = TypeUtils.getArgumentTypes(this, ctx);
            String simpleName = NameResolver.resolveName(accessExpr.simpleName());
            FunctionType functionType = MemberUtils.selectFunction(unit.environment(), ownerType.second, ownerType.first, simpleName, argumentTypes);
            if (functionType != null) {
                return functionType.returnType;
            }
            return null;
        }

        if (ctx.Left instanceof LocalAccessExprContext) {
            LocalAccessExprContext accessExpr = (LocalAccessExprContext) ctx.Left;

            List<Type> argumentTypes = TypeUtils.getArgumentTypes(this, ctx);

            String simpleName = NameResolver.resolveName(accessExpr.simpleName());

            Scope scope = unit.lookupScope(accessExpr);
            List<FunctionSymbol> localFunctions = scope.lookupSymbols(FunctionSymbol.class,
                it -> Objects.equals(it.getName(), simpleName)
            );


            if (!localFunctions.isEmpty()) {
                FunctionType functionType = MemberUtils.selectFunction(unit.environment(), argumentTypes, localFunctions, null);
                if (functionType != null) {
                    return functionType.returnType;
                }
            } else {
                Symbol global = scope.lookupSymbol(Symbol.class, simpleName);
                if (global == null) {
                    global = unit.environment().findSymbol(Symbol.class, simpleName);
                }
                FunctionType functionType = MemberUtils.selectFunction(unit.environment(), argumentTypes, Collections.emptyList(), global);
                if (functionType != null) {
                    return functionType.returnType;
                }

            }


        }

        Type leftType = ctx.Left.accept(this);
        FunctionType functionType = TypeUtils.extractFunctionType(leftType);
        if (functionType != null) {
            return functionType.returnType;
        }
        return null;
    }

    @Override
    public Type visitThisExpr(ThisExprContext ctx) {
        ParserRuleContext tree = ctx.getParent();
        while (!(tree instanceof ClassDeclarationContext)) {
            if (tree == null) {
                return null;
            }
            tree = tree.getParent();
        }
        Symbol symbol = unit.getSymbol(tree);
        if (symbol instanceof ClassSymbol) {
            return symbol.getType();
        }
        return null;
    }


    @Override
    public Type visitMemberAccessExpr(MemberAccessExprContext ctx) {
        Tuple<Boolean, Type> ownerType = MemberUtils.resolveQualifierTarget(unit, ctx.Left);
        if (!TypeUtils.isValidType(ownerType.second))
            return null;
        String simpleName = NameResolver.resolveName(ctx.simpleName());

        if (simpleName == null)
            return ownerType.second;

        Symbol member = MemberUtils.findMember(unit.environment(), ownerType.second, ownerType.first, simpleName);

        if (member != null) {
            return member.getType();
        }
        return null;
    }

    @Override
    public Type visitArrayIndexExpr(ArrayIndexExprContext ctx) {
        Type leftType = ctx.Left.accept(this);
        if (!TypeUtils.isValidType(leftType)) {
            return null;
        }

        if (leftType.getKind() == Type.Kind.ARRAY) {
            return ((ArrayType) leftType).elementType;
        }


        if (leftType.getKind() == Type.Kind.MAP) {
            return ((MapType) leftType).valueType;
        }

        if (leftType.getKind() == Type.Kind.LIST) {
            return ((ListType) leftType).elementType;
        }

        // TODO: Operator override
        return null;
    }

    @Override
    public Type visitUnaryExpr(UnaryExprContext ctx) {
        return super.visitUnaryExpr(ctx);
        // TODO: Operator override
    }

    @Override
    public Type visitBinaryExpr(BinaryExprContext ctx) {
        return super.visitBinaryExpr(ctx);
        // TODO: Operator override
    }

    @Override
    public Type visitTernaryExpr(TernaryExprContext ctx) {
        return super.visitTernaryExpr(ctx);
        // TODO: commonSuperClass
    }

    @Override
    public Type visitAssignmentExpr(AssignmentExprContext ctx) {
        return ctx.Left.accept(this);
    }

    @Override
    public Type visitIntRangeExpr(IntRangeExprContext ctx) {
        return IntRangeType.INSTANCE;
    }

    @Override
    public Type visitFunctionExpr(FunctionExprContext ctx) {
        LiteralTypeResolver resolver = new LiteralTypeResolver(unit);
        Type returnType = resolver.resolve(ctx.typeLiteral());
        List<Type> parameterTypes = ctx.parameter()
            .stream()
            .map(ParameterContext::typeLiteral)
            .map(resolver::resolve)
            .collect(Collectors.toList());
        return new FunctionType(parameterTypes, returnType);
    }

    @Override
    public Type visitArrayInitializerExpr(ArrayInitializerExprContext ctx) {
        return super.visitArrayInitializerExpr(ctx);
    }

    @Override
    public Type visitMapInitializerExpr(MapInitializerExprContext ctx) {
        return super.visitMapInitializerExpr(ctx);
    }

    @Override
    public Type visitParensExpr(ParensExprContext ctx) {
        return ctx.expression().accept(this);
    }

    @Override
    public Type visitTypeCastExpr(TypeCastExprContext ctx) {
        return new LiteralTypeResolver(unit).resolve(ctx.typeLiteral());
    }

    @Override
    public Type visitTrueLiteralExpr(TrueLiteralExprContext ctx) {
        return BoolType.INSTANCE;
    }

    @Override
    public Type visitFalseLiteralExpr(FalseLiteralExprContext ctx) {
        return BoolType.INSTANCE;
    }

    @Override
    public Type visitStringLiteralExpr(StringLiteralExprContext ctx) {
        return StringType.INSTANCE;
    }

    @Override
    public Type visitNullLiteralExpr(NullLiteralExprContext ctx) {
        return NullType.INSTANCE;
    }

    @Override
    public Type visitIntLiteralExpr(IntLiteralExprContext ctx) {
        return IntType.INSTANCE;
    }

    @Override
    public Type visitDoubleLiteralExpr(DoubleLiteralExprContext ctx) {
        return DoubleType.INSTANCE;
    }

    @Override
    public Type visitFloatLiteralExpr(FloatLiteralExprContext ctx) {
        return FloatType.INSTANCE;
    }

    @Override
    public Type visitLongLiteralExpr(LongLiteralExprContext ctx) {
        return LongType.INSTANCE;
    }

    @Override
    public Type visitTypeLiteral(TypeLiteralContext ctx) {
        return new LiteralTypeResolver(unit).resolve(ctx);
    }


}
