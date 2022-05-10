package raylras.zen.ast;

import raylras.zen.ast.expr.*;
import raylras.zen.ast.stmt.*;

public interface ASTVisitor {

    void visitAlias(AliasNode alias);

    void visitBlock(BlockNode block);

    void visitClassReference(ClassReferenceNode classReference);

    void visitConstructor(ConstructorNode constructor);

    void visitCrossScriptReference(CrossScriptReferenceNode scriptReference);

    void visitField(FieldNode field);

    void visitFunction(FunctionNode function);

    void visitIdentifier(IdentifierNode id);

    void visitImport(ImportNode importNode);

    void visitParameter(ParameterNode parameter);

    void visitReference(ReferenceNode reference);

    void visitScriptNode(ScriptNode scriptUnit);

    void visitType(TypeNode type);

    void visitVariable(VariableNode variable);

    void visitZenClass(ZenClassNode zenClass);

    //
    // Statement
    //

    void visitBlockStatement(BlockStatement blockStmt);

    void visitBreakStatement(BreakStatement breakStmt);

    void visitContinueStatement(ContinueStatement continueStmt);

    void visitExpressionStatement(ExpressionStatement expressionStmt);

    void visitForeachStatement(ForeachStatement foreachStmt);

    void visitIfStatement(IfStatement ifStmt);

    void visitImportStatement(ImportStatement importStmt);

    void visitReturnStatement(ReturnStatement returnStmt);

    void visitStatement(Statement statement);

    void visitVarStatement(VarStatement varStmt);

    void visitWhileStatement(WhileStatement whileStmt);

    //
    // Expression
    //

    void visitArgumentsExpression(ArgumentsExpression argumentsExpr);

    void visitArrayLiteralExpression(ArrayLiteralExpression arrayLiteralExpr);

    void visitAssignmentExpression(AssignmentExpression assignmentExpr);

    void visitBinaryExpression(BinaryExpression binaryExpr);

    void visitBooleanLiteralExpression(BooleanLiteralExpression boolLiteralExpr);

    void visitBracketHandlerExpression(BracketHandlerExpression bracketExpr);

    void visitExpression(Expression expression);

    void visitFunctionExpression(FunctionExpression functionExpr);

    void visitIdentifierExpression(IdentifierExpression idExpr);

    void visitMapEntryExpression(MapEntryExpression entryExpr);

    void visitMapLiteralExpression(MapLiteralExpression mapLiteralExpr);

    void visitMemberAccessExpression(MemberAccessExpression memberAccessExpr);

    void visitMemberIndexExpression(MemberIndexExpression memberIndexExpr);

    void visitNullExpression(NullExpression nullExpr);

    void visitNumberLiteralExpression(NumberLiteralExpression numberLiteralExpr);

    void visitParensExpression(ParensExpression parensExpr);

    void visitRangeExpression(RangeExpression rangeExpr);

    void visitStringLiteralExpression(StringLiteralExpression stringLiteralExpr);

    void visitTernaryExpression(TernaryExpression ternaryExpr);

    void visitThisExpression(ThisExpression thisExpr);

    void visitTypeCastExpression(TypeCastExpression typeCastExpr);

    void visitUnaryExpression(UnaryExpression unaryExpr);

}
