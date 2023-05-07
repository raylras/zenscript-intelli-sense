package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTreeWalker;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Listener;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.*;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.Stack;

public class DefResolver extends Listener {

    private final CompilationUnit unit;
    private final Stack<Scope> stack = new ArrayStack<>();

    public DefResolver(CompilationUnit unit) {
        this.unit = unit;
    }

    public void resolve() {
        ParseTreeWalker.DEFAULT.walk(this, unit.parseTree);
    }

    private void enterScope(Scope scope) {
        unit.scopes.put(scope.owner, scope);
        stack.push(scope);
    }

    private void exitScope() {
        stack.pop();
    }

    private Scope currentScope() {
        return stack.peek();
    }

    private void enterSymbol(Symbol symbol) {
        unit.symbols.put(symbol.owner, symbol);
        currentScope().addSymbol(symbol);
    }

    @Override
    public void enterCompilationUnit(ZenScriptParser.CompilationUnitContext ctx) {
        enterScope(new Scope(null, ctx));
    }

    @Override
    public void exitCompilationUnit(ZenScriptParser.CompilationUnitContext ctx) {
        exitScope();
    }

    @Override
    public void enterImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        enterSymbol(new ImportSymbol(currentScope(), ctx, unit));
    }

    @Override
    public void enterFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        enterSymbol(new FunctionSymbol(currentScope(), ctx, unit));
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        exitScope();
    }

    @Override
    public void enterParameter(ZenScriptParser.ParameterContext ctx) {
        enterSymbol(new VariableSymbol(currentScope(), ctx, unit));
    }

    @Override
    public void enterClassDeclaration(ZenScriptParser.ClassDeclarationContext ctx) {
        enterSymbol(new ClassSymbol(currentScope(), ctx, unit));
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitClassDeclaration(ZenScriptParser.ClassDeclarationContext ctx) {
        exitScope();
    }

    @Override
    public void enterConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        enterSymbol(new FunctionSymbol(currentScope(), ctx, unit));
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        exitScope();
    }

    @Override
    public void enterVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
        enterSymbol(new VariableSymbol(currentScope(), ctx, unit));
    }

    @Override
    public void enterThenBody(ZenScriptParser.ThenBodyContext ctx) {
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitThenBody(ZenScriptParser.ThenBodyContext ctx) {
        exitScope();
    }

    @Override
    public void enterElseBody(ZenScriptParser.ElseBodyContext ctx) {
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitElseBody(ZenScriptParser.ElseBodyContext ctx) {
        exitScope();
    }

    @Override
    public void enterForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        exitScope();
    }

    @Override
    public void enterSimpleVariable(ZenScriptParser.SimpleVariableContext ctx) {
        enterSymbol(new VariableSymbol(currentScope(), ctx, unit));
    }

    @Override
    public void enterWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        exitScope();
    }

}
