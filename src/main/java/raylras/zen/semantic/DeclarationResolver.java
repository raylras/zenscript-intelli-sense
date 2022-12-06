package raylras.zen.semantic;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;
import raylras.zen.cst.ZenScriptParserBaseListener;
import raylras.zen.semantic.scope.BaseScope;
import raylras.zen.semantic.scope.Scope;
import raylras.zen.semantic.symbol.ClassSymbol;
import raylras.zen.semantic.symbol.FunctionSymbol;
import raylras.zen.semantic.symbol.ImportSymbol;
import raylras.zen.semantic.symbol.VariableSymbol;
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
        pushScope(ctx, new BaseScope(annotatedTree.getName(), null, ctx));
    }

    @Override
    public void exitCompilationUnit(ZenScriptParser.CompilationUnitContext ctx) {
        popScope();
    }

    @Override
    public void enterImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        if (ctx.alias() != null) {
            return;
        }
        TerminalNode lastID = ctx.packageName().IDENTIFIER().get(ctx.packageName().IDENTIFIER().size() - 1);
        currentScope().addSymbol(new ImportSymbol(lastID.getText(), currentScope(), ctx));
    }

    @Override
    public void enterAlias(ZenScriptParser.AliasContext ctx) {
        if (ctx.IDENTIFIER() == null) {
            return;
        }
        currentScope().addSymbol(new ImportSymbol(ctx.IDENTIFIER().getText(), currentScope(), ctx));
    }

    @Override
    public void enterFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        if (ctx.IDENTIFIER() == null) {
            return;
        }
        FunctionSymbol func = new FunctionSymbol(ctx.IDENTIFIER().getText(), currentScope(), ctx);
        currentScope().addSymbol(func);
        pushScope(ctx, func);
    }

    @Override
    public void exitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        popScope();
    }

    @Override
    public void enterParameter(ZenScriptParser.ParameterContext ctx) {
        if (ctx.IDENTIFIER() == null) {
            return;
        }
        currentScope().addSymbol(new VariableSymbol(ctx.IDENTIFIER().getText(), currentScope(), ctx));
    }

    @Override
    public void enterClassDeclaration(ZenScriptParser.ClassDeclarationContext ctx) {
        if (ctx.IDENTIFIER() == null) {
            return;
        }
        ClassSymbol clazz = new ClassSymbol(ctx.IDENTIFIER().getText(), currentScope(), ctx);
        currentScope().addSymbol(clazz);
        pushScope(ctx, clazz);
    }

    @Override
    public void exitClassDeclaration(ZenScriptParser.ClassDeclarationContext ctx) {
        popScope();
    }

    @Override
    public void enterConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        FunctionSymbol symbol = new FunctionSymbol("constructor", currentScope(), ctx);
        currentScope().addSymbol(symbol);
        pushScope(ctx, symbol);
    }

    @Override
    public void exitConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        popScope();
    }

    @Override
    public void enterVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
        if (ctx.IDENTIFIER() == null) {
            return;
        }
        int modifier = 0;
        switch (ctx.Declarator.getType()) {
            case ZenScriptLexer.VAR:
                modifier = VariableSymbol.Modifier.VAR;
                break;
            case ZenScriptLexer.VAL:
                modifier = VariableSymbol.Modifier.VAL;
                break;
            case ZenScriptLexer.GLOBAL:
                modifier = VariableSymbol.Modifier.GLOBAL;
                break;
            case ZenScriptLexer.STATIC:
                modifier = VariableSymbol.Modifier.STATIC;
                break;
        }
        VariableSymbol var = new VariableSymbol(ctx.IDENTIFIER().getText(), currentScope(), ctx);
        var.setModifier(modifier);
        currentScope().addSymbol(var);
    }

    @Override
    public void enterBlockStatement(ZenScriptParser.BlockStatementContext ctx) {
        String name = "block";
        if (ctx.parent instanceof ZenScriptParser.IfBodyContext) {
            name = "if";
        } else if (ctx.parent instanceof ZenScriptParser.ElseBodyContext) {
            name = "else";
        }
        pushScope(ctx, new BaseScope(name, currentScope(), ctx));
    }

    @Override
    public void exitBlockStatement(ZenScriptParser.BlockStatementContext ctx) {
        popScope();
    }

    @Override
    public void enterForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        pushScope(ctx, new BaseScope("for", currentScope(), ctx));
        for (TerminalNode id : ctx.IDENTIFIER()) {
            currentScope().addSymbol(new VariableSymbol(id.getText(), currentScope(), id));
        }
    }

    @Override
    public void exitForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        popScope();
    }

    @Override
    public void enterWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        pushScope(ctx, new BaseScope("while", currentScope(), ctx));
    }

    @Override
    public void exitWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        popScope();
    }

    @Override
    public void enterFunctionExpression(ZenScriptParser.FunctionExpressionContext ctx) {
        pushScope(ctx, new BaseScope("function", currentScope(), ctx));
    }

    @Override
    public void exitFunctionExpression(ZenScriptParser.FunctionExpressionContext ctx) {
        popScope();
    }

}
