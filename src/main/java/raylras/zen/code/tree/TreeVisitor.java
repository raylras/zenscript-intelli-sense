package raylras.zen.code.tree;

import raylras.zen.code.tree.expr.*;
import raylras.zen.code.tree.stmt.*;

/**
 * Visitor interface for Abstract Syntax Tree (AST) nodes.
 *
 * @param <R> Type of the return value.
 */
public interface TreeVisitor<R> {

    R visitCompilationUnit(CompilationUnit node);

    R visitImportDecl(ImportDecl node);

    R visitClassDecl(ClassDecl node);

    R visitConstructorDecl(ConstructorDecl node);

    R visitFunctionDecl(FunctionDecl node);

    R visitParameterDecl(ParameterDecl node);

    R visitName(Name node);

    R visitTypeLiteral(TypeLiteral node);

    R visitExpression(Expression node);

    R visitArrayAccess(ArrayAccess node);

    R visitArrayLiteral(ArrayLiteral node);

    R visitAssignment(Assignment node);

    R visitBinaryExpr(Binary node);

    R visitBracketHandler(BracketHandler node);

    R visitCall(Call node);

    R visitConstantExpr(ConstantExpr node);

    R visitFunctionExpr(FunctionExpr node);

    R visitIDExpr(IDExpr node);

    R visitIntRange(IntRange node);

    R visitMapLiteral(MapLiteral node);

    R visitMapEntry(MapEntry node);

    R visitMemberAccess(MemberAccess node);

    R visitParens(Parens node);

    R visitTernary(Ternary node);

    R visitThis(This node);

    R visitSuper(Super node);

    R visitTypeCast(TypeCast node);

    R visitUnary(Unary node);

    R visitStatement(Statement node);

    R visitBlock(Block node);

    R visitBreak(Break node);

    R visitContinue(Continue node);

    R visitExpressionStmt(ExpressionStmt node);

    R visitForeach(Foreach node);

    R visitIf(If node);

    R visitReturn(Return node);

    R visitVariableDecl(VariableDecl node);

    R visitWhile(While node);

    R visitTreeNode(TreeNode node);
}
