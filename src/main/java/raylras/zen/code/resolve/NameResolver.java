package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.*;

public class NameResolver extends Visitor<String> {

    @Override
    public String visitImportDeclaration(ImportDeclarationContext ctx) {
        String name = visitAlias(ctx.alias());
        if (name == null) {
            name = ctx.qualifiedName().IDENTIFIER().stream()
                    .skip(ctx.qualifiedName().IDENTIFIER().size() - 1)
                    .map(ParseTree::getText)
                    .findFirst()
                    .orElse(null);
        }
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
        return ctx.IDENTIFIER().getText();
    }

    @Override
    public String visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        return ctx.IDENTIFIER().getText();
    }

    @Override
    public String visitParameter(ParameterContext ctx) {
        return ctx.IDENTIFIER().getText();
    }

    @Override
    public String visitClassDeclaration(ClassDeclarationContext ctx) {
        return ctx.qualifiedName().getText();
    }

    @Override
    public String visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
        return ctx.ZEN_CONSTRUCTOR().getText();
    }

    @Override
    public String visitVariableDeclaration(VariableDeclarationContext ctx) {
        return ctx.IDENTIFIER().getText();
    }

    @Override
    public String visitSimpleVariable(SimpleVariableContext ctx) {
        return ctx.IDENTIFIER().getText();
    }

    @Override
    public String visitSimpleNameExpression(SimpleNameExpressionContext ctx) {
        return ctx.IDENTIFIER().getText();
    }

}
