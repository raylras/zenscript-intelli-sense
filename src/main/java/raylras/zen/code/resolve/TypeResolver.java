package raylras.zen.code.resolve;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TypeResolver extends Visitor<Type> {

    private final CompilationUnit unit;

    public TypeResolver(CompilationUnit unit) {
        this.unit = unit;
    }

    public Type visitTypeLiteral(TypeLiteralContext ctx) {
        if (ctx == null) return null;
        return ctx.accept(this);
    }

    public Type visitExpression(ExpressionContext ctx) {
        return ctx.accept(this);
    }

    @Override
    public Type visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        Type returnType = visitTypeLiteral(ctx.typeLiteral());
        if (returnType == null)
            returnType = new AnyType();
        List<Type> paramTypes = ctx.parameter().stream()
                .map(this::visitParameter)
                .collect(Collectors.toList());
        return new FunctionType(paramTypes, returnType);
    }

    @Override
    public Type visitParameter(ParameterContext ctx) {
        Type type = visitTypeLiteral(ctx.typeLiteral());
        if (type == null)
            type = visitDefaultValue(ctx.defaultValue());
        if (type == null)
            type = new AnyType();
        return type;
    }

    @Override
    public Type visitDefaultValue(DefaultValueContext ctx) {
        if (ctx == null) return null;
        return visitExpression(ctx.expression());
    }

    @Override
    public Type visitVariableDeclaration(VariableDeclarationContext ctx) {
        Type type = visitTypeLiteral(ctx.typeLiteral());
        if (type == null)
            type = visitInitializer(ctx.initializer());
        if (type == null)
            type = new AnyType();
        return type;
    }

    @Override
    public Type visitInitializer(InitializerContext ctx) {
        if (ctx == null) return null;
        return visitExpression(ctx.expression());
    }

    @Override
    public Type visitSimpleVariable(SimpleVariableContext ctx) {
        return new AnyType();
    }

    @Override
    public Type visitCall(CallContext ctx) {
        Type leftType = ctx.Left.accept(this);
        if (leftType instanceof FunctionType) {
            return ((FunctionType) leftType).returnType;
        }
        return new AnyType();
    }

    @Override
    public Type visitSimpleNameExpression(SimpleNameExpressionContext ctx) {
        Symbol symbol = unit.lookupSymbol(ctx);
        if (symbol != null)
            return symbol.getType();
        return new AnyType();
    }

    @Override
    public Type visitMemberAccess(MemberAccessContext ctx) {
        Symbol leftSymbol = visitExpression(ctx.Left).lookupSymbol();
        String rightName = ctx.IDENTIFIER().getText();
        Type type = null;
        if (leftSymbol != null) {
            for (Symbol member : leftSymbol.getMembers()) {
                if (member.getName().equals(rightName)) {
                    type = member.getType();
                    break;
                }
            }
        }
        if (type == null)
            type = new AnyType();
        return type;
    }

    @Override
    public Type visitBinary(BinaryContext ctx) {
        Type type;
        switch (ctx.Op.getType()) {
            case ZenScriptLexer.ADD:
            case ZenScriptLexer.SUB:
            case ZenScriptLexer.MUL:
            case ZenScriptLexer.DIV:
                type = visitExpression(ctx.Left);
            default:
                type = new AnyType();
        }
        return type;
    }

    @Override
    public Type visitTypeCast(TypeCastContext ctx) {
        Type type = visitTypeLiteral(ctx.typeLiteral());
        if (type == null)
            type = new AnyType();
        return type;
    }

    @Override
    public Type visitNullLiteral(NullLiteralContext ctx) {
        return new AnyType();
    }

    @Override
    public Type visitArrayInitializer(ArrayInitializerContext ctx) {
        Type type = visitExpression(ctx.expression(0));
        if (type == null)
            type = new AnyType();
        return type;
    }

    @Override
    public Type visitArrayType(ArrayTypeContext ctx) {
        Type elementType = visit(ctx.typeLiteral());
        return new ArrayType(elementType);
    }

    @Override
    public Type visitFunctionType(FunctionTypeContext ctx) {
        List<Type> paramTypes = new ArrayList<>();
        for (int i = 0; i < ctx.typeLiteral().size() - 1; i++) {
            paramTypes.add(visit(ctx.typeLiteral(i)));
        }
        Type returnType = visit(ctx.Return);
        return new FunctionType(paramTypes, returnType);
    }

    @Override
    public Type visitListType(ListTypeContext ctx) {
        Type elementType = visit(ctx.typeLiteral());
        return new ListType(elementType);
    }

    @Override
    public Type visitPrimitiveType(PrimitiveTypeContext ctx) {
        switch (ctx.start.getType()) {
            case ZenScriptLexer.ANY:
                return new AnyType();

            case ZenScriptLexer.BYTE:
                return new ByteType();

            case ZenScriptLexer.SHORT:
                return new ShortType();

            case ZenScriptLexer.INT:
                return new IntType();

            case ZenScriptLexer.LONG:
                return new LongType();

            case ZenScriptLexer.FLOAT:
                return new FloatType();

            case ZenScriptLexer.DOUBLE:
                return new DoubleType();

            case ZenScriptLexer.BOOL:
                return new BoolType();

            case ZenScriptLexer.VOID:
                return new VoidType();

            case ZenScriptLexer.STRING:
                return new StringType();

            default:
                return null;
        }
    }

    @Override
    public Type visitClassType(ClassTypeContext ctx) {
        Type type = unit.lookupType(ctx);
        if (type == null)
            type = new ClassType(ctx, unit);
        return type;
    }

    @Override
    public Type visitMapType(MapTypeContext ctx) {
        Type keyType = visit(ctx.Key);
        Type valueType = visit(ctx.Value);
        return new MapType(keyType, valueType);
    }

}
