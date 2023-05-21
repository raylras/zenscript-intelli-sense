package raylras.zen.code.type.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.ParameterContext;
import raylras.zen.code.parser.ZenScriptParser.SimpleVariableContext;
import raylras.zen.code.parser.ZenScriptParser.VariableDeclarationContext;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.Type;

public class VariableTypeResolver extends Visitor<Type> {

    private final CompilationUnit unit;

    public VariableTypeResolver(CompilationUnit unit) {
        this.unit = unit;
    }

    public Type resolve(ParseTree node) {
        if (node == null)
            return null;
        return node.accept(this);
    }

    @Override
    public Type visitParameter(ParameterContext ctx) {
        Type type = new LiteralTypeResolver(unit).resolve(ctx.typeLiteral());
        if (type == null)
            type = new ExpressionTypeResolver(unit).resolve(ctx.defaultValue());
        if (type == null)
            type = AnyType.INSTANCE;
        return type;
    }

    @Override
    public Type visitVariableDeclaration(VariableDeclarationContext ctx) {
        Type type = new LiteralTypeResolver(unit).resolve(ctx.typeLiteral());
        if (type == null)
            type = new ExpressionTypeResolver(unit).resolve(ctx.initializer());
        if (type == null)
            type = AnyType.INSTANCE;
        return type;
    }

    @Override
    public Type visitSimpleVariable(SimpleVariableContext ctx) {
        return AnyType.INSTANCE;
    }

}
