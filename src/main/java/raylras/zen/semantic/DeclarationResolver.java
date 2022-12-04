package raylras.zen.semantic;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;
import raylras.zen.cst.ZenScriptParserBaseListener;
import raylras.zen.semantic.scope.Scope;
import raylras.zen.semantic.symbol.*;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.Stack;

/**
 * The first scan, records all scopes, variables, classes, and functions into the annotated tree.
 */
public class DeclarationResolver extends ZenScriptParserBaseListener {

    private final AnnotatedTree annotatedTree;
    private final Stack<Scope> stack;

    public DeclarationResolver(AnnotatedTree annotatedTree) {
        this.annotatedTree = annotatedTree;
        this.stack = new ArrayStack<>();
    }

    private void pushScope(ParseTree node, Scope scope) {
        scope.setNode(node);
        annotatedTree.bindNodeToScope(node, scope);
        stack.push(scope);
    }

    private void popScope() {
        stack.pop();
    }

    private Scope currentScope() {
        return stack.peek();
    }

    @Override
    public void enterCompilationUnit(ZenScriptParser.CompilationUnitContext ctx) {
        pushScope(ctx, new Scope(annotatedTree.getName(), null));
    }

    @Override
    public void exitCompilationUnit(ZenScriptParser.CompilationUnitContext ctx) {
        popScope();
    }

    @Override
    public void enterImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        if (ctx.alias() == null) {
            TerminalNode lastID = ctx.packageName().IDENTIFIER().get(ctx.packageName().IDENTIFIER().size() - 1);
            currentScope().addSymbol(new ClassSymbol(lastID, lastID.getText()));
        }
    }

    @Override
    public void enterAlias(ZenScriptParser.AliasContext ctx) {
        currentScope().addSymbol(new ClassSymbol(ctx.IDENTIFIER(), ctx.IDENTIFIER().getText()));
    }

    @Override
    public void enterFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        currentScope().addSymbol(new FunctionSymbol(ctx.IDENTIFIER(), ctx.IDENTIFIER().getText()));
        pushScope(ctx, new Scope(ctx.IDENTIFIER().getText(), currentScope()));
    }

    @Override
    public void exitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        popScope();
    }

    @Override
    public void enterParameter(ZenScriptParser.ParameterContext ctx) {
        currentScope().addSymbol(new VariableSymbol(ctx.IDENTIFIER(), ctx.IDENTIFIER().getText()));
    }

    @Override
    public void enterClassDeclaration(ZenScriptParser.ClassDeclarationContext ctx) {
        currentScope().addSymbol(new ClassSymbol(ctx.IDENTIFIER(), ctx.IDENTIFIER().getText()));
        pushScope(ctx, new Scope(ctx.IDENTIFIER().getText(), currentScope()));
    }

    @Override
    public void exitClassDeclaration(ZenScriptParser.ClassDeclarationContext ctx) {
        popScope();
    }

    @Override
    public void enterConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        currentScope().addSymbol(new FunctionSymbol(ctx.ZEN_CONSTRUCTOR(), ctx.ZEN_CONSTRUCTOR().getText()));
        pushScope(ctx, new Scope("constructor", currentScope()));
    }

    @Override
    public void exitConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        popScope();
    }

    @Override
    public void enterVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
        int modifier = 0;
        switch (ctx.Declarator.getType()) {
            case ZenScriptLexer.VAR:
                modifier = VariableModifier.VAR;
                break;
            case ZenScriptLexer.VAL:
                modifier = VariableModifier.VAL;
                break;
            case ZenScriptLexer.GLOBAL:
                modifier = VariableModifier.GLOBAL;
                break;
            case ZenScriptLexer.STATIC:
                modifier = VariableModifier.STATIC;
                break;
        }
        VariableSymbol symbol = new VariableSymbol(ctx.IDENTIFIER(), ctx.IDENTIFIER().getText(), modifier);
        currentScope().addSymbol(symbol);
    }

    @Override
    public void enterBlockStatement(ZenScriptParser.BlockStatementContext ctx) {
        pushScope(ctx, new Scope("block", currentScope()));
    }

    @Override
    public void exitBlockStatement(ZenScriptParser.BlockStatementContext ctx) {
        popScope();
    }

    @Override
    public void enterForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        pushScope(ctx, new Scope("for", currentScope()));
        for (TerminalNode id : ctx.IDENTIFIER()) {
            currentScope().addSymbol(new VariableSymbol(id, id.getText()));
        }
    }

    @Override
    public void exitForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        popScope();
    }

    @Override
    public void enterWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        pushScope(ctx, new Scope("while", currentScope()));
    }

    @Override
    public void exitWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        popScope();
    }

    @Override
    public void enterFunctionExpression(ZenScriptParser.FunctionExpressionContext ctx) {
        pushScope(ctx, new Scope("function", currentScope()));
    }

    @Override
    public void exitFunctionExpression(ZenScriptParser.FunctionExpressionContext ctx) {
        popScope();
    }

}
