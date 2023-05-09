package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.*;

import java.util.List;

public class NameResolver extends Visitor<String> {

    @Override
    public String visitImportDeclaration(ImportDeclarationContext ctx) {
        if (ctx.alias() != null) {
            return ctx.alias().getText();
        }
        List<TerminalNode> names = ctx.qualifiedName().IDENTIFIER();
        return names.get(names.size() - 1).getText();
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

}
