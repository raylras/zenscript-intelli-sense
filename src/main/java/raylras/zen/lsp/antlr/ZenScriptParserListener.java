// Generated from D:/Projects/Forge/1.12.2/ZenServer/src/main/resources\ZenScriptParser.g4 by ANTLR 4.9.2
package raylras.zen.lsp.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ZenScriptParser}.
 */
public interface ZenScriptParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#script}.
	 * @param ctx the parse tree
	 */
	void enterScript(ZenScriptParser.ScriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#script}.
	 * @param ctx the parse tree
	 */
	void exitScript(ZenScriptParser.ScriptContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportStatement(ZenScriptParser.ImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportStatement(ZenScriptParser.ImportStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#zenClassDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#zenClassDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(ZenScriptParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(ZenScriptParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#constructor}.
	 * @param ctx the parse tree
	 */
	void enterConstructor(ZenScriptParser.ConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#constructor}.
	 * @param ctx the parse tree
	 */
	void exitConstructor(ZenScriptParser.ConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(ZenScriptParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(ZenScriptParser.FieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#method}.
	 * @param ctx the parse tree
	 */
	void enterMethod(ZenScriptParser.MethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#method}.
	 * @param ctx the parse tree
	 */
	void exitMethod(ZenScriptParser.MethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclaration(ZenScriptParser.LocalVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclaration(ZenScriptParser.LocalVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#globalVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGlobalVariableDeclaration(ZenScriptParser.GlobalVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#globalVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGlobalVariableDeclaration(ZenScriptParser.GlobalVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#asType}.
	 * @param ctx the parse tree
	 */
	void enterAsType(ZenScriptParser.AsTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#asType}.
	 * @param ctx the parse tree
	 */
	void exitAsType(ZenScriptParser.AsTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#memberCall}.
	 * @param ctx the parse tree
	 */
	void enterMemberCall(ZenScriptParser.MemberCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#memberCall}.
	 * @param ctx the parse tree
	 */
	void exitMemberCall(ZenScriptParser.MemberCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(ZenScriptParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(ZenScriptParser.MethodCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#anonymousFunction}.
	 * @param ctx the parse tree
	 */
	void enterAnonymousFunction(ZenScriptParser.AnonymousFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#anonymousFunction}.
	 * @param ctx the parse tree
	 */
	void exitAnonymousFunction(ZenScriptParser.AnonymousFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameters(ZenScriptParser.FormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameters(ZenScriptParser.FormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameter(ZenScriptParser.FormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameter(ZenScriptParser.FormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(ZenScriptParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(ZenScriptParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(ZenScriptParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(ZenScriptParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ZenScriptParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ZenScriptParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(ZenScriptParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(ZenScriptParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#map}.
	 * @param ctx the parse tree
	 */
	void enterMap(ZenScriptParser.MapContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#map}.
	 * @param ctx the parse tree
	 */
	void exitMap(ZenScriptParser.MapContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#mapEntry}.
	 * @param ctx the parse tree
	 */
	void enterMapEntry(ZenScriptParser.MapEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#mapEntry}.
	 * @param ctx the parse tree
	 */
	void exitMapEntry(ZenScriptParser.MapEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#mapKey}.
	 * @param ctx the parse tree
	 */
	void enterMapKey(ZenScriptParser.MapKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#mapKey}.
	 * @param ctx the parse tree
	 */
	void exitMapKey(ZenScriptParser.MapKeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#mapValue}.
	 * @param ctx the parse tree
	 */
	void enterMapValue(ZenScriptParser.MapValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#mapValue}.
	 * @param ctx the parse tree
	 */
	void exitMapValue(ZenScriptParser.MapValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(ZenScriptParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(ZenScriptParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ZenScriptParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ZenScriptParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(ZenScriptParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(ZenScriptParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(ZenScriptParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(ZenScriptParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#doWhileStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileStatement(ZenScriptParser.DoWhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#doWhileStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileStatement(ZenScriptParser.DoWhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#forControl}.
	 * @param ctx the parse tree
	 */
	void enterForControl(ZenScriptParser.ForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#forControl}.
	 * @param ctx the parse tree
	 */
	void exitForControl(ZenScriptParser.ForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#range}.
	 * @param ctx the parse tree
	 */
	void enterRange(ZenScriptParser.RangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#range}.
	 * @param ctx the parse tree
	 */
	void exitRange(ZenScriptParser.RangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#bounds}.
	 * @param ctx the parse tree
	 */
	void enterBounds(ZenScriptParser.BoundsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#bounds}.
	 * @param ctx the parse tree
	 */
	void exitBounds(ZenScriptParser.BoundsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#packageName}.
	 * @param ctx the parse tree
	 */
	void enterPackageName(ZenScriptParser.PackageNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#packageName}.
	 * @param ctx the parse tree
	 */
	void exitPackageName(ZenScriptParser.PackageNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionArrayGet}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionArrayGet(ZenScriptParser.ExpressionArrayGetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionArrayGet}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionArrayGet(ZenScriptParser.ExpressionArrayGetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionReturn}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionReturn(ZenScriptParser.ExpressionReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionReturn}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionReturn(ZenScriptParser.ExpressionReturnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionFunction}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionFunction(ZenScriptParser.ExpressionFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionFunction}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionFunction(ZenScriptParser.ExpressionFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionUnary}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionUnary(ZenScriptParser.ExpressionUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionUnary}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionUnary(ZenScriptParser.ExpressionUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionAssign}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionAssign(ZenScriptParser.ExpressionAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionAssign}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionAssign(ZenScriptParser.ExpressionAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionTrinary}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionTrinary(ZenScriptParser.ExpressionTrinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionTrinary}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionTrinary(ZenScriptParser.ExpressionTrinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionID}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionID(ZenScriptParser.ExpressionIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionID}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionID(ZenScriptParser.ExpressionIDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionAnd}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionAnd(ZenScriptParser.ExpressionAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionAnd}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionAnd(ZenScriptParser.ExpressionAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionCat}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionCat(ZenScriptParser.ExpressionCatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionCat}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionCat(ZenScriptParser.ExpressionCatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionAndAnd}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionAndAnd(ZenScriptParser.ExpressionAndAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionAndAnd}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionAndAnd(ZenScriptParser.ExpressionAndAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionBracketHandler}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionBracketHandler(ZenScriptParser.ExpressionBracketHandlerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionBracketHandler}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionBracketHandler(ZenScriptParser.ExpressionBracketHandlerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionBreak}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionBreak(ZenScriptParser.ExpressionBreakContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionBreak}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionBreak(ZenScriptParser.ExpressionBreakContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionCompare}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionCompare(ZenScriptParser.ExpressionCompareContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionCompare}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionCompare(ZenScriptParser.ExpressionCompareContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionContinue}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionContinue(ZenScriptParser.ExpressionContinueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionContinue}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionContinue(ZenScriptParser.ExpressionContinueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionParens}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionParens(ZenScriptParser.ExpressionParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionParens}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionParens(ZenScriptParser.ExpressionParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionInstanceof}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionInstanceof(ZenScriptParser.ExpressionInstanceofContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionInstanceof}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionInstanceof(ZenScriptParser.ExpressionInstanceofContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionXor}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionXor(ZenScriptParser.ExpressionXorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionXor}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionXor(ZenScriptParser.ExpressionXorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionMap}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionMap(ZenScriptParser.ExpressionMapContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionMap}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionMap(ZenScriptParser.ExpressionMapContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionBinary}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionBinary(ZenScriptParser.ExpressionBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionBinary}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionBinary(ZenScriptParser.ExpressionBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionLiteral}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionLiteral(ZenScriptParser.ExpressionLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionLiteral}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionLiteral(ZenScriptParser.ExpressionLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionCall}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionCall(ZenScriptParser.ExpressionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionCall}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionCall(ZenScriptParser.ExpressionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionArray}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionArray(ZenScriptParser.ExpressionArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionArray}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionArray(ZenScriptParser.ExpressionArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionOrOr}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionOrOr(ZenScriptParser.ExpressionOrOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionOrOr}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionOrOr(ZenScriptParser.ExpressionOrOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionOr}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionOr(ZenScriptParser.ExpressionOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionOr}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionOr(ZenScriptParser.ExpressionOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionIn}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionIn(ZenScriptParser.ExpressionInContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionIn}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionIn(ZenScriptParser.ExpressionInContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(ZenScriptParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(ZenScriptParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#typeFunction}.
	 * @param ctx the parse tree
	 */
	void enterTypeFunction(ZenScriptParser.TypeFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#typeFunction}.
	 * @param ctx the parse tree
	 */
	void exitTypeFunction(ZenScriptParser.TypeFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#typePrimitive}.
	 * @param ctx the parse tree
	 */
	void enterTypePrimitive(ZenScriptParser.TypePrimitiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#typePrimitive}.
	 * @param ctx the parse tree
	 */
	void exitTypePrimitive(ZenScriptParser.TypePrimitiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#typeArray}.
	 * @param ctx the parse tree
	 */
	void enterTypeArray(ZenScriptParser.TypeArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#typeArray}.
	 * @param ctx the parse tree
	 */
	void exitTypeArray(ZenScriptParser.TypeArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#typeList}.
	 * @param ctx the parse tree
	 */
	void enterTypeList(ZenScriptParser.TypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#typeList}.
	 * @param ctx the parse tree
	 */
	void exitTypeList(ZenScriptParser.TypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#typeMap}.
	 * @param ctx the parse tree
	 */
	void enterTypeMap(ZenScriptParser.TypeMapContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#typeMap}.
	 * @param ctx the parse tree
	 */
	void exitTypeMap(ZenScriptParser.TypeMapContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#typeClass}.
	 * @param ctx the parse tree
	 */
	void enterTypeClass(ZenScriptParser.TypeClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#typeClass}.
	 * @param ctx the parse tree
	 */
	void exitTypeClass(ZenScriptParser.TypeClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(ZenScriptParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(ZenScriptParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(ZenScriptParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(ZenScriptParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#bracketHandler}.
	 * @param ctx the parse tree
	 */
	void enterBracketHandler(ZenScriptParser.BracketHandlerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#bracketHandler}.
	 * @param ctx the parse tree
	 */
	void exitBracketHandler(ZenScriptParser.BracketHandlerContext ctx);
}