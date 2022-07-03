package raylras.zen.ast.visit;

import raylras.zen.ast.BlockNode;
import raylras.zen.ast.IDNode;
import raylras.zen.ast.ScriptNode;
import raylras.zen.ast.decl.*;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.stmt.*;

public class DefaultVisitor<T> implements NodeVisitor<T> {

    @Override
    public T visit(AliasDeclaration aliasDecl) {
        aliasDecl.getId().accept(this);
        return null;
    }

    @Override
    public T visit(BlockNode blockNode) {
        blockNode.getStatements().forEach(stmt -> stmt.accept(this));
        return null;
    }

    @Override
    public T visit(ConstructorDeclaration ctorDecl) {
        ctorDecl.getParameters().forEach(param -> param.accept(this));
        ctorDecl.getBlock().accept(this);
        return null;
    }

    @Override
    public T visit(FunctionDeclaration funcDecl) {
        funcDecl.getId().accept(this);
        funcDecl.getParameters().forEach(param -> param.accept(this));
        funcDecl.getBlock().accept(this);
        return null;
    }

    @Override
    public T visit(ImportDeclaration importDecl) {
        importDecl.getReference().accept(this);
        importDecl.getAlias().ifPresent(alias -> alias.accept(this));
        return null;
    }

    @Override
    public T visit(ParameterDeclaration paramDecl) {
        paramDecl.getId().accept(this);
        paramDecl.getDefaultValue().ifPresent(expr -> expr.accept(this));
        return null;
    }

    @Override
    public T visit(VariableDeclaration varDecl) {
        varDecl.getId().accept(this);
        return null;
    }

    @Override
    public T visit(ScriptNode scriptNode) {
        scriptNode.getImports().forEach(importDecl -> importDecl.accept(this));
        scriptNode.getFunctions().forEach(func -> func.accept(this));
        scriptNode.getZenClasses().forEach(zenClass -> zenClass.accept(this));
        scriptNode.getStatements().forEach(stmt -> stmt.accept(this));
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
    public T visit(ZenClassDeclaration classDecl) {
        classDecl.getId().accept(this);
        classDecl.getProperties().forEach(prop -> prop.accept(this));
        classDecl.getConstructors().forEach(ctor -> ctor.accept(this));
        classDecl.getFunctions().forEach(func -> func.accept(this));
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
        exprStmt.getExpr().accept(this);
        return null;
    }

    @Override
    public T visit(ForeachStatement foreachStmt) {
        foreachStmt.getVariables().forEach(variable -> variable.accept(this));
        foreachStmt.getExpr().accept(this);
        foreachStmt.getBlock().accept(this);
        return null;
    }

    @Override
    public T visit(IfElseStatement ifStmt) {
        ifStmt.getCondition().accept(this);
        ifStmt.getThenStmt().accept(this);
        ifStmt.getElseStmt().ifPresent(elseStmt -> elseStmt.accept(this));
        return null;
    }

    @Override
    public T visit(ReturnStatement returnStmt) {
        returnStmt.getExpr().ifPresent(expr -> expr.accept(this));
        return null;
    }

    @Override
    public T visit(VariableDeclStatement varDeclStmt) {
        varDeclStmt.getExpr().ifPresent(expr -> expr.accept(this));
        return null;
    }

    @Override
    public T visit(WhileStatement whileStmt) {
        whileStmt.getCondition().accept(this);
        whileStmt.getBlock().accept(this);
        return null;
    }

    @Override
    public T visit(Expression expr) {
        expr.getChildren().forEach(child -> child.accept(this));
        return null;
    }

    @Override
    public T visit(ArgumentsExpression argsExpr) {
        argsExpr.getLeft().accept(this);
        argsExpr.getArguments().forEach(expr -> expr.accept(this));
        return null;
    }

    @Override
    public T visit(ArrayLiteral arrayExpr) {
        arrayExpr.getElements().forEach(expr -> expr.accept(this));
        return null;
    }

    @Override
    public T visit(AssignmentExpression assignExpr) {
        assignExpr.getLeft().accept(this);
        assignExpr.getRight().accept(this);
        return null;
    }

    @Override
    public T visit(BinaryExpression binaryExpr) {
        binaryExpr.getLeft().accept(this);
        binaryExpr.getRight().accept(this);
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
        funcExpr.getParameters().forEach(param -> param.accept(this));
        funcExpr.getBlock().accept(this);
        return null;
    }

    @Override
    public T visit(IntLiteral intExpr) {
        return null;
    }

    @Override
    public T visit(VarAccessExpression varAccess) {
        return null;
    }

    @Override
    public T visit(MapEntryExpression entryExpr) {
        entryExpr.getKey().accept(this);
        entryExpr.getValue().accept(this);
        return null;
    }

    @Override
    public T visit(MapLiteral mapExpr) {
        mapExpr.getEntries().forEach(entry -> entry.accept(this));
        return null;
    }

    @Override
    public T visit(MemberAccess memberAccess) {
        memberAccess.getLeft().accept(this);
        return null;
    }

    @Override
    public T visit(MemberIndexExpression memberIndex) {
        memberIndex.getLeft().accept(this);
        memberIndex.getIndex().accept(this);
        return null;
    }

    @Override
    public T visit(NullExpression nullExpr) {
        return null;
    }

    @Override
    public T visit(ParensExpression parensExpr) {
        parensExpr.getExpr().accept(this);
        return null;
    }

    @Override
    public T visit(RangeExpression rangeExpr) {
        rangeExpr.getFrom().accept(this);
        rangeExpr.getTo().accept(this);
        return null;
    }

    @Override
    public T visit(StringLiteral stringExpr) {
        return null;
    }

    @Override
    public T visit(TernaryExpression ternaryExpr) {
        ternaryExpr.getCondition().accept(this);
        return null;
    }

    @Override
    public T visit(ThisExpression thisExpr) {
        return null;
    }

    @Override
    public T visit(TypeCastExpression castExpr) {
        castExpr.getExpr().accept(this);
        return null;
    }

    @Override
    public T visit(UnaryExpression unaryExpr) {
        unaryExpr.getExpr().accept(this);
        return null;
    }

}
