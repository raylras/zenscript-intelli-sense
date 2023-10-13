package raylras.zen.model.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.Visitor;
import raylras.zen.model.parser.ZenScriptParser.*;
import raylras.zen.model.scope.Scope;
import raylras.zen.model.symbol.*;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.Stack;

import java.util.List;
import java.util.function.Consumer;

public final class DeclarationResolver {

    private DeclarationResolver() {}

    public static void resolveDeclarations(CompilationUnit unit) {
        unit.accept(new DeclarationVisitor(unit));
    }

    private static class DeclarationVisitor extends Visitor<Void> {
        final CompilationUnit unit;
        final Stack<Scope> scopeStack = new ArrayStack<>();
        final Stack<ClassSymbol> classStack = new ArrayStack<>();

        DeclarationVisitor(CompilationUnit unit) {
            this.unit = unit;
        }

        void enterScope(RuleNode cst) {
            enterScopeThen(cst, scope -> {/* do nothing */});
        }

        void enterScopeThen(RuleNode cst, Consumer<Scope> then) {
            Scope scope = new Scope(scopeStack.peek(), cst);
            unit.addScope(scope);
            scopeStack.push(scope);
            try {
                then.accept(scope);
                visitChildren(cst);
            } finally {
                scopeStack.pop();
            }
        }

        void enterClassThen(ClassSymbol symbol, Runnable then) {
            classStack.push(symbol);
            try {
                then.run();
            } finally {
                classStack.pop();
            }
        }

        ClassSymbol currentClass() {
            return classStack.peek();
        }

        void enterSymbol(ParseTree cst, Symbol symbol) {
            unit.putSymbol(cst, symbol);
            scopeStack.peek().addSymbol(symbol);
        }

        @Override
        public Void visitCompilationUnit(CompilationUnitContext ctx) {
            enterScope(ctx);
            return null;
        }

        @Override
        public Void visitImportDeclaration(ImportDeclarationContext ctx) {
            SimpleNameContext name;
            if (ctx.alias() != null) {
                name = ctx.alias().simpleName();
            } else if (ctx.qualifiedName() != null) {
                List<SimpleNameContext> simpleNameList = ctx.qualifiedName().simpleName();
                name = simpleNameList.get(simpleNameList.size() - 1);
            } else {
                name = null;
            }

            if (name != null) {
                ImportSymbol symbol = SymbolFactory.createImportSymbol(name, ctx, unit);
                unit.addImport(symbol);
            }
            return null;
        }

        @Override
        public Void visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            FunctionSymbol symbol = SymbolFactory.createFunctionSymbol(ctx.simpleName(), ctx, unit);
            enterSymbol(ctx, symbol);
            enterScope(ctx);
            return null;
        }

        @Override
        public Void visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            if (ctx.simpleName() != null) {
                ExpandFunctionSymbol symbol = SymbolFactory.createExpandFunctionSymbol(ctx, unit);
                enterSymbol(ctx, symbol);
                enterScopeThen(ctx, scope -> scope.addSymbol(SymbolFactory.createThisSymbol(symbol::getExpandingType)));
            }
            return null;
        }

        @Override
        public Void visitFormalParameter(FormalParameterContext ctx) {
            if (ctx.simpleName() != null) {
                ParameterSymbol symbol = SymbolFactory.createParameterSymbol(ctx, unit);
                enterSymbol(ctx, symbol);
            }
            return null;
        }

        @Override
        public Void visitFunctionBody(FunctionBodyContext ctx) {
            enterScope(ctx);
            return null;
        }

        @Override
        public Void visitClassDeclaration(ClassDeclarationContext ctx) {
            ParseTree name;
            if (ctx.simpleName() != null) {
                name = ctx.simpleName();
            } else {
                name = ctx.simpleNameOrPrimitiveType();
            }

            if (name != null) {
                ClassSymbol symbol = SymbolFactory.createClassSymbol(name, ctx, unit);
                enterSymbol(ctx, symbol);
                enterClassThen(symbol, () -> {
                    enterScopeThen(ctx, scope -> {
                        scope.addSymbol(SymbolFactory.createThisSymbol(symbol::getType));
                    });
                });
            }
            return null;
        }

        @Override
        public Void visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            ConstructorSymbol symbol = SymbolFactory.createConstructorSymbol(ctx, unit, currentClass());
            enterSymbol(ctx, symbol);
            enterScope(ctx);
            return null;
        }

        @Override
        public Void visitVariableDeclaration(VariableDeclarationContext ctx) {
            if (ctx.simpleName() != null) {
                VariableSymbol symbol = SymbolFactory.createVariableSymbol(ctx.simpleName(), ctx, unit);
                enterSymbol(ctx, symbol);
            }
            return null;
        }

        @Override
        public Void visitOperatorFunctionDeclaration(OperatorFunctionDeclarationContext ctx) {
            if (ctx.operator() != null) {
                OperatorFunctionSymbol symbol = SymbolFactory.createOperatorFunctionSymbol(ctx, unit);
                enterSymbol(ctx, symbol);
                enterScope(ctx);
            }
            return null;
        }

        @Override
        public Void visitThenPart(ThenPartContext ctx) {
            enterScope(ctx);
            return null;
        }

        @Override
        public Void visitElsePart(ElsePartContext ctx) {
            enterScope(ctx);
            return null;
        }

        @Override
        public Void visitForeachStatement(ForeachStatementContext ctx) {
            enterScope(ctx);
            return null;
        }

        @Override
        public Void visitForeachVariable(ForeachVariableContext ctx) {
            if (ctx.simpleName() != null) {
                VariableSymbol symbol = SymbolFactory.createVariableSymbol(ctx.simpleName(), ctx, unit);
                enterSymbol(ctx, symbol);
            }
            return null;
        }

        @Override
        public Void visitWhileStatement(WhileStatementContext ctx) {
            enterScope(ctx);
            return null;
        }

        @Override
        public Void visitFunctionExpr(FunctionExprContext ctx) {
            enterScope(ctx);
            return null;
        }
    }

}
