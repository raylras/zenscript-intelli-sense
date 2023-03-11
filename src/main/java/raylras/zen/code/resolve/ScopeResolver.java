package raylras.zen.code.resolve;

import raylras.zen.code.SourceUnit;
import raylras.zen.code.scope.LocalScope;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.VariableSymbol;
import raylras.zen.code.tree.*;
import raylras.zen.code.tree.expr.FunctionExpression;
import raylras.zen.code.tree.expr.MemberAccess;
import raylras.zen.code.tree.stmt.Block;
import raylras.zen.code.tree.stmt.Foreach;
import raylras.zen.code.tree.stmt.VariableDeclaration;
import raylras.zen.code.tree.stmt.While;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.Stack;

/**
 * Class used to resolve the scope and symbol for AST.
 */
public class ScopeResolver extends TreeVisitor implements Resolver {

    private final Stack<LocalScope> stack = new ArrayStack<>();

    @Override
    public void resolve(SourceUnit sourceUnit) {
        sourceUnit.ast.accept(this);
    }

    private void enterSymbol(Symbol symbol) {
        Scope currentScope = stack.peek();
        if (currentScope == null) return;
        currentScope.add(symbol);
    }

    private void enterScope(LocalScope scope) {
        stack.push(scope);
    }

    private void exitScope() {
        stack.pop();
    }

    @Override
    public boolean visit(CompilationUnit node) {
        node.localScope = new LocalScope(null, node);
        enterScope(node.localScope);
        return true;
    }

    @Override
    public void afterVisit(CompilationUnit node) {
        exitScope();
    }

    @Override
    public boolean visit(ImportDeclaration node) {
        // TODO: handle imports
        return false;
    }

    @Override
    public boolean visit(ClassDeclaration node) {
        node.symbol = new ClassSymbol(node.name.literal, stack.peek(), node);
        enterSymbol(node.symbol);
        node.localScope = new LocalScope(stack.peek(), node);
        enterScope(node.localScope);
        return true;
    }

    @Override
    public void afterVisit(ClassDeclaration node) {
        exitScope();
    }

    @Override
    public boolean visit(ConstructorDeclaration node) {
        node.symbol = new FunctionSymbol(node.name.literal, stack.peek(), node);
        enterSymbol(node.symbol);
        node.localScope = new LocalScope(stack.peek(), node);
        enterScope(node.localScope);
        return true;
    }

    @Override
    public void afterVisit(ConstructorDeclaration node) {
        exitScope();
    }

    @Override
    public boolean visit(FunctionDeclaration node) {
        node.symbol = new FunctionSymbol(node.name.literal, stack.peek(), node);
        enterSymbol(node.symbol);
        node.localScope = new LocalScope(stack.peek(), node);
        enterScope(node.localScope);
        return true;
    }

    @Override
    public void afterVisit(FunctionDeclaration node) {
        exitScope();
    }

    @Override
    public boolean visit(ParameterDeclaration node) {
        node.symbol = new VariableSymbol(node.name.literal, stack.peek(), node);
        enterSymbol(node.symbol);
        return true;
    }

    @Override
    public boolean visit(FunctionExpression node) {
        node.localScope = new LocalScope(stack.peek(), node);
        enterScope(node.localScope);
        return true;
    }

    @Override
    public void afterVisit(FunctionExpression node) {
        exitScope();
    }

    @Override
    public boolean visit(Block node) {
        node.localScope = new LocalScope(stack.peek(), node);
        enterScope(node.localScope);
        return true;
    }

    @Override
    public void afterVisit(Block node) {
        exitScope();
    }

    @Override
    public boolean visit(Foreach node) {
        node.localScope = new LocalScope(stack.peek(), node);
        enterScope(node.localScope);
        return true;
    }

    @Override
    public void afterVisit(Foreach node) {
        exitScope();
    }

    @Override
    public boolean visit(VariableDeclaration node) {
        node.symbol = new VariableSymbol(node.name.literal, stack.peek(), node);
        enterSymbol(node.symbol);
        return true;
    }

    @Override
    public boolean visit(While node) {
        node.localScope = new LocalScope(stack.peek(), node);
        enterScope(node.localScope);
        return true;
    }

    @Override
    public void afterVisit(While node) {
        exitScope();
    }

}
