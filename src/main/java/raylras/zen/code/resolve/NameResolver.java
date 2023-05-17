package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;

public class NameResolver extends Visitor<String> {

    public String resolve(ParseTree node) {
        if (node == null)
            return null;
        return node.accept(this);
    }

    @Override
    public String visitTerminal(TerminalNode node) {
        return node.getText();
    }

    @Override
    public String visitImportDeclaration(ImportDeclarationContext ctx) {
        String name = visitAlias(ctx.alias());
        if (name == null)
            name = visitQualifiedName(ctx.qualifiedName());
        return name;
    }

    @Override
    public String visitQualifiedName(QualifiedNameContext ctx) {
        if (ctx == null) return null;
        return ctx.getText();
    }

    @Override
    public String visitAlias(AliasContext ctx) {
        if (ctx == null) return null;
        return ctx.getText();
    }

    @Override
    public String visitSimpleName(SimpleNameContext ctx) {
        if (ctx == null)
            return null;
        return ctx.getText();
    }

    @Override
    public String visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        return visitSimpleName(ctx.simpleName());
    }

    @Override
    public String visitParameter(ParameterContext ctx) {
        return visitSimpleName(ctx.simpleName());
    }

    @Override
    public String visitClassDeclaration(ClassDeclarationContext ctx) {
        return visitQualifiedName(ctx.qualifiedName());
    }

    @Override
    public String visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
        return ZenScriptLexer.VOCABULARY.getLiteralName(ZenScriptLexer.ZEN_CONSTRUCTOR);
    }

    @Override
    public String visitVariableDeclaration(VariableDeclarationContext ctx) {
        return visitSimpleName(ctx.simpleName());
    }

    @Override
    public String visitSimpleVariable(SimpleVariableContext ctx) {
        return visitSimpleName(ctx.simpleName());
    }

    @Override
    public String visitLocalAccessExpr(LocalAccessExprContext ctx) {
        return visitSimpleName(ctx.simpleName());
    }

}
