package raylras.zen.ast;

import raylras.zen.ast.decl.*;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.stmt.*;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Node;
import raylras.zen.ast.type.TopLevel;

public abstract class ASTNodeBaseVisitor<T> implements ASTNodeVisitor<T> {

    private void visitChildren(Node node) {
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public T visit(CompilationUnitNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(TopLevel node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(TypeAnnotationNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(ConstructorDeclarationNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(FunctionDeclarationNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(ImportDeclarationNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(AliasNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(ParameterDeclarationNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(VariableDeclarationNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(BlockStatementNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(ZenClassDeclarationNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(BreakStatementNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(ContinueStatementNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(ExpressionStatementNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(ForeachStatementNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(IfElseStatementNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(ReturnStatementNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(WhileStatementNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(Expression node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(CallExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(ArrayLiteralExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(BinaryExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(NumericLiteralExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(BoolLiteralNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(StringLiteralExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(BracketHandlerExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(FunctionExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(MapEntryNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(MapLiteralExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(MemberAccessExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(MemberIndexExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(NullLiteralExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(ParensExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(IntRangeExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(TernaryExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(ThisExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(TypeAssertionExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(UnaryExpressionNode node) {
        visitChildren(node);
        return null;
    }

    @Override
    public T visit(TypeNameNode node) {
        visitChildren(node);
        return null;
    }

}
