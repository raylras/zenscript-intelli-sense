package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.parser.ZenScriptParser.ConstructorDeclarationContext;
import raylras.zen.code.parser.ZenScriptParser.FunctionDeclarationContext;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.ErrorType;
import raylras.zen.code.type.Type;

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

        // zs do not have new expression, thus make constructor return class type is better than void
//        return VoidType.INSTANCE;
        ZenScriptParser.ClassDeclarationContext clazz = (ZenScriptParser.ClassDeclarationContext) ctx.getParent();
        Symbol symbol = unit.getSymbol(clazz);
        if (symbol == null) {
            return new ErrorType("unknown constructor");
        }
        Type type = symbol.getType();
        if (type == null)
            type = AnyType.INSTANCE;
        return symbol.getType();
    }


    @Override
    public Type visitFunctionExpr(ZenScriptParser.FunctionExprContext ctx) {
        Type type = new LiteralTypeResolver(unit).resolve(ctx.typeLiteral());
        if (type == null)
            type = AnyType.INSTANCE;
        return type;
    }
}
