package raylras.zen.ast.visit;

import raylras.zen.ast.BlockNode;
import raylras.zen.ast.IDNode;
import raylras.zen.ast.ScriptNode;
import raylras.zen.ast.decl.*;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.stmt.*;

public interface NodeVisitor<T> {

    T visit(ScriptNode scriptNode);

    T visit(IDNode idNode);

    T visit(TypeDeclaration typeDecl);

    T visit(BlockNode blockNode);

    T visit(AliasDeclaration aliasDecl);

    T visit(ConstructorDeclaration ctorDecl);

    T visit(FunctionDeclaration funcDecl);

    T visit(ImportDeclaration importDecl);

    T visit(ParameterDeclaration paramDecl);

    T visit(VariableDeclaration varDecl);

    T visit(ZenClassDeclaration classDecl);

    T visit(BreakStatement breakStmt);

    T visit(ContinueStatement contStmt);

    T visit(ExpressionStatement exprStmt);

    T visit(ForeachStatement foreachStmt);

    T visit(IfElseStatement ifStmt);

    T visit(ReturnStatement returnStmt);

    T visit(VariableDeclStatement varDeclStmt);

    T visit(WhileStatement whileStmt);

    T visit(Expression expr);

    T visit(ArgumentsExpression argsExpr);

    T visit(ArrayLiteral arrayExpr);

    T visit(AssignmentExpression assignExpr);

    T visit(BinaryExpression binaryExpr);

    T visit(BoolLiteral boolExpr);

    T visit(BracketHandler bracketExpr);

    T visit(FloatLiteral floatExpr);

    T visit(FunctionExpression funcExpr);

    T visit(IntLiteral intExpr);

    T visit(MapEntryExpression entryExpr);

    T visit(MapLiteral mapExpr);

    T visit(MemberAccess memberAccess);

    T visit(MemberIndexExpression memberIndex);

    T visit(NullExpression nullExpr);

    T visit(ParensExpression parensExpr);

    T visit(RangeExpression rangeExpr);

    T visit(StringLiteral stringExpr);

    T visit(TernaryExpression ternaryExpr);

    T visit(ThisExpression thisExpr);

    T visit(TypeCastExpression castExpr);

    T visit(UnaryExpression unaryExpr);

    T visit(VarAccessExpression varAccess);

}
