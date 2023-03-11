package raylras.zen.code.tree;

import raylras.zen.code.tree.expr.*;
import raylras.zen.code.tree.stmt.*;

/**
 * Base visitor for abstract syntax trees.
 * <p>
 * For each different concrete node has a pair of methods:
 * <ul>
 * <li><code>public boolean visit(<i>T</i> node)
 * <p> Visits the given node.
 * <p> if <code>true</code> is returned, the given node's
 * child nodes will be visited next.
 * <p> if <code>false</code> is returned, the given node's
 * children will not be visited.
 * </li>
 * <p>
 * <li><code>public void afterVisit(<i>T</i> node)</code>
 * <p> Called after the given node's children has been visited.
 * </li>
 * </ul>
 */
public abstract class TreeVisitor {

    public boolean visit(CompilationUnit node) {
        return true;
    }

    public void afterVisit(CompilationUnit node) {

    }

    public boolean visit(ImportDeclaration node) {
        return true;
    }

    public void afterVisit(ImportDeclaration node) {

    }

    public boolean visit(ClassDeclaration node) {
        return true;
    }

    public void afterVisit(ClassDeclaration node) {

    }

    public boolean visit(ConstructorDeclaration node) {
        return true;
    }

    public void afterVisit(ConstructorDeclaration node) {

    }

    public boolean visit(FunctionDeclaration node) {
        return true;
    }

    public void afterVisit(FunctionDeclaration node) {

    }

    public boolean visit(ParameterDeclaration node) {
        return true;
    }

    public void afterVisit(ParameterDeclaration node) {

    }

    public boolean visit(SimpleName node) {
        return true;
    }

    public void afterVisit(SimpleName node) {

    }

    public boolean visit(QualifiedName node) {
        return true;
    }

    public void afterVisit(QualifiedName node) {

    }

    public boolean visit(TypeLiteral node) {
        return true;
    }

    public void afterVisit(TypeLiteral node) {

    }

    public boolean visit(ArrayAccess node) {
        return true;
    }

    public void afterVisit(ArrayAccess node) {

    }

    public boolean visit(ArrayLiteral node) {
        return true;
    }

    public void afterVisit(ArrayLiteral node) {

    }

    public boolean visit(Assignment node) {
        return true;
    }

    public void afterVisit(Assignment node) {

    }

    public boolean visit(Binary node) {
        return true;
    }

    public void afterVisit(Binary node) {

    }

    public boolean visit(BracketHandler node) {
        return true;
    }

    public void afterVisit(BracketHandler node) {

    }

    public boolean visit(Call node) {
        return true;
    }

    public void afterVisit(Call node) {

    }

    public boolean visit(ConstantExpression node) {
        return true;
    }

    public void afterVisit(ConstantExpression node) {

    }

    public boolean visit(FunctionExpression node) {
        return true;
    }

    public void afterVisit(FunctionExpression node) {

    }

    public boolean visit(IntRange node) {
        return true;
    }

    public void afterVisit(IntRange node) {

    }

    public boolean visit(MapLiteral node) {
        return true;
    }

    public void afterVisit(MapLiteral node) {

    }

    public boolean visit(MapEntry node) {
        return true;
    }

    public void afterVisit(MapEntry node) {

    }

    public boolean visit(MemberAccess node) {
        return true;
    }

    public void afterVisit(MemberAccess node) {

    }

    public boolean visit(Parens node) {
        return true;
    }

    public void afterVisit(Parens node) {

    }

    public boolean visit(Ternary node) {
        return true;
    }

    public void afterVisit(Ternary node) {

    }

    public boolean visit(This node) {
        return true;
    }

    public void afterVisit(This node) {

    }

    public boolean visit(Super node) {
        return true;
    }

    public void afterVisit(Super node) {

    }

    public boolean visit(TypeCast node) {
        return true;
    }

    public void afterVisit(TypeCast node) {

    }

    public boolean visit(Unary node) {
        return true;
    }

    public void afterVisit(Unary node) {

    }

    public boolean visit(Block node) {
        return true;
    }

    public void afterVisit(Block node) {
    }

    public boolean visit(Break node) {
        return true;
    }

    public void afterVisit(Break node) {

    }

    public boolean visit(Continue node) {
        return true;
    }

    public void afterVisit(Continue node) {

    }

    public boolean visitExpressionStmt(ExpressionStmt node) {
        return true;
    }

    public void afterVisit(ExpressionStmt node) {

    }

    public boolean visit(Foreach node) {
        return true;
    }

    public void afterVisit(Foreach node) {

    }

    public boolean visit(If node) {
        return true;
    }

    public void afterVisit(If node) {

    }

    public boolean visit(Return node) {
        return true;
    }

    public void afterVisit(Return node) {

    }

    public boolean visit(VariableDeclaration node) {
        return true;
    }

    public void afterVisit(VariableDeclaration node) {

    }

    public boolean visit(While node) {
        return true;
    }

    public void afterVisit(While node) {

    }

}
