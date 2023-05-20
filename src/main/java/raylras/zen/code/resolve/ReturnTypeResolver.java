package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.parser.ZenScriptParser.ConstructorDeclarationContext;
import raylras.zen.code.parser.ZenScriptParser.FunctionDeclarationContext;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.Type;
import raylras.zen.code.type.VoidType;

public class ReturnTypeResolver extends Visitor<Type> {

    private final CompilationUnit unit;

    public ReturnTypeResolver(CompilationUnit unit) {
        this.unit = unit;
    }

    public Type resolve(ParseTree node) {
        if (node == null)
            return null;
        return node.accept(this);
    }

    @Override
    public Type visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        Type type = new LiteralTypeResolver(unit).resolve(ctx.typeLiteral());
        if (type == null)
            type = AnyType.INSTANCE;
        return type;
    }

    @Override
    public Type visitExpandFunctionDeclaration(ZenScriptParser.ExpandFunctionDeclarationContext ctx) {
        Type type = new LiteralTypeResolver(unit).resolve(ctx.typeLiteral());
        if (type == null)
            type = AnyType.INSTANCE;
        return type;
    }

    @Override
    public Type visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
        return VoidType.INSTANCE;
    }

    @Override
    public Type visitFunctionExpr(ZenScriptParser.FunctionExprContext ctx) {
        Type type = new LiteralTypeResolver(unit).resolve(ctx.typeLiteral());
        if (type == null)
            type = AnyType.INSTANCE;
        return type;
    }
}
