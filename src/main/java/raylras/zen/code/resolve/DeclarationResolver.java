package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTreeWalker;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Listener;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.*;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.ParseTreeProperty;
import raylras.zen.util.Stack;

public class DeclarationResolver extends Listener {

    private final Stack<Scope> stack = new ArrayStack<>();
    private final CompilationUnit unit;
    private final ParseTreeProperty<Scope> scopeProp;
    private final ParseTreeProperty<Symbol> symbolProp;

    public DeclarationResolver(CompilationUnit unit, ParseTreeProperty<Scope> scopeProp, ParseTreeProperty<Symbol> symbolProp) {
        this.unit = unit;
        this.scopeProp = scopeProp;
        this.symbolProp = symbolProp;
    }

    public void resolve() {
        ParseTreeWalker.DEFAULT.walk(this, unit.getParseTree());
    }

    private void enterScope(Scope scope) {

        scopeProp.put(scope.getOwner(), scope);
        stack.push(scope);
    }

    private void exitScope() {
        stack.pop();
    }

    private Scope currentScope() {
        return stack.peek();
    }

    private void enterSymbol(Symbol symbol) {
        symbolProp.put(symbol.getOwner(), symbol);
        currentScope().addSymbol(symbol);
    }

    @Override
    public void enterCompilationUnit(CompilationUnitContext ctx) {
        enterScope(new Scope(null, ctx));
    }

    @Override
    public void exitCompilationUnit(CompilationUnitContext ctx) {
        exitScope();
    }

    @Override
    public void enterImportDeclaration(ImportDeclarationContext ctx) {
        enterSymbol(new ImportSymbol(ctx, unit));
    }

    @Override
    public void enterFunctionDeclaration(FunctionDeclarationContext ctx) {
        enterSymbol(new FunctionSymbol(ctx, unit, false));
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitFunctionDeclaration(FunctionDeclarationContext ctx) {
        exitScope();
    }


    @Override
    public void enterFunctionExpr(FunctionExprContext ctx) {
        enterSymbol(new FunctionExpressionSymbol(ctx, unit));
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitFunctionExpr(FunctionExprContext ctx) {
        exitScope();
    }

    @Override
    public void enterExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
        // TODO: expand constructor?
        enterSymbol(new ExpandFunctionSymbol(ctx, unit, false));
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
        exitScope();
    }

    @Override
    public void enterParameter(ParameterContext ctx) {
        enterSymbol(new VariableSymbol(ctx, unit));
    }

    @Override
    public void enterClassDeclaration(ClassDeclarationContext ctx) {
        enterSymbol(new ClassSymbol(ctx, unit));
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitClassDeclaration(ClassDeclarationContext ctx) {
        exitScope();
    }

    @Override
    public void enterConstructorDeclaration(ConstructorDeclarationContext ctx) {
        enterSymbol(new FunctionSymbol(ctx, unit, true));
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitConstructorDeclaration(ConstructorDeclarationContext ctx) {
        exitScope();
    }

    @Override
    public void enterVariableDeclaration(VariableDeclarationContext ctx) {
        enterSymbol(new VariableSymbol(ctx, unit));
    }

    @Override
    public void enterThenBody(ThenBodyContext ctx) {
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitThenBody(ThenBodyContext ctx) {
        exitScope();
    }

    @Override
    public void enterElseBody(ElseBodyContext ctx) {
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitElseBody(ElseBodyContext ctx) {
        exitScope();
    }

    @Override
    public void enterForeachStatement(ForeachStatementContext ctx) {
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitForeachStatement(ForeachStatementContext ctx) {
        exitScope();
    }

    @Override
    public void enterSimpleVariable(SimpleVariableContext ctx) {
        enterSymbol(new VariableSymbol(ctx, unit));
    }

    @Override
    public void enterWhileStatement(WhileStatementContext ctx) {
        enterScope(new Scope(currentScope(), ctx));
    }

    @Override
    public void exitWhileStatement(WhileStatementContext ctx) {
        exitScope();
    }

}
