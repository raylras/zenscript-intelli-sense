package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.type.*;

import java.util.List;
import java.util.stream.Collectors;

public class LiteralTypeResolver extends Visitor<Type> {

    private final CompilationUnit unit;

    public LiteralTypeResolver(CompilationUnit unit) {
        this.unit = unit;
    }

    public Type resolve(ParseTree node) {
        if (node == null)
            return null;
        return node.accept(this);
    }

    @Override
    public Type visitClassType(ClassTypeContext ctx) {
        return AnyType.INSTANCE;
    }

    @Override
    public Type visitMapType(MapTypeContext ctx) {
        Type keyType = visit(ctx.Key);
        Type valueType = visit(ctx.Value);
        return new MapType(keyType, valueType);
    }

    @Override
    public Type visitArrayType(ArrayTypeContext ctx) {
        Type elementType = ctx.typeLiteral().accept(this);
        return new ArrayType(elementType);
    }

    @Override
    public Type visitFunctionType(FunctionTypeContext ctx) {
        List<Type> typeList = ctx.typeLiteral().stream()
                .map(typeCtx-> typeCtx.accept(this))
                .collect(Collectors.toList());
        int lastElementIndex = typeList.size() - 1;
        List<Type> paramTypes = typeList.subList(0, lastElementIndex);
        Type returnType = typeList.get(lastElementIndex);
        return new FunctionType(paramTypes, returnType);
    }

    @Override
    public Type visitListType(ListTypeContext ctx) {
        Type elementType = ctx.typeLiteral().accept(this);
        return new ListType(elementType);
    }

    @Override
    public Type visitPrimitiveType(PrimitiveTypeContext ctx) {
        switch (ctx.start.getType()) {
            case ZenScriptLexer.ANY:
                return AnyType.INSTANCE;

            case ZenScriptLexer.BYTE:
                return ByteType.INSTANCE;

            case ZenScriptLexer.SHORT:
                return ShortType.INSTANCE;

            case ZenScriptLexer.INT:
                return IntType.INSTANCE;

            case ZenScriptLexer.LONG:
                return LongType.INSTANCE;

            case ZenScriptLexer.FLOAT:
                return FloatType.INSTANCE;

            case ZenScriptLexer.DOUBLE:
                return DoubleType.INSTANCE;

            case ZenScriptLexer.BOOL:
                return BoolType.INSTANCE;

            case ZenScriptLexer.VOID:
                return VoidType.INSTANCE;

            case ZenScriptLexer.STRING:
                return StringType.INSTANCE;

            default:
                return null;
        }
    }

}
