package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Listener;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.*;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.Stack;

import java.util.List;
import java.util.Objects;

public final class DeclarationResolver {

    private DeclarationResolver() {}

    public static void resolveDeclarations(CompilationUnit unit) {
        Objects.requireNonNull(unit);
        unit.accept(new DeclarationListener(unit));
    }

    private static final class DeclarationListener extends Listener {
        private final CompilationUnit unit;
        private final Stack<Scope> scopeStack = new ArrayStack<>();
        private final Stack<ClassSymbol> classStack = new ArrayStack<>();

        public DeclarationListener(CompilationUnit unit) {
            this.unit = unit;
        }

        private void enterScope(Scope scope) {
            unit.addScope(scope);
            scopeStack.push(scope);
        }

        private void exitScope() {
            scopeStack.pop();
        }

        private Scope currentScope() {
            return scopeStack.peek();
        }

        private void enterClass(ClassSymbol symbol) {
            classStack.push(symbol);
        }

        private void exitClass() {
            classStack.pop();
        }

        private ClassSymbol currentClass() {
            return classStack.peek();
        }

        private void enterSymbol(ParseTree cst, Symbol symbol) {
            unit.putSymbol(cst, symbol);
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
            ParseTree name;
            if (ctx.alias() != null) {
                name = ctx.alias();
            } else {
                List<SimpleNameContext> simpleNameList = ctx.qualifiedName().simpleName();
                name = simpleNameList.get(simpleNameList.size() - 1);
            }
            ImportSymbol symbol = SymbolFactory.createImportSymbol(name, ctx, unit);
            enterSymbol(ctx, symbol);
        }

        @Override
        public void enterFunctionDeclaration(FunctionDeclarationContext ctx) {
            FunctionSymbol symbol = SymbolFactory.createFunctionSymbol(ctx.simpleName(), ctx, unit);
            enterSymbol(ctx, symbol);
            enterScope(new Scope(currentScope(), ctx));
        }

        @Override
        public void exitFunctionDeclaration(FunctionDeclarationContext ctx) {
            exitScope();
        }

        @Override
        public void enterExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            ExpandFunctionSymbol symbol = SymbolFactory.createExpandFunctionSymbol(ctx.simpleName(), ctx, unit);
            enterSymbol(ctx, symbol);
            Scope scope = new Scope(currentScope(), ctx);
            enterScope(scope);
            scope.addSymbol(SymbolFactory.createThisSymbol(symbol::getExpandingType));
        }

        @Override
        public void exitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            exitScope();
        }

        @Override
        public void enterFormalParameter(FormalParameterContext ctx) {
            ParameterSymbol symbol = SymbolFactory.createParameterSymbol(ctx.simpleName(), ctx, unit);
            enterSymbol(ctx, symbol);
        }

        @Override
        public void enterFunctionBody(FunctionBodyContext ctx) {
            enterScope(new Scope(currentScope(), ctx));
        }

        @Override
        public void exitFunctionBody(FunctionBodyContext ctx) {
            exitScope();
        }

        @Override
        public void enterClassDeclaration(ClassDeclarationContext ctx) {
            ParseTree name;
            if (ctx.simpleName() != null) {
                name = ctx.simpleName();
            } else {
                name = ctx.simpleNameOrPrimitiveType();
            }

            ClassSymbol symbol = SymbolFactory.createClassSymbol(name, ctx, unit);
            enterSymbol(ctx, symbol);
            enterClass(symbol);

            Scope scope = new Scope(currentScope(), ctx);
            enterScope(scope);
            scope.addSymbol(SymbolFactory.createThisSymbol(symbol::getType));
        }

        @Override
        public void exitClassDeclaration(ClassDeclarationContext ctx) {
            exitScope();
            exitClass();
        }

        @Override
        public void enterConstructorDeclaration(ConstructorDeclarationContext ctx) {
            ConstructorSymbol symbol = SymbolFactory.createConstructorSymbol(ctx.ZEN_CONSTRUCTOR(), ctx, unit, currentClass());
            enterSymbol(ctx, symbol);
            enterScope(new Scope(currentScope(), ctx));
        }

        @Override
        public void exitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            exitScope();
        }

        @Override
        public void enterVariableDeclaration(VariableDeclarationContext ctx) {
            VariableSymbol symbol = SymbolFactory.createVariableSymbol(ctx.simpleName(), ctx, unit);
            enterSymbol(ctx, symbol);
        }

        @Override
        public void enterOperatorFunctionDeclaration(OperatorFunctionDeclarationContext ctx) {
            OperatorFunctionSymbol symbol = SymbolFactory.createOperatorFunctionSymbol(ctx.operator(), ctx, unit);
            enterSymbol(ctx, symbol);
            enterScope(new Scope(currentScope(), ctx));
        }

        @Override
        public void exitOperatorFunctionDeclaration(OperatorFunctionDeclarationContext ctx) {
            exitScope();
        }

        @Override
        public void enterThenPart(ThenPartContext ctx) {
            enterScope(new Scope(currentScope(), ctx));
        }

        @Override
        public void exitThenPart(ThenPartContext ctx) {
            exitScope();
        }

        @Override
        public void enterElsePart(ElsePartContext ctx) {
            enterScope(new Scope(currentScope(), ctx));
        }

        @Override
        public void exitElsePart(ElsePartContext ctx) {
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
        public void enterForeachVariable(ForeachVariableContext ctx) {
            VariableSymbol symbol = SymbolFactory.createVariableSymbol(ctx.simpleName(), ctx, unit);
            enterSymbol(ctx, symbol);
        }

        @Override
        public void enterWhileStatement(WhileStatementContext ctx) {
            enterScope(new Scope(currentScope(), ctx));
        }

        @Override
        public void exitWhileStatement(WhileStatementContext ctx) {
            exitScope();
        }

        @Override
        public void enterFunctionExpr(FunctionExprContext ctx) {
            enterScope(new Scope(currentScope(), ctx));
        }

        @Override
        public void exitFunctionExpr(FunctionExprContext ctx) {
            exitScope();
        }
    }

}
