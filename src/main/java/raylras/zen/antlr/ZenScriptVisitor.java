package raylras.zen.antlr;

import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.ast.*;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.stmt.*;
import raylras.zen.ast.type.TypeBoolean;
import raylras.zen.ast.type.TypeNull;
import raylras.zen.ast.type.TypeNumber;
import raylras.zen.ast.type.TypeString;

import java.util.stream.Collectors;

/*
    CST visitor class, used to convert CST to AST
 */

public class ZenScriptVisitor extends ZenScriptParserBaseVisitor<ASTNode> {

    public ZenScriptVisitor() {}

    @Override
    public ScriptNode visitScript(ZenScriptParser.ScriptContext ctx) {
        ScriptNode scriptNode = new ScriptNode();
        scriptNode.setSourcePosition(ctx);
        scriptNode.setImports(ctx.importStatement().stream().map(this::visitImportStatement).collect(Collectors.toList()));
        scriptNode.setFunctions(ctx.functionDeclaration().stream().map(this::visitFunctionDeclaration).collect(Collectors.toList()));
        scriptNode.setZenClasses(ctx.zenClassDeclaration().stream().map(this::visitZenClassDeclaration).collect(Collectors.toList()));
        scriptNode.setStatements(ctx.statement().stream().map(this::visitStatement).collect(Collectors.toList()));
        return scriptNode;
    }

    @Override
    public ImportNode visitImportStatement(ZenScriptParser.ImportStatementContext ctx) {
        ImportNode importNode = new ImportNode();
        importNode.setSourcePosition(ctx);
        importNode.setClassNameNode(visitClassName(ctx.className()));
        importNode.setAliasNode(visitAlias(ctx.alias()));
        return importNode;
    }

    @Override
    public ReferenceNode visitClassName(ZenScriptParser.ClassNameContext ctx) {
        ReferenceNode referenceNode = new ReferenceNode();
        referenceNode.setSourcePosition(ctx);
        referenceNode.setReference(ctx.getText());
        return referenceNode;
    }

    @Override
    public AliasNode visitAlias(ZenScriptParser.AliasContext ctx) {
        AliasNode aliasNode = new AliasNode();
        aliasNode.setSourcePosition(ctx);
        aliasNode.setAlias(ctx.getText());
        return aliasNode;
    }

