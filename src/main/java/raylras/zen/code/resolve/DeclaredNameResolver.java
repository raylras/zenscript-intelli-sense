package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.*;

public class DeclaredNameResolver extends Visitor<String> {

    public String resolve(ParseTree node) {
        if (node != null) {
            return node.accept(this);
        } else {
            return null;
        }
    }

    @Override
    public String visitTerminal(TerminalNode node) {
        if (node != null) {
            return node.getText();
        } else {
            return null;
        }
    }

    @Override
    public String visitImportDeclaration(ImportDeclarationContext ctx) {
        if (ctx.alias() != null) {
            return visitAlias(ctx.alias());
        } else {
            return visit(ctx.qualifiedName().identifier());
        }
    }

    @Override
    public String visitQualifiedName(QualifiedNameContext ctx) {
        if (ctx != null) {
            return ctx.getText();
        } else {
            return null;
        }
    }

    @Override
    public String visitAlias(AliasContext ctx) {
        if (ctx != null) {
            return visitIdentifier(ctx.identifier());
        } else {
            return null;
        }
    }

    @Override
    public String visitIdentifier(IdentifierContext ctx) {
        if (ctx != null) {
            return ctx.getText();
        } else {
            return null;
        }
    }

    @Override
    public String visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        return visitIdentifier(ctx.identifier());
    }

    @Override
    public String visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
        return visitIdentifier(ctx.identifier());
    }

    @Override
    public String visitParameter(ParameterContext ctx) {
        return visitIdentifier(ctx.identifier());
    }

    @Override
    public String visitClassDeclaration(ClassDeclarationContext ctx) {
        return visitQualifiedName(ctx.qualifiedName());
    }

    @Override
    public String visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
        return visitTerminal(ctx.ZEN_CONSTRUCTOR());
    }

    @Override
    public String visitVariableDeclaration(VariableDeclarationContext ctx) {
        return visitIdentifier(ctx.identifier());
    }

    @Override
    public String visitForeachVariableDeclaration(ForeachVariableDeclarationContext ctx) {
        return visitIdentifier(ctx.identifier());
    }

    @Override
    public String visitLocalAccessExpr(LocalAccessExprContext ctx) {
        return visitIdentifier(ctx.identifier());
    }

}
