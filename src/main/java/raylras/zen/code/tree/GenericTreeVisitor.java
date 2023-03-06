package raylras.zen.code.tree;

import raylras.zen.code.tree.expr.*;
import raylras.zen.code.tree.stmt.*;

import java.util.Collection;

/**
 * Base visitor class for accessing child nodes.
 * Subclasses visit child nodes by calling <code>super.visit()</code>.
 */
public class GenericTreeVisitor<R> implements TreeVisitor<R> {

    private void visit(TreeNode node) {
        if (node != null) node.accept(this);
    }

    private void visit(Collection<? extends TreeNode> nodes) {
        nodes.forEach(this::visit);
    }

    // Only the child nodes are visited.
    // Attempting to visit the node itself will cause StackOverflowError
    // unless it is an abstract class.

    @Override
    public R visitCompilationUnit(CompilationUnit node) {
        visit(node.imports);
        visit(node.classes);
        visit(node.functions);
        visit(node.statements);
        return null;
    }

    @Override
    public R visitImportDecl(ImportDecl node) {
        visit(node.fullName);
        visit(node.alias);
        return null;
    }

    @Override
    public R visitClassDecl(ClassDecl node) {
        visit(node.name);
        visit(node.fields);
        visit(node.constructors);
        visit(node.methods);
        return null;
    }

    @Override
    public R visitConstructorDecl(ConstructorDecl node) {
        visit(node.name);
        visit(node.params);
        visit(node.statements);
        return null;
    }

    @Override
    public R visitFunctionDecl(FunctionDecl node) {
        visit(node.name);
        visit(node.params);
        visit(node.returnType);
        visit(node.statements);
        return null;
    }

    @Override
    public R visitParameterDecl(ParameterDecl node) {
        visit(node.name);
        visit(node.typeDecl);
        visit(node.defaultValue);
        return null;
    }

    @Override
    public R visitName(Name node) {
        return null;
    }

    @Override
    public R visitTypeLiteral(TypeLiteral node) {
        return null;
    }

    @Override
    public R visitExpression(Expression node) {
        visit(node);
        return null;
    }

    @Override
    public R visitArrayAccess(ArrayAccess node) {
        visit(node.left);
        visit(node.index);
        return null;
    }

    @Override
    public R visitArrayLiteral(ArrayLiteral node) {
        visit(node.elements);
        return null;
    }

    @Override
    public R visitAssignment(Assignment node) {
        visit(node.left);
        visit(node.right);
        return null;
    }

    @Override
    public R visitBinary(Binary node) {
        visit(node.left);
        visit(node.right);
        return null;
    }

    @Override
    public R visitBracketHandler(BracketHandler node) {
        return null;
    }

    @Override
    public R visitCall(Call node) {
        visit(node.left);
        visit(node.args);
        return null;
    }

    @Override
    public R visitConstantExpr(ConstantExpr node) {
        return null;
    }

    @Override
    public R visitFunctionExpr(FunctionExpr node) {
        visit(node.params);
        visit(node.typeDecl);
        visit(node.statements);
        return null;
    }

    @Override
    public R visitIDExpr(IDExpr node) {
        visitName(node.name);
        return null;
    }

    @Override
    public R visitIntRange(IntRange node) {
        visit(node.from);
        visit(node.to);
        return null;
    }

    @Override
    public R visitMapLiteral(MapLiteral node) {
        visit(node.entries);
        return null;
    }

    @Override
    public R visitMapEntry(MapEntry node) {
        visit(node.key);
        visit(node.value);
        return null;
    }

    @Override
    public R visitMemberAccess(MemberAccess node) {
        visit(node.left);
        visit(node.right);
        return null;
    }

    @Override
    public R visitParens(Parens node) {
        visit(node.expr);
        return null;
    }

    @Override
    public R visitTernary(Ternary node) {
        visit(node.condition);
        visit(node.truePart);
        visit(node.falsePart);
        return null;
    }

    @Override
    public R visitThis(This node) {
        return null;
    }

    @Override
    public R visitSuper(Super node) {
        return null;
    }

    @Override
    public R visitTypeCast(TypeCast node) {
        visit(node.expr);
        visit(node.type);
        return null;
    }

    @Override
    public R visitUnary(Unary node) {
        visit(node.expr);
        return null;
    }

    @Override
    public R visitStatement(Statement node) {
        visit(node);
        return null;
    }

    @Override
    public R visitBlock(Block node) {
        visit(node.statements);
        return null;
    }

    @Override
    public R visitBreak(Break node) {
        return null;
    }

    @Override
    public R visitContinue(Continue node) {
        return null;
    }

    @Override
    public R visitExpressionStmt(ExpressionStmt node) {
        visit(node.expr);
        return null;
    }

    @Override
    public R visitForeach(Foreach node) {
        visit(node.variables);
        visit(node.expression);
        visit(node.statements);
        return null;
    }

    @Override
    public R visitIf(If node) {
        visit(node.condition);
        visit(node.thenPart);
        visit(node.elsePart);
        return null;
    }

    @Override
    public R visitReturn(Return node) {
        visit(node.expr);
        return null;
    }

    @Override
    public R visitVariableDecl(VariableDecl node) {
        visit(node.name);
        visit(node.typeDecl);
        visit(node.init);
        return null;
    }

    @Override
    public R visitWhile(While node) {
        visit(node.condition);
        visit(node.statement);
        return null;
    }

    @Override
    public R visitTreeNode(TreeNode node) {
        visit(node);
        return null;
    }

}
