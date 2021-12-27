// Generated from D:/Projects/Forge/1.12.2/ZenServer/src/main/resources\ZenScriptParser.g4 by ANTLR 4.9.2
package raylras.zen.lsp.antlr;
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
	 * Visit a parse tree produced by {@link ZenScriptParser#script}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScript(ZenScriptParser.ScriptContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#importStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStatement(ZenScriptParser.ImportStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#zenClassDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(ZenScriptParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#constructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructor(ZenScriptParser.ConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(ZenScriptParser.FieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(ZenScriptParser.MethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclaration(ZenScriptParser.LocalVariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#globalVariableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalVariableDeclaration(ZenScriptParser.GlobalVariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#asType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsType(ZenScriptParser.AsTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#memberCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberCall(ZenScriptParser.MemberCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#methodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCall(ZenScriptParser.MethodCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#anonymousFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnonymousFunction(ZenScriptParser.AnonymousFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#formalParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameters(ZenScriptParser.FormalParametersContext ctx);
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
	 * Visit a parse tree produced by {@link ZenScriptParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(ZenScriptParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(ZenScriptParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ZenScriptParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(ZenScriptParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#map}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMap(ZenScriptParser.MapContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#mapEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapEntry(ZenScriptParser.MapEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#mapKey}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapKey(ZenScriptParser.MapKeyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#mapValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapValue(ZenScriptParser.MapValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(ZenScriptParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ZenScriptParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(ZenScriptParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(ZenScriptParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#doWhileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileStatement(ZenScriptParser.DoWhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#forControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForControl(ZenScriptParser.ForControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#range}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRange(ZenScriptParser.RangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#bounds}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBounds(ZenScriptParser.BoundsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#packageName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageName(ZenScriptParser.PackageNameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionArrayGet}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionArrayGet(ZenScriptParser.ExpressionArrayGetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionReturn}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionReturn(ZenScriptParser.ExpressionReturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionFunction}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionFunction(ZenScriptParser.ExpressionFunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionUnary}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionUnary(ZenScriptParser.ExpressionUnaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionAssign}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionAssign(ZenScriptParser.ExpressionAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionTrinary}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionTrinary(ZenScriptParser.ExpressionTrinaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionID}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionID(ZenScriptParser.ExpressionIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionAnd}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionAnd(ZenScriptParser.ExpressionAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionCat}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionCat(ZenScriptParser.ExpressionCatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionAndAnd}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionAndAnd(ZenScriptParser.ExpressionAndAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionBracketHandler}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionBracketHandler(ZenScriptParser.ExpressionBracketHandlerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionBreak}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionBreak(ZenScriptParser.ExpressionBreakContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionCompare}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionCompare(ZenScriptParser.ExpressionCompareContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionContinue}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionContinue(ZenScriptParser.ExpressionContinueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionParens}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionParens(ZenScriptParser.ExpressionParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionInstanceof}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionInstanceof(ZenScriptParser.ExpressionInstanceofContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionXor}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionXor(ZenScriptParser.ExpressionXorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionMap}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionMap(ZenScriptParser.ExpressionMapContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionBinary}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionBinary(ZenScriptParser.ExpressionBinaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionLiteral}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionLiteral(ZenScriptParser.ExpressionLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionCall}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionCall(ZenScriptParser.ExpressionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionArray}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionArray(ZenScriptParser.ExpressionArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionOrOr}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionOrOr(ZenScriptParser.ExpressionOrOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionOr}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionOr(ZenScriptParser.ExpressionOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionIn}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionIn(ZenScriptParser.ExpressionInContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ZenScriptParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#typeFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeFunction(ZenScriptParser.TypeFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#typePrimitive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypePrimitive(ZenScriptParser.TypePrimitiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#typeArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArray(ZenScriptParser.TypeArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#typeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeList(ZenScriptParser.TypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#typeMap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeMap(ZenScriptParser.TypeMapContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#typeClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeClass(ZenScriptParser.TypeClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ZenScriptParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#integerLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(ZenScriptParser.IntegerLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZenScriptParser#bracketHandler}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketHandler(ZenScriptParser.BracketHandlerContext ctx);
}