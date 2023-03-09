package raylras.zen.code.resolve;

import raylras.zen.code.SourceUnit;
import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.VariableSymbol;
import raylras.zen.code.tree.*;
import raylras.zen.code.tree.expr.FunctionExpr;
import raylras.zen.code.tree.stmt.Block;
import raylras.zen.code.tree.stmt.Foreach;
import raylras.zen.code.tree.stmt.VariableDecl;
import raylras.zen.code.tree.stmt.While;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.Stack;

/**
 * Class used to resolve the scope and symbol for AST.
 */
public class ScopeResolver extends GenericTreeVisitor<Void> implements Resolver {

    private final Stack<LocalScope> stack = new ArrayStack<>();

    @Override
    public void resolve(SourceUnit sourceUnit) {
        visitCompilationUnit(sourceUnit.ast);
    }

    private void addSymbolToCurrentScope(Symbol symbol) {
        Scope currentScope = stack.peek();
        if (currentScope != null) {
            currentScope.add(symbol);
        }
    }

    @Override
    public Void visitCompilationUnit(CompilationUnit node) {
        node.localScope = new LocalScope(null, node);
        stack.push(node.localScope);
        super.visitCompilationUnit(node);
        stack.pop();
        return null;
    }

    @Override
    public Void visitImportDecl(ImportDecl node) {
        return null;
    }

    @Override
    public Void visitClassDecl(ClassDecl node) {
        node.symbol = new ClassSymbol(node.name.literal, stack.peek(), node);
        addSymbolToCurrentScope(node.symbol);
        node.localScope = new LocalScope(stack.peek(), node);
        stack.push(node.localScope);
        super.visitClassDecl(node);
        stack.pop();
        return null;
    }

    @Override
    public Void visitConstructorDecl(ConstructorDecl node) {
        node.symbol = new FunctionSymbol(node.name.literal, stack.peek(), node);
        addSymbolToCurrentScope(node.symbol);
        node.localScope = new LocalScope(stack.peek(), node);
        stack.push(node.localScope);
        super.visitConstructorDecl(node);
        stack.pop();
        return null;
    }

    @Override
    public Void visitFunctionDecl(FunctionDecl node) {
        node.symbol = new FunctionSymbol(node.name.literal, stack.peek(), node);
        addSymbolToCurrentScope(node.symbol);
        node.localScope = new LocalScope(stack.peek(), node);
        stack.push(node.localScope);
        super.visitFunctionDecl(node);
        stack.pop();
        return null;
    }

    @Override
    public Void visitParameterDecl(ParameterDecl node) {
        node.symbol = new VariableSymbol(node.name.literal, stack.peek(), node);
        addSymbolToCurrentScope(node.symbol);
        return null;
    }

    @Override
    public Void visitFunctionExpr(FunctionExpr node) {
        node.localScope = new LocalScope(stack.peek(), node);
        stack.push(node.localScope);
        super.visitFunctionExpr(node);
        stack.pop();
        return null;
    }

    @Override
    public Void visitBlock(Block node) {
        node.localScope = new LocalScope(stack.peek(), node);
        stack.push(node.localScope);
        super.visitBlock(node);
        stack.pop();
        return null;
    }

    @Override
    public Void visitForeach(Foreach node) {
        node.localScope = new LocalScope(stack.peek(), node);
        stack.push(node.localScope);
        super.visitForeach(node);
        stack.pop();
        return null;
    }

    @Override
    public Void visitVariableDecl(VariableDecl node) {
        node.symbol = new VariableSymbol(node.name.literal, stack.peek(), node);
        addSymbolToCurrentScope(node.symbol);
        return null;
    }

    @Override
    public Void visitWhile(While node) {
        node.localScope = new LocalScope(stack.peek(), node);
        stack.push(node.localScope);
        super.visitWhile(node);
        stack.pop();
        return null;
    }

}
