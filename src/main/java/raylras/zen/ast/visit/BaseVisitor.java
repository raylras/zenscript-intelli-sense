package raylras.zen.ast.visit;

import raylras.zen.ast.BlockNode;
import raylras.zen.ast.IDNode;
import raylras.zen.ast.ScriptNode;
import raylras.zen.ast.decl.*;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.stmt.*;

public class BaseVisitor<T> implements NodeVisitor<T> {

    @Override
    public T visit(ScriptNode scriptNode) {
        return null;
    }

    @Override
    public T visit(IDNode idNode) {
        return null;
    }

    @Override
    public T visit(TypeDeclaration typeDecl) {
        return null;
    }

    @Override
    public T visit(BlockNode blockNode) {
        return null;
    }

    @Override
    public T visit(AliasDeclaration aliasDecl) {
        return null;
    }

    @Override
    public T visit(ConstructorDeclaration ctorDecl) {
        return null;
    }

    @Override
    public T visit(FunctionDeclaration funcDecl) {
        return null;
    }

    @Override
    public T visit(ImportDeclaration importDecl) {
        return null;
    }

    @Override
    public T visit(ParameterDeclaration paramDecl) {
        return null;
    }

    @Override
    public T visit(VariableDeclaration varDecl) {
        return null;
    }

    @Override
    public T visit(ZenClassDeclaration classDecl) {
        return null;
    }

    @Override
    public T visit(BreakStatement breakStmt) {
        return null;
    }

    @Override
    public T visit(ContinueStatement contStmt) {
        return null;
    }

    @Override
    public T visit(ExpressionStatement exprStmt) {
        return null;
    }

    @Override
    public T visit(ForeachStatement foreachStmt) {
        return null;
    }

    @Override
    public T visit(IfElseStatement ifStmt) {
        return null;
    }

    @Override
    public T visit(ReturnStatement returnStmt) {
        return null;
    }

    @Override
    public T visit(VariableDeclStatement varDeclStmt) {
        return null;
    }

    @Override
    public T visit(WhileStatement whileStmt) {
        return null;
    }

    @Override
    public T visit(Expression expr) {
        return null;
    }

    @Override
    public T visit(ArgumentsExpression argsExpr) {
        return null;
    }

    @Override
    public T visit(ArrayLiteral arrayExpr) {
        return null;
    }

    @Override
    public T visit(AssignmentExpression assignExpr) {
        return null;
    }

    @Override
    public T visit(BinaryExpression binaryExpr) {
        return null;
    }

    @Override
    public T visit(BoolLiteral boolExpr) {
        return null;
    }

    @Override
    public T visit(BracketHandler bracketExpr) {
        return null;
    }

    @Override
    public T visit(FloatLiteral floatExpr) {
        return null;
    }

    @Override
    public T visit(FunctionExpression funcExpr) {
        return null;
    }

    @Override
    public T visit(IntLiteral intExpr) {
        return null;
    }

    @Override
    public T visit(MapEntryExpression entryExpr) {
        return null;
    }

    @Override
    public T visit(MapLiteral mapExpr) {
        return null;
    }

    @Override
    public T visit(MemberAccess memberAccess) {
        return null;
    }

    @Override
    public T visit(MemberIndexExpression memberIndex) {
        return null;
    }

    @Override
    public T visit(NullExpression nullExpr) {
        return null;
    }

    @Override
    public T visit(ParensExpression parensExpr) {
        return null;
    }

    @Override
    public T visit(RangeExpression rangeExpr) {
        return null;
    }

    @Override
    public T visit(StringLiteral stringExpr) {
        return null;
    }

    @Override
    public T visit(TernaryExpression ternaryExpr) {
        return null;
    }

    @Override
    public T visit(ThisExpression thisExpr) {
        return null;
    }

    @Override
    public T visit(TypeCastExpression castExpr) {
        return null;
    }

    @Override
    public T visit(UnaryExpression unaryExpr) {
        return null;
    }

    @Override
    public T visit(VarAccessExpression varAccess) {
        return null;
    }

}