    @Override
    public FunctionNode visitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        FunctionNode functionNode = new FunctionNode();
        functionNode.setSourcePosition(ctx);
        functionNode.setNameNode(new IDNode(ctx.IDENTIFIER().getSymbol()));
        functionNode.setParameterNodes(ctx.parameters().parameter().stream().map(this::visitParameter).collect(Collectors.toList()));
        functionNode.setBlockNode(visitBlock(ctx.block()));
        functionNode.setReturnTypeNode(visitAsType(ctx.asType()));
        return functionNode;
    }

    @Override
    public ParameterNode visitParameter(ZenScriptParser.ParameterContext ctx) {
        ParameterNode parameterNode = new ParameterNode();
        parameterNode.setSourcePosition(ctx);
        parameterNode.setNameNode(new IDNode(ctx.IDENTIFIER().getSymbol()));
        parameterNode.setTypeNode(visitAsType(ctx.asType()));
        return parameterNode;
    }

    @Override
    public ZenClassNode visitZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx) {
        ZenClassNode zenClassNode = new ZenClassNode();
        zenClassNode.setSourcePosition(ctx);
        zenClassNode.setNameNode(new IDNode(ctx.IDENTIFIER().getSymbol()));
        zenClassNode.setFieldNodes(ctx.classBody().field().stream().map(this::visitField).collect(Collectors.toList()));
        zenClassNode.setMethodNodes(ctx.classBody().method().stream().map(this::visitMethod).collect(Collectors.toList()));
        zenClassNode.setConstructorNodes(ctx.classBody().constructor().stream().map(this::visitConstructor).collect(Collectors.toList()));
        return zenClassNode;
    }

    @Override
    public FieldNode visitField(ZenScriptParser.FieldContext ctx) {
        FieldNode fieldNode = new FieldNode();
        fieldNode.setSourcePosition(ctx);
        fieldNode.setNameNode(new IDNode(ctx.IDENTIFIER().getSymbol()));
        fieldNode.setTypeNode(visitAsType(ctx.asType()));
        return fieldNode;
    }

    @Override
    public MethodNode visitMethod(ZenScriptParser.MethodContext ctx) {
        MethodNode methodNode = new MethodNode();
        methodNode.setSourcePosition(ctx);
        methodNode.setNameNode(new IDNode(ctx.IDENTIFIER().getSymbol()));
        methodNode.setParameterNodes(ctx.parameters().parameter().stream().map(this::visitParameter).collect(Collectors.toList()));
        methodNode.setReturnTypeNode(visitAsType(ctx.asType()));
        methodNode.setBlockNode(visitBlock(ctx.block()));
        return methodNode;
    }

    @Override
    public ConstructorNode visitConstructor(ZenScriptParser.ConstructorContext ctx) {
        ConstructorNode constructorNode = new ConstructorNode();
        constructorNode.setSourcePosition(ctx);
        constructorNode.setParameterNodes(ctx.parameters().parameter().stream().map(this::visitParameter).collect(Collectors.toList()));
        constructorNode.setBlockNode(visitBlock(ctx.block()));
        return constructorNode;
    }

    @Override
    public BlockNode visitBlock(ZenScriptParser.BlockContext ctx) {
        BlockNode blockNode = new BlockNode();
        blockNode.setSourcePosition(ctx);
        blockNode.setStatements(ctx.statement().stream().map(this::visitStatement).collect(Collectors.toList()));
        return blockNode;
    }

    @Override
    public Statement visitStatement(ZenScriptParser.StatementContext ctx) {
        return (Statement) visitChildren(ctx);
    }

    @Override
    public StatementReturn visitReturnStatement(ZenScriptParser.ReturnStatementContext ctx) {
        StatementReturn statementReturn = new StatementReturn();
        statementReturn.setSourcePosition(ctx);
        statementReturn.setExpr(visitExpression(ctx.expression()));
        return statementReturn;
    }

    @Override
    public StatementBreak visitBreakStatement(ZenScriptParser.BreakStatementContext ctx) {
        StatementBreak statementBreak = new StatementBreak();
        statementBreak.setSourcePosition(ctx);
        return statementBreak;
    }

    @Override
    public StatementContinue visitContinueStatement(ZenScriptParser.ContinueStatementContext ctx) {
        StatementContinue statementContinue = new StatementContinue();
        statementContinue.setSourcePosition(ctx);
        return statementContinue;
    }

    @Override
    public StatementIf visitIfStatement(ZenScriptParser.IfStatementContext ctx) {
        StatementIf statementIf = new StatementIf();
        statementIf.setSourcePosition(ctx);
        statementIf.setExpr(visitExpression(ctx.expression()));
        statementIf.setIfStmt(visitStatement(ctx.statement(0)));
        statementIf.setIfBlock(visitBlock(ctx.block(0)));
        statementIf.setElseStmt(visitStatement(ctx.statement(1)));
        statementIf.setElseBlock(visitBlock(ctx.block(1)));
        return statementIf;
    }

    @Override
    public StatementFor visitForStatement(ZenScriptParser.ForStatementContext ctx) {
        StatementFor statementFor = new StatementFor();
        statementFor.setSourcePosition(ctx);
        TerminalNode first = ctx.forControl().IDENTIFIER(0);
        if (first != null) {
            statementFor.setFirstVariableNode(new VariableNode(first.getSymbol()));
        }
        TerminalNode second = ctx.forControl().IDENTIFIER(1);
        if (second != null) {
            statementFor.setSecondVariableNode(new VariableNode(second.getSymbol()));
        }
        statementFor.setExpr((Expression) visit(ctx.forControl().expression()));
        return statementFor;
    }

    @Override
    public StatementWhile visitWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        StatementWhile statementWhile = new StatementWhile();
        statementWhile.setSourcePosition(ctx);
        statementWhile.setExpr(visitExpression(ctx.expression()));
        statementWhile.setBlockNode(visitBlock(ctx.block()));
        return statementWhile;
    }

    @Override
    public StatementVar visitVarStatement(ZenScriptParser.VarStatementContext ctx) {
        StatementVar statementVar = new StatementVar();
        statementVar.setSourcePosition(ctx);
        statementVar.setVariableNode(new VariableNode(ctx.IDENTIFIER().getSymbol()));
        statementVar.setExpression(visitExpression(ctx.expression()));
        return statementVar;
    }

    public Expression visitExpression(ZenScriptParser.ExpressionContext ctx) {
        return (Expression) visit(ctx);
    }

    @Override
    public StatementExpression visitExpressionStatement(ZenScriptParser.ExpressionStatementContext ctx) {
        StatementExpression statementExpression = new StatementExpression();
        statementExpression.setSourcePosition(ctx);
        statementExpression.setExpr(visitExpression(ctx.expression()));
        return statementExpression;
    }

    @Override
    public ExpressionIdentifier visitExpressionIdentifier(ZenScriptParser.ExpressionIdentifierContext ctx) {
        ExpressionIdentifier expressionIdentifier = new ExpressionIdentifier();
        expressionIdentifier.setSourcePosition(ctx);
        expressionIdentifier.setId(ctx.id.getText());
        return expressionIdentifier;
    }

    @Override
    public ExpressionAccess visitExpressionAccess(ZenScriptParser.ExpressionAccessContext ctx) {
        ExpressionAccess expressionAccess = new ExpressionAccess();
        expressionAccess.setSourcePosition(ctx);
        expressionAccess.setLeft(visitExpression(ctx.expression(0)));
        expressionAccess.setRight(visitExpression(ctx.expression(1)));
        return expressionAccess;
    }

    @Override
    public ExpressionIndex visitExpressionIndex(ZenScriptParser.ExpressionIndexContext ctx) {
        ExpressionIndex expressionIndex = new ExpressionIndex();
        expressionIndex.setSourcePosition(ctx);
        expressionIndex.setExpr(visitExpression(ctx.expression(0)));
        expressionIndex.setIndex(visitExpression(ctx.expression(1)));
        return expressionIndex;
    }

    @Override
    public ExpressionAnonymousFunction visitAnonymousFunction(ZenScriptParser.AnonymousFunctionContext ctx) {
        ExpressionAnonymousFunction expressionAnonymousFunction = new ExpressionAnonymousFunction();
        expressionAnonymousFunction.setSourcePosition(ctx);
        expressionAnonymousFunction.setParameterNodes(ctx.parameters().parameter().stream().map(this::visitParameter).collect(Collectors.toList()));
        expressionAnonymousFunction.setReturnTypeNode(visitAsType(ctx.asType()));
        expressionAnonymousFunction.setBlockNode(visitBlock(ctx.block()));
        return expressionAnonymousFunction;
    }

    @Override
    public ExpressionUnary visitExpressionUnary(ZenScriptParser.ExpressionUnaryContext ctx) {
        ExpressionUnary expressionUnary = new ExpressionUnary();
        expressionUnary.setSourcePosition(ctx);
        expressionUnary.setExpr(visitExpression(ctx.expression()));
        expressionUnary.setOperator(Operator.getUnary(ctx.op.getText()));
        return expressionUnary;
    }

    @Override
    public ExpressionParens visitExpressionParens(ZenScriptParser.ExpressionParensContext ctx) {
        ExpressionParens expressionParens = new ExpressionParens();
        expressionParens.setSourcePosition(ctx);
        expressionParens.setExpr(visitExpression(ctx.expression()));
        return expressionParens;
    }

    @Override
    public ExpressionAssign visitExpressionAssign(ZenScriptParser.ExpressionAssignContext ctx) {
        ExpressionAssign expressionAssign = new ExpressionAssign();
        expressionAssign.setSourcePosition(ctx);
        expressionAssign.setLeft(visitExpression(ctx.expression(0)));
        expressionAssign.setRight(visitExpression(ctx.expression(1)));
        expressionAssign.setOperator(Operator.getAssign(ctx.op.getText()));
        return expressionAssign;
    }

    @Override
    public ExpressionMapInit visitMapInit(ZenScriptParser.MapInitContext ctx) {
        ExpressionMapInit expressionMapInit = new ExpressionMapInit();
        expressionMapInit.setSourcePosition(ctx);
        expressionMapInit.setEntries(ctx.mapEntry().stream().map(this::visitMapEntry).collect(Collectors.toList()));
        return expressionMapInit;
    }

    @Override
    public ExpressionBinary visitExpressionBinary(ZenScriptParser.ExpressionBinaryContext ctx) {
        ExpressionBinary expressionBinary = new ExpressionBinary();
        expressionBinary.setSourcePosition(ctx);
        expressionBinary.setLeft(visitExpression(ctx.expression(0)));
        expressionBinary.setRight(visitExpression(ctx.expression(1)));
        return expressionBinary;
    }

    @Override
    public Expression visitExpressionLiteral(ZenScriptParser.ExpressionLiteralContext ctx) {
        return (Expression) visitChildren(ctx);
    }

    @Override
    public ExpressionTernary visitExpressionTrinary(ZenScriptParser.ExpressionTrinaryContext ctx) {
        ExpressionTernary expressionTernary = new ExpressionTernary();
        expressionTernary.setSourcePosition(ctx);
        expressionTernary.setFirst(visitExpression(ctx.expression(0)));
        expressionTernary.setSecond(visitExpression(ctx.expression(1)));
        expressionTernary.setThird(visitExpression(ctx.expression(2)));
        return expressionTernary;
    }

    @Override
    public ExpressionCall visitExpressionCall(ZenScriptParser.ExpressionCallContext ctx) {
        ExpressionCall expressionCall = new ExpressionCall();
        expressionCall.setSourcePosition(ctx);
        expressionCall.setExpr(visitExpression(ctx.expression()));
        expressionCall.setArgs(ctx.arguments().argument().stream().map(this::visitArgument).collect(Collectors.toList()));
        return expressionCall;
    }

    @Override
    public ExpressionCast visitExpressionCast(ZenScriptParser.ExpressionCastContext ctx) {
        ExpressionCast expressionCast = new ExpressionCast();
        expressionCast.setSourcePosition(ctx);
        expressionCast.setExpr(visitExpression(ctx.expression()));
        expressionCast.setTypeNode(visitAsType(ctx.asType()));
        return expressionCast;
    }

    @Override
    public ExpressionArrayInit visitArrayInit(ZenScriptParser.ArrayInitContext ctx) {
        ExpressionArrayInit expressionArrayInit = new ExpressionArrayInit();
        expressionArrayInit.setSourcePosition(ctx);
        expressionArrayInit.setElements(ctx.expression().stream().map(this::visitExpression).collect(Collectors.toList()));
        return expressionArrayInit;
    }

    @Override
    public ExpressionRange visitExpressionRange(ZenScriptParser.ExpressionRangeContext ctx) {
        ExpressionRange expressionRange = new ExpressionRange();
        expressionRange.setSourcePosition(ctx);
        expressionRange.setLeft(visitExpression(ctx.expression(0)));
        expressionRange.setRight(visitExpression(ctx.expression(1)));
        return expressionRange;
    }

    @Override
    public ExpressionBracketHandler visitBracketHandler(ZenScriptParser.BracketHandlerContext ctx) {
        ExpressionBracketHandler expressionBracketHandler = new ExpressionBracketHandler();
        expressionBracketHandler.setSourcePosition(ctx);
        expressionBracketHandler.setContent(ctx.getText());
        return expressionBracketHandler;
    }

    @Override
    public Expression visitArgument(ZenScriptParser.ArgumentContext ctx) {
        return visitExpression(ctx.expression());
    }

    @Override
    public TypeNode visitAsType(ZenScriptParser.AsTypeContext ctx) {
        return ctx == null ? null : visitType(ctx.type());
    }

    @Override
    public TypeNode visitType(ZenScriptParser.TypeContext ctx) {
        return (TypeNode) visitChildren(ctx);
    }

    @Override
    public TypeNode visitTypeFunction(ZenScriptParser.TypeFunctionContext ctx) {
        TypeNode typeNode = new TypeNode();
        typeNode.setSourcePosition(ctx);
        typeNode.setTypeName(ctx.getText());
        return typeNode;
    }

    @Override
    public TypeNode visitTypePrimitive(ZenScriptParser.TypePrimitiveContext ctx) {
        TypeNode typeNode = new TypeNode();
        typeNode.setSourcePosition(ctx);
        typeNode.setTypeName(ctx.getText());
        return typeNode;
    }

    @Override
    public TypeNode visitTypeArray(ZenScriptParser.TypeArrayContext ctx) {
        TypeNode typeNode = new TypeNode();
        typeNode.setSourcePosition(ctx);
        typeNode.setTypeName(ctx.getText());
        return typeNode;
    }

    @Override
    public TypeNode visitTypeList(ZenScriptParser.TypeListContext ctx) {
        TypeNode typeNode = new TypeNode();
        typeNode.setSourcePosition(ctx);
        typeNode.setTypeName(ctx.getText());
        return typeNode;
    }

    @Override
    public TypeNode visitTypeMap(ZenScriptParser.TypeMapContext ctx) {
        TypeNode typeNode = new TypeNode();
        typeNode.setSourcePosition(ctx);
        typeNode.setTypeName(ctx.getText());
        return typeNode;
    }

    @Override
    public TypeNode visitTypeClass(ZenScriptParser.TypeClassContext ctx) {
        TypeNode typeNode = new TypeNode();
        typeNode.setSourcePosition(ctx);
        typeNode.setTypeName(ctx.getText());
        return typeNode;
    }

    @Override
    public ExpressionMapEntry visitMapEntry(ZenScriptParser.MapEntryContext ctx) {
        ExpressionMapEntry expressionMapEntry = new ExpressionMapEntry();
        expressionMapEntry.setSourcePosition(ctx);
        expressionMapEntry.setKey(visitExpression(ctx.expression(0)));
        expressionMapEntry.setValue(visitExpression(ctx.expression(1)));
        return expressionMapEntry;
    }

    public Expression visitLiteral(ZenScriptParser.LiteralContext ctx) {
        return (Expression) visit(ctx);
    }

    @Override
    public ExpressionNumber visitIntegerLiteral(ZenScriptParser.IntegerLiteralContext ctx) {
        ExpressionNumber expressionNumber = new ExpressionNumber();
        expressionNumber.setSourcePosition(ctx);
        expressionNumber.setType(TypeNumber.INSTANCE);
        return expressionNumber;
    }

    @Override
    public ExpressionNumber visitFloatingLiteral(ZenScriptParser.FloatingLiteralContext ctx) {
        ExpressionNumber expressionNumber = new ExpressionNumber();
        expressionNumber.setSourcePosition(ctx);
        expressionNumber.setType(TypeNumber.INSTANCE);
        return expressionNumber;
    }

    @Override
    public ExpressionString visitStringLiteral(ZenScriptParser.StringLiteralContext ctx) {
        ExpressionString expressionString = new ExpressionString();
        expressionString.setSourcePosition(ctx);
        expressionString.setType(TypeString.INSTANCE);
        return expressionString;
    }

    @Override
    public ExpressionBoolean visitBooleanLiteral(ZenScriptParser.BooleanLiteralContext ctx) {
        ExpressionBoolean expressionBoolean = new ExpressionBoolean();
        expressionBoolean.setSourcePosition(ctx);
        expressionBoolean.setType(TypeBoolean.INSTANCE);
        return expressionBoolean;
    }

    @Override
    public ExpressionNull visitNullLiteral(ZenScriptParser.NullLiteralContext ctx) {
        ExpressionNull expressionNull = new ExpressionNull();
        expressionNull.setSourcePosition(ctx);
        expressionNull.setType(TypeNull.INSTANCE);
        return expressionNull;
    }

}
