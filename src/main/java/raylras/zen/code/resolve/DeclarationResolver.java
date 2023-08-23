package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Listener;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.*;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.CSTNodes;
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
        private final Stack<Scope> stack = new ArrayStack<>();

        public DeclarationListener(CompilationUnit unit) {
            this.unit = unit;
        }

        private void enterScope(Scope scope) {
            unit.addScope(scope);
            stack.push(scope);
        }

        private void exitScope() {
            stack.pop();
        }

        private Scope currentScope() {
            return stack.peek();
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
            String name;
            if (ctx.alias() != null) {
                name = CSTNodes.getText(ctx.alias());
            } else {
                List<SimpleNameContext> simpleNameList = ctx.qualifiedName().simpleName();
                SimpleNameContext lastSimpleName = simpleNameList.get(simpleNameList.size() - 1);
                name = CSTNodes.getText(lastSimpleName);
            }
            ImportSymbol symbol = SymbolFactory.createImportSymbol(name, ctx, unit);
            enterSymbol(ctx, symbol);
        }

        @Override
        public void enterFunctionDeclaration(FunctionDeclarationContext ctx) {
            String name = CSTNodes.getText(ctx.simpleName());
            FunctionSymbol symbol = SymbolFactory.createFunctionSymbol(name, ctx, unit);
            enterSymbol(ctx, symbol);
            enterScope(new Scope(currentScope(), ctx));
        }

        @Override
        public void exitFunctionDeclaration(FunctionDeclarationContext ctx) {
            exitScope();
        }

        @Override
        public void enterExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            String name = CSTNodes.getText(ctx.simpleName());
            FunctionSymbol symbol = SymbolFactory.createFunctionSymbol(name, ctx, unit);
            enterSymbol(ctx, symbol);
            enterScope(new Scope(currentScope(), ctx));
        }

        @Override
        public void exitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            exitScope();
        }

        @Override
        public void enterFormalParameter(FormalParameterContext ctx) {
            String name = CSTNodes.getText(ctx.simpleName());
            ParameterSymbol symbol = SymbolFactory.createParameterSymbol(name, ctx, unit);
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
            String name;
            if (ctx.simpleName() != null) {
                name = ctx.simpleName().getText();
            } else {
                name = ctx.simpleNameOrPrimitiveType().getText();
            }
            ClassSymbol symbol = SymbolFactory.createClassSymbol(name, ctx, unit);
            enterSymbol(ctx, symbol);
            enterScope(new Scope(currentScope(), ctx));
        }

        @Override
        public void exitClassDeclaration(ClassDeclarationContext ctx) {
            exitScope();
        }

        @Override
        public void enterConstructorDeclaration(ConstructorDeclarationContext ctx) {
            String name = CSTNodes.getText(ctx.ZEN_CONSTRUCTOR());
            FunctionSymbol symbol = SymbolFactory.createFunctionSymbol(name, ctx, unit);
            enterSymbol(ctx, symbol);
            enterScope(new Scope(currentScope(), ctx));
        }

        @Override
        public void exitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            exitScope();
        }

        @Override
        public void enterVariableDeclaration(VariableDeclarationContext ctx) {
            String name = CSTNodes.getText(ctx.simpleName());
            VariableSymbol symbol = SymbolFactory.createVariableSymbol(name, ctx, unit);
            enterSymbol(ctx, symbol);
        }

        @Override
        public void enterOperatorFunctionDeclaration(OperatorFunctionDeclarationContext ctx) {
            String name = CSTNodes.getText(ctx.operator());
            OperatorFunctionSymbol symbol = SymbolFactory.createOperatorFunctionSymbol(name, ctx, unit);
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
            String name = CSTNodes.getText(ctx.simpleName());
            VariableSymbol symbol = SymbolFactory.createVariableSymbol(name, ctx, unit);
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
