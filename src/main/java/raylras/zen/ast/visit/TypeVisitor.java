package raylras.zen.ast.visit;

import raylras.zen.antlr.ZenScriptLexer;
import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.antlr.ZenScriptParserBaseVisitor;
import raylras.zen.ast.type.*;

import java.util.List;
import java.util.stream.Collectors;

public final class TypeVisitor extends ZenScriptParserBaseVisitor<Type> {
    public Type visitType(ZenScriptParser.TypeContext ctx) {
        if (ctx == null) return null;
        return ctx.accept(this);
    }

    @Override
    public Type visitBuiltinType(ZenScriptParser.BuiltinTypeContext ctx) {
        if (ctx == null) return null;

        switch (ctx.getStart().getType()) {
            case ZenScriptLexer.BOOL:
            case ZenScriptLexer.BOOL_OBJ:
                return Types.BOOL;

            case ZenScriptLexer.BYTE:
            case ZenScriptLexer.BYTE_OBJ:
                return Types.BYTE;

            case ZenScriptLexer.SHORT:
            case ZenScriptLexer.SHORT_OBJ:
                return Types.SHORT;

            case ZenScriptLexer.INT:
            case ZenScriptLexer.INT_OBJ:
                return Types.INT;

            case ZenScriptLexer.LONG:
            case ZenScriptLexer.LONG_OBJ:
                return Types.LONG;

            case ZenScriptLexer.FLOAT:
            case ZenScriptLexer.FLOAT_OBJ:
                return Types.FLOAT;

            case ZenScriptLexer.DOUBLE:
            case ZenScriptLexer.DOUBLE_OBJ:
                return Types.DOUBLE;

            case ZenScriptLexer.VOID:
                return Types.VOID;

            case ZenScriptLexer.STRING:
                return Types.STRING;

            default:
                return null;
        }
    }

    @Override
    public Type visitArrayType(ZenScriptParser.ArrayTypeContext ctx) {
        if (ctx == null) return null;
        Type base = ctx.BaseType.accept(this);
        return new ArrayType(base);
    }

    @Override
    public Type visitFunctionType(ZenScriptParser.FunctionTypeContext ctx) {
        if (ctx == null) return null;
        List<Type> argTypeList = ctx.argumentTypeList().type().stream().map(this::visitType).collect(Collectors.toList());
        Type result = ctx.type().accept(this);
        return new FunctionType(argTypeList, result);
    }

    @Override
    public Type visitListType(ZenScriptParser.ListTypeContext ctx) {
        if (ctx == null) return null;
        Type base = ctx.BaseType.accept(this);
        return new ListType(base);
    }

    @Override
    public Type visitReferenceType(ZenScriptParser.ReferenceTypeContext ctx) {
        if (ctx == null) return null;
        return new ClassType(ctx.getText());
    }

    @Override
    public Type visitMapType(ZenScriptParser.MapTypeContext ctx) {
        if (ctx == null) return null;
        Type key = ctx.KeyType.accept(this);
        Type value = ctx.ValueType.accept(this);
        return new MapType(key, value);
    }

}
