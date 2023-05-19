package raylras.zen.code;

import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.parser.ZenScriptParserBaseVisitor;

public abstract class Visitor<T> extends ZenScriptParserBaseVisitor<T> {

    public T visitTypeLiteral(ZenScriptParser.TypeLiteralContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public T visitArrayType(ZenScriptParser.ArrayTypeContext ctx) {
        return super.visitArrayType(ctx);
    }

    @Override
    public T visitFunctionType(ZenScriptParser.FunctionTypeContext ctx) {
        return super.visitFunctionType(ctx);
    }

    @Override
    public T visitListType(ZenScriptParser.ListTypeContext ctx) {
        return super.visitListType(ctx);
    }

    @Override
    public T visitPrimitiveType(ZenScriptParser.PrimitiveTypeContext ctx) {
        return super.visitPrimitiveType(ctx);
    }

    @Override
    public T visitClassType(ZenScriptParser.ClassTypeContext ctx) {
        return super.visitClassType(ctx);
    }

    @Override
    public T visitMapType(ZenScriptParser.MapTypeContext ctx) {
        return super.visitMapType(ctx);
    }
}
