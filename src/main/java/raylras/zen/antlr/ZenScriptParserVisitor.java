// Generated from ZenScriptParser.g4 by ANTLR 4.10.1
package raylras.zen.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ZenScriptParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ZenScriptParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#scriptUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScriptUnit(ZenScriptParser.ScriptUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#reference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReference(ZenScriptParser.ReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#aliasDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAliasDeclaration(ZenScriptParser.AliasDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#formalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameter(ZenScriptParser.FormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#defaultValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultValue(ZenScriptParser.DefaultValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#zenClassDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ZenScriptParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ZenScriptParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(ZenScriptParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(ZenScriptParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(ZenScriptParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(ZenScriptParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#ifElseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElseStatement(ZenScriptParser.IfElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#foreachStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForeachStatement(ZenScriptParser.ForeachStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(ZenScriptParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#variableDeclStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclStatement(ZenScriptParser.VariableDeclStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(ZenScriptParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MemberAccessExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccessExpression(ZenScriptParser.MemberAccessExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapLiteralExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapLiteralExpression(ZenScriptParser.MapLiteralExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BracketHandlerExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketHandlerExpression(ZenScriptParser.BracketHandlerExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypeCastExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeCastExpression(ZenScriptParser.TypeCastExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpression(ZenScriptParser.LiteralExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayLiteralExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLiteralExpression(ZenScriptParser.ArrayLiteralExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(ZenScriptParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RangeExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeExpression(ZenScriptParser.RangeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MemberIndexExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberIndexExpression(ZenScriptParser.MemberIndexExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParensExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParensExpression(ZenScriptParser.ParensExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArgumentsExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentsExpression(ZenScriptParser.ArgumentsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ThisExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisExpression(ZenScriptParser.ThisExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionExpression(ZenScriptParser.FunctionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpression(ZenScriptParser.BinaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpression(ZenScriptParser.AssignmentExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarAccessExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarAccessExpression(ZenScriptParser.VarAccessExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TrinaryExpression}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrinaryExpression(ZenScriptParser.TrinaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#mapEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapEntry(ZenScriptParser.MapEntryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BuiltinType}
	 * labeled alternative in {@link ZenScriptParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBuiltinType(ZenScriptParser.BuiltinTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayType}
	 * labeled alternative in {@link ZenScriptParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(ZenScriptParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionType}
	 * labeled alternative in {@link ZenScriptParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionType(ZenScriptParser.FunctionTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListType}
	 * labeled alternative in {@link ZenScriptParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListType(ZenScriptParser.ListTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReferenceType}
	 * labeled alternative in {@link ZenScriptParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceType(ZenScriptParser.ReferenceTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link ZenScriptParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapType(ZenScriptParser.MapTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#builtin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBuiltin(ZenScriptParser.BuiltinContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#argumentTypeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentTypeList(ZenScriptParser.ArgumentTypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ZenScriptParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(ZenScriptParser.IdentifierContext ctx);
}