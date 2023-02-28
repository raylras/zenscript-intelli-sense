package raylras.zen.code.resolve;

import raylras.zen.code.CompilationContext;
import raylras.zen.code.SourceUnit;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.tree.*;
import raylras.zen.code.tree.expr.FunctionExpr;
import raylras.zen.code.tree.stmt.*;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.Stack;

/**
 * Class used to resolve the scope and symbol for AST.
 */
public class ScopeResolver extends GenericTreeVisitor<Void> implements Resolver {

    private final Stack<Scope> stack = new ArrayStack<>();

    @Override
    public void resolve(SourceUnit sourceUnit) {
        visitCompilationUnit(sourceUnit.ast);
        // TODO
    }

    @Override
    public void resolve(CompilationContext context) {

    }

    private void addSymbolToCurrentScope(Symbol symbol) {
        Scope currentScope = stack.peek();
        if (currentScope != null) {
            currentScope.add(symbol);
        }
    }

    @Override
    public Void visitCompilationUnit(CompilationUnit node) {
        return null;
    }

    @Override
    public Void visitImportDecl(ImportDecl node) {
        return null;
    }

    @Override
    public Void visitClassDecl(ClassDecl node) {
        return null;
    }

    @Override
    public Void visitConstructorDecl(ConstructorDecl node) {
        return null;
    }

    @Override
    public Void visitFunctionDecl(FunctionDecl node) {
        return null;
    }

    @Override
    public Void visitParameterDecl(ParameterDecl node) {
        return null;
    }


    @Override
    public Void visitFunctionExpr(FunctionExpr node) {
        return null;
    }

    @Override
    public Void visitBlock(Block node) {
        return null;
    }

    @Override
    public Void visitForeach(Foreach node) {
        return null;
    }

    @Override
    public Void visitIf(If node) {
        return null;
    }

    @Override
    public Void visitVariableDecl(VariableDecl node) {
        return null;
    }

    @Override
    public Void visitWhile(While node) {
        return null;
    }

}
