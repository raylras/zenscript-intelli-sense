// Generated from D:/Projects/Forge/1.12.2/ZenServer/src/main/java/raylras/zen/antlr\ZenScriptParser.g4 by ANTLR 4.9.2
package raylras.zen.antlr;
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
	 * Enter a parse tree produced by {@link ZenScriptParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(ZenScriptParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(ZenScriptParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(ZenScriptParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(ZenScriptParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void enterDefaultValue(ZenScriptParser.DefaultValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void exitDefaultValue(ZenScriptParser.DefaultValueContext ctx);
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
	 * Enter a parse tree produced by {@link ZenScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ZenScriptParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ZenScriptParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(ZenScriptParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(ZenScriptParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(ZenScriptParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(ZenScriptParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(ZenScriptParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(ZenScriptParser.ContinueStatementContext ctx);
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
	 * Enter a parse tree produced by {@link ZenScriptParser#varStatement}.
	 * @param ctx the parse tree
	 */
	void enterVarStatement(ZenScriptParser.VarStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#varStatement}.
	 * @param ctx the parse tree
	 */
	void exitVarStatement(ZenScriptParser.VarStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZenScriptParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(ZenScriptParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(ZenScriptParser.ExpressionStatementContext ctx);
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
	 * Enter a parse tree produced by {@link ZenScriptParser#className}.
	 * @param ctx the parse tree
	 */
	void enterClassName(ZenScriptParser.ClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZenScriptParser#className}.
	 * @param ctx the parse tree
	 */
	void exitClassName(ZenScriptParser.ClassNameContext ctx);
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
	 * Enter a parse tree produced by the {@code expressionCast}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionCast(ZenScriptParser.ExpressionCastContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionCast}
	 * labeled alternative in {@link ZenScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionCast(ZenScriptParser.ExpressionCastContext ctx);
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