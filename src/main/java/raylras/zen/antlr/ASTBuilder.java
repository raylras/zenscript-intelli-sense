package raylras.zen.antlr;

import raylras.zen.ast.*;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.stmt.*;
import raylras.zen.verify.type.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ASTBuilder extends ZenScriptParserBaseVisitor<ASTNode> {

    private final List<ASTNode> astNodeList = new ArrayList<>();

    public List<ASTNode> getAstNodeList() {
        return astNodeList;
    }

    public ScriptNode visitScriptUnit(ZenScriptParser.ScriptUnitContext ctx, URI uri) {
        if (ctx == null) return null;

        List<ImportNode> imports = ctx.importDeclaration().stream().map(this::visitImportDeclaration).filter(Objects::nonNull).collect(Collectors.toList());
        List<FunctionNode> functions = ctx.functionDeclaration().stream().map(this::visitFunctionDeclaration).filter(Objects::nonNull).collect(Collectors.toList());
        List<ZenClassNode> zenClasses = ctx.zenClassDeclaration().stream().map(this::visitZenClassDeclaration).filter(Objects::nonNull).collect(Collectors.toList());
        List<Statement> statements = ctx.statement().stream().map(this::visitStatement).filter(Objects::nonNull).collect(Collectors.toList());

        ScriptNode scriptUnit = new ScriptNode(imports, functions, zenClasses, statements, uri);
        scriptUnit.setSourcePosition(ctx);
        addAstNode(scriptUnit);

        return scriptUnit;
    }

    private void addAstNode(ASTNode node) {
        astNodeList.add(node);
    }

    private Expression visitExpression(ZenScriptParser.ExpressionContext ctx) {
        if (ctx == null) return null;
        return (Expression) super.visit(ctx);
    }

    public TypeNode visitType(ZenScriptParser.TypeContext ctx) {
        if (ctx == null) return null;
        return (TypeNode) super.visit(ctx);
    }

    //
    // antlr overrides
    //

    @Override
    public ScriptNode visitScriptUnit(ZenScriptParser.ScriptUnitContext ctx) {
        if (ctx == null) return null;
        return visitScriptUnit(ctx, null);
    }

    @Override
    public ImportNode visitImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        if (ctx == null) return null;

        ReferenceNode reference = null;
        if (ctx.className() != null) reference = visitClassName(ctx.className());
        if (ctx.crossScriptReference() != null) reference = visitCrossScriptReference(ctx.crossScriptReference());

        AliasNode alias = null;
        if (ctx.alias() != null) alias = visitAlias(ctx.alias());

        ImportNode importNode = new ImportNode(reference, alias);
        importNode.setSourcePosition(ctx);
        addAstNode(importNode);

        return importNode;
    }

    @Override
    public ClassReferenceNode visitClassName(ZenScriptParser.ClassNameContext ctx) {
        if (ctx == null) return null;

        ClassReferenceNode classReference = new ClassReferenceNode(ctx.getText());
        classReference.setSourcePosition(ctx);
        addAstNode(classReference);

        return classReference;
    }

    @Override
    public CrossScriptReferenceNode visitCrossScriptReference(ZenScriptParser.CrossScriptReferenceContext ctx) {
        if (ctx == null) return null;

        CrossScriptReferenceNode scriptReference = new CrossScriptReferenceNode(ctx.getText());
        scriptReference.setSourcePosition(ctx);
        addAstNode(scriptReference);

        return scriptReference;
    }

    @Override
    public AliasNode visitAlias(ZenScriptParser.AliasContext ctx) {
        if (ctx == null) return null;

        IdentifierNode idNode = visitIdentifier(ctx.identifier());
        AliasNode alias = new AliasNode(idNode);
        alias.setSourcePosition(ctx);
        addAstNode(alias);

        return alias;
    }

    @Override
    public FunctionNode visitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        if (ctx == null) return null;

        IdentifierNode name = visitIdentifier(ctx.identifier());
        List<ParameterNode> parameters = ctx.formalParameter().stream().map(this::visitFormalParameter).filter(Objects::nonNull).collect(Collectors.toList());
        TypeNode result = visitType(ctx.type());
        BlockNode block = visitBlock(ctx.block());

        FunctionNode function = new FunctionNode(name, parameters, result, block);
        function.setSourcePosition(ctx);
        addAstNode(function);

        return function;
    }

    @Override
    public ParameterNode visitFormalParameter(ZenScriptParser.FormalParameterContext ctx) {
        if (ctx == null) return null;

        IdentifierNode name = visitIdentifier(ctx.identifier());
        TypeNode type = visitType(ctx.type());
        Expression defaultValue = visitDefaultValue(ctx.defaultValue());

        ParameterNode parameter = new ParameterNode(name, type, defaultValue);
        parameter.setSourcePosition(ctx);
        addAstNode(parameter);

        return parameter;
    }

    @Override
    public Expression visitDefaultValue(ZenScriptParser.DefaultValueContext ctx) {
        if (ctx == null) return null;
        return visitExpression(ctx.expression());
    }

    @Override
    public ZenClassNode visitZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx) {
        if (ctx == null) return null;

        IdentifierNode name = visitIdentifier(ctx.identifier());
        List<FieldNode> fields = ctx.fieldDeclaration().stream().map(this::visitFieldDeclaration).filter(Objects::nonNull).collect(Collectors.toList());
        List<ConstructorNode> constructors = ctx.constructorDeclaration().stream().map(this::visitConstructorDeclaration).filter(Objects::nonNull).collect(Collectors.toList());
        List<FunctionNode> functions = ctx.functionDeclaration().stream().map(this::visitFunctionDeclaration).filter(Objects::nonNull).collect(Collectors.toList());

        ZenClassNode zenClass = new ZenClassNode(name,fields, constructors, functions);
        zenClass.setSourcePosition(ctx);
        addAstNode(zenClass);

        return zenClass;
    }

    @Override
    public ConstructorNode visitConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        if (ctx == null) return null;

        List<ParameterNode> parameters = ctx.formalParameter().stream().map(this::visitFormalParameter).filter(Objects::nonNull).collect(Collectors.toList());
        BlockNode block = visitBlock(ctx.block());

        ConstructorNode constructor = new ConstructorNode(parameters, block);
        constructor.setSourcePosition(ctx);
        addAstNode(constructor);

        return constructor;
    }

    @Override
    public FieldNode visitFieldDeclaration(ZenScriptParser.FieldDeclarationContext ctx) {
        if (ctx == null) return null;

        IdentifierNode name = visitIdentifier(ctx.identifier());
        TypeNode type = visitType(ctx.type());
        Expression expr = visitExpression(ctx.expression());

        FieldNode field = new FieldNode(name, type, expr);
        field.setSourcePosition(ctx);
        addAstNode(field);

        return field;
    }

    @Override
    public BlockNode visitBlock(ZenScriptParser.BlockContext ctx) {
        if (ctx == null) return null;

        List<Statement> statements = ctx.statement().stream().map(this::visitStatement).filter(Objects::nonNull).collect(Collectors.toList());

        BlockNode block = new BlockNode(statements);
        block.setSourcePosition(ctx);
        addAstNode(block);

        return block;
    }

    @Override
    public Statement visitStatement(ZenScriptParser.StatementContext ctx) {
        if (ctx == null) return null;
        return (Statement) visitChildren(ctx);
    }

    @Override
    public BlockStatement visitBlockStatement(ZenScriptParser.BlockStatementContext ctx) {
        if (ctx == null) return null;

        BlockNode block = visitBlock(ctx.block());

        BlockStatement blockStmt = new BlockStatement(block);
        blockStmt.setSourcePosition(ctx);
        addAstNode(blockStmt);

        return blockStmt;
    }

    @Override
    public ReturnStatement visitReturnStatement(ZenScriptParser.ReturnStatementContext ctx) {
        if (ctx == null) return null;

        Expression expr = visitExpression(ctx.expression());

        ReturnStatement returnStmt = new ReturnStatement(expr);
        returnStmt.setSourcePosition(ctx);
        addAstNode(returnStmt);

        return returnStmt;
    }

    @Override
    public BreakStatement visitBreakStatement(ZenScriptParser.BreakStatementContext ctx) {
        if (ctx == null) return null;

        BreakStatement breakStmt = new BreakStatement();
        breakStmt.setSourcePosition(ctx);
        addAstNode(breakStmt);

        return breakStmt;
    }

    @Override
    public ContinueStatement visitContinueStatement(ZenScriptParser.ContinueStatementContext ctx) {
        if (ctx == null) return null;

        ContinueStatement continueStmt = new ContinueStatement();
        continueStmt.setSourcePosition(ctx);
        addAstNode(continueStmt);

        return continueStmt;
    }

    @Override
    public IfStatement visitIfStatement(ZenScriptParser.IfStatementContext ctx) {
        if (ctx == null) return null;

        Expression condition = visitExpression(ctx.expression());
        Statement thenStmt = visitStatement(ctx.statement(0));
        Statement elseStmt = visitStatement(ctx.statement(1));

        IfStatement ifStmt = new IfStatement(condition, thenStmt, elseStmt);
        ifStmt.setSourcePosition(ctx);
        addAstNode(ifStmt);

        return ifStmt;
    }

    @Override
    public ForeachStatement visitForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        if (ctx == null) return null;

        List<VariableNode> variables = ctx.identifier().stream()
                .map(this::visitIdentifier)
                .filter(Objects::nonNull)
                .map(idNode -> {
                    VariableNode variable = new VariableNode(idNode, null);
                    variable.setSourcePosition(idNode);
                    addAstNode(variable);
                    return variable;
                })
                .collect(Collectors.toList());
        Expression expr = visitExpression(ctx.expression());
        BlockNode block = visitBlock(ctx.block());

        ForeachStatement foreachStmt = new ForeachStatement(variables, expr, block);
        foreachStmt.setSourcePosition(ctx);
        addAstNode(foreachStmt);

        return foreachStmt;
    }

    @Override
    public WhileStatement visitWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        if (ctx == null) return null;

        Expression condition = visitExpression(ctx.expression());
        BlockNode block = visitBlock(ctx.block());

        WhileStatement whileStmt = new WhileStatement(condition, block);
        whileStmt.setSourcePosition(ctx);
        addAstNode(whileStmt);

        return whileStmt;
    }

    @Override
    public VarStatement visitVariableDeclarationStatement(ZenScriptParser.VariableDeclarationStatementContext ctx) {
        if (ctx == null) return null;

        IdentifierNode name = visitIdentifier(ctx.identifier());
        TypeNode type = visitType(ctx.type());
        Expression expr = visitExpression(ctx.expression());

        VariableNode variable = new VariableNode(name, type);
        variable.setSourcePosition(name);
        switch (ctx.Modifier.getType()) {
            case ZenScriptLexer.GLOBAL: variable.setGlobal(true);
            case ZenScriptLexer.STATIC: variable.setStatic(true);
            case ZenScriptLexer.VAL: variable.setFinal(true);
        }
        addAstNode(variable);

        VarStatement varStmt = new VarStatement(variable, expr);
        varStmt.setSourcePosition(ctx);
        addAstNode(varStmt);

        return varStmt;
    }

    @Override
    public ExpressionStatement visitExpressionStatement(ZenScriptParser.ExpressionStatementContext ctx) {
        if (ctx == null) return null;

        Expression expr = visitExpression(ctx.expression());

        ExpressionStatement exprStmt = new ExpressionStatement(expr);
        exprStmt.setSourcePosition(ctx);
        addAstNode(exprStmt);

        return exprStmt;
    }

    @Override
    public UnaryExpression visitUnaryExpression(ZenScriptParser.UnaryExpressionContext ctx) {
        if (ctx == null) return null;

        Expression expr = visitExpression(ctx.expression());
        Operator.Unary operator = Operator.getUnary(ctx.Operator.getText());

        UnaryExpression unaryExpr = new UnaryExpression(expr, operator);
        unaryExpr.setSourcePosition(ctx);
        addAstNode(unaryExpr);

        return unaryExpr;
    }

    @Override
    public BinaryExpression visitBinaryExpression(ZenScriptParser.BinaryExpressionContext ctx) {
        if (ctx == null) return null;

        Expression left = visitExpression(ctx.Left);
        Expression right = visitExpression(ctx.Right);
        Operator.Binary operator = Operator.getBinary(ctx.Operator.getText());

        BinaryExpression binaryExpr = new BinaryExpression(left, right, operator);
        binaryExpr.setSourcePosition(ctx);
        addAstNode(binaryExpr);

        return binaryExpr;
    }

    @Override
    public TypeCastExpression visitTypeCastExpression(ZenScriptParser.TypeCastExpressionContext ctx) {
        if (ctx == null) return null;

        Expression expr = visitExpression(ctx.expression());
        TypeNode type = visitType(ctx.type());

        TypeCastExpression typeCastExpr = new TypeCastExpression(expr, type);
        typeCastExpr.setSourcePosition(ctx);
        addAstNode(typeCastExpr);

        return typeCastExpr;
    }

    @Override
    public RangeExpression visitRangeExpression(ZenScriptParser.RangeExpressionContext ctx) {
        if (ctx == null) return null;

        Expression from = visitExpression(ctx.From);
        Expression to = visitExpression(ctx.To);

        RangeExpression rangeExpr = new RangeExpression(from, to);
        rangeExpr.setSourcePosition(ctx);
        addAstNode(rangeExpr);

        return rangeExpr;
    }

    @Override
    public ParensExpression visitParensExpression(ZenScriptParser.ParensExpressionContext ctx) {
        if (ctx == null) return null;

        Expression expr = visitExpression(ctx.expression());

        ParensExpression parensExpr = new ParensExpression(expr);
        parensExpr.setSourcePosition(ctx);
        addAstNode(parensExpr);

        return parensExpr;
    }

    @Override
    public ASTNode visitArgumentsExpression(ZenScriptParser.ArgumentsExpressionContext ctx) {
        if (ctx == null) return null;

        Expression left = visitExpression(ctx.Left);
        List<Expression> arguments = ctx.expression().stream().skip(1).map(this::visitExpression).filter(Objects::nonNull).collect(Collectors.toList());

        ArgumentsExpression argumentsExpr = new ArgumentsExpression(left, arguments);
        argumentsExpr.setSourcePosition(ctx);
        addAstNode(argumentsExpr);

        return argumentsExpr;
    }

    @Override
    public ThisExpression visitThisExpression(ZenScriptParser.ThisExpressionContext ctx) {
        if (ctx == null) return null;

        ThisExpression thisExpr = new ThisExpression();
        thisExpr.setSourcePosition(ctx);
        addAstNode(thisExpr);

        return thisExpr;
    }

    @Override
    public FunctionExpression visitFunctionExpression(ZenScriptParser.FunctionExpressionContext ctx) {
        if (ctx == null) return null;

        List<ParameterNode> parameters = ctx.formalParameter().stream().map(this::visitFormalParameter).filter(Objects::nonNull).collect(Collectors.toList());
        TypeNode resultType = visitType(ctx.type());
        BlockNode block = visitBlock(ctx.block());

        FunctionExpression functionExpr = new FunctionExpression(parameters, resultType, block);
        functionExpr.setSourcePosition(ctx);
        addAstNode(functionExpr);

        return functionExpr;
    }

    @Override
    public AssignmentExpression visitAssignmentExpression(ZenScriptParser.AssignmentExpressionContext ctx) {
        if (ctx == null) return null;

        Expression left = visitExpression(ctx.Left);
        Expression right = visitExpression(ctx.Right);
        Operator.Assignment operator = Operator.getAssignment(ctx.Operator.getText());

        AssignmentExpression assignmentExpr = new AssignmentExpression(left, right, operator);
        assignmentExpr.setSourcePosition(ctx);
        addAstNode(assignmentExpr);

        return assignmentExpr;
    }

    @Override
    public TernaryExpression visitTrinaryExpression(ZenScriptParser.TrinaryExpressionContext ctx) {
        if (ctx == null) return null;

        Expression condition = visitExpression(ctx.Condition);
        Expression thenExpr = visitExpression(ctx.Then);
        Expression elseExpr = visitExpression(ctx.Else);

        TernaryExpression ternaryExpr = new TernaryExpression(condition, thenExpr, elseExpr);
        ternaryExpr.setSourcePosition(ctx);
        addAstNode(ternaryExpr);

        return ternaryExpr;
    }

    @Override
    public MemberAccessExpression visitMemberAccessExpression(ZenScriptParser.MemberAccessExpressionContext ctx) {
        if (ctx == null) return null;

        Expression left = visitExpression(ctx.Left);
        Expression right = visitExpression(ctx.Right);
        addAstNode(right);

        MemberAccessExpression memberAccessExpr = new MemberAccessExpression(left, right);
        memberAccessExpr.setSourcePosition(ctx);
        addAstNode(memberAccessExpr);

        return memberAccessExpr;
    }

    @Override
    public MapLiteralExpression visitMapLiteralExpression(ZenScriptParser.MapLiteralExpressionContext ctx) {
        if (ctx == null) return null;

        List<MapEntryExpression> entries = ctx.mapEntry().stream().map(this::visitMapEntry).filter(Objects::nonNull).collect(Collectors.toList());

        MapLiteralExpression mapLiteralExpr = new MapLiteralExpression(entries);
        mapLiteralExpr.setSourcePosition(ctx);
        addAstNode(mapLiteralExpr);

        return mapLiteralExpr;
    }

    @Override
    public BracketHandlerExpression visitBracketHandlerExpression(ZenScriptParser.BracketHandlerExpressionContext ctx) {
        if (ctx == null) return null;

        String literal = ctx.getText();

        BracketHandlerExpression bracketHandler = new BracketHandlerExpression(literal);
        bracketHandler.setSourcePosition(ctx);
        addAstNode(bracketHandler);

        return bracketHandler;
    }

    @Override
    public Expression visitLiteralExpression(ZenScriptParser.LiteralExpressionContext ctx) {
        if (ctx == null) return null;

        Expression literal = null;

        switch (ctx.literal().getStart().getType()) {
            case ZenScriptLexer.BOOLEAN_LITERAL:
                literal = new BooleanLiteralExpression(ctx.getText());
                literal.setType(BuiltinType.BOOL);
                break;
            case ZenScriptLexer.DECIMAL_LITERAL:
            case ZenScriptLexer.FLOATING_LITERAL:
            case ZenScriptLexer.HEX_LITERAL:
                literal = new NumberLiteralExpression(ctx.getText());
                break;
            case ZenScriptLexer.STRING_LITERAL:
                literal = new StringLiteralExpression(ctx.getText());
                literal.setType(BuiltinType.STRING);
                break;
            case ZenScriptLexer.NULL_LITERAL:
                literal = new NullExpression();
                break;
        }

        if (literal != null) {
            literal.setSourcePosition(ctx.literal());
            addAstNode(literal);
        }

        return literal;
    }

    @Override
    public ArrayLiteralExpression visitArrayLiteralExpression(ZenScriptParser.ArrayLiteralExpressionContext ctx) {
        if (ctx == null) return null;

        List<Expression> elements = ctx.expression().stream().map(this::visitExpression).filter(Objects::nonNull).collect(Collectors.toList());

        ArrayLiteralExpression arrayLiteral = new ArrayLiteralExpression(elements);
        arrayLiteral.setSourcePosition(ctx);
        addAstNode(arrayLiteral);

        return arrayLiteral;
    }

    @Override
    public MemberIndexExpression visitMemberIndexExpression(ZenScriptParser.MemberIndexExpressionContext ctx) {
        if (ctx == null) return null;

        Expression left = visitExpression(ctx.Left);
        Expression index = visitExpression(ctx.Index);

        MemberIndexExpression memberIndexExpr = new MemberIndexExpression(left, index);
        memberIndexExpr.setSourcePosition(ctx);
        addAstNode(memberIndexExpr);

        return memberIndexExpr;
    }

    @Override
    public IdentifierExpression visitIdentifierExpression(ZenScriptParser.IdentifierExpressionContext ctx) {
        if (ctx == null) return null;

        String name = ctx.getText();

        IdentifierExpression idExpr = new IdentifierExpression(name);
        idExpr.setSourcePosition(ctx);
        addAstNode(idExpr);

        return idExpr;
    }

    @Override
    public MapEntryExpression visitMapEntry(ZenScriptParser.MapEntryContext ctx) {
        if (ctx == null) return null;

        Expression key = visitExpression(ctx.Key);
        Expression value = visitExpression(ctx.Value);

        MapEntryExpression mapEntryExpr = new MapEntryExpression(key, value);
        mapEntryExpr.setSourcePosition(ctx);
        addAstNode(mapEntryExpr);

        return mapEntryExpr;
    }

    @Override
    public ASTNode visitBuiltinType(ZenScriptParser.BuiltinTypeContext ctx) {
        if (ctx == null) return null;

        Type type = null;

        switch (ctx.getStart().getType()) {
            case ZenScriptLexer.BOOL:
                type = BuiltinType.BOOL;
                break;
            case ZenScriptLexer.BYTE:
                type = BuiltinType.BYTE;
                break;
            case ZenScriptLexer.SHORT:
                type = BuiltinType.SHORT;
                break;
            case ZenScriptLexer.INT:
                type = BuiltinType.INT;
                break;
            case ZenScriptLexer.LONG:
                type = BuiltinType.LONG;
                break;
            case ZenScriptLexer.FLOAT:
                type = BuiltinType.FLOAT;
                break;
            case ZenScriptLexer.DOUBLE:
                type = BuiltinType.DOUBLE;
                break;
            case ZenScriptLexer.VOID:
                type = BuiltinType.VOID;
                break;
            case ZenScriptLexer.STRING:
                type = BuiltinType.STRING;
                break;
            case ZenScriptLexer.BOOL_OBJ:
                type = BuiltinType.BOOL_OBJ;
                break;
            case ZenScriptLexer.BYTE_OBJ:
                type = BuiltinType.BYTE_OBJ;
                break;
            case ZenScriptLexer.SHORT_OBJ:
                type = BuiltinType.SHORT_OBJ;
                break;
            case ZenScriptLexer.INT_OBJ:
                type = BuiltinType.INT_OBJ;
                break;
            case ZenScriptLexer.LONG_OBJ:
                type = BuiltinType.LONG_OBJ;
                break;
            case ZenScriptLexer.FLOAT_OBJ:
                type = BuiltinType.FLOAT_OBJ;
                break;
            case ZenScriptLexer.DOUBLE_OBJ:
                type = BuiltinType.DOUBLE_OBJ;
                break;
        }

        TypeNode typeNode = new TypeNode(type);
        typeNode.setSourcePosition(ctx);
        addAstNode(typeNode);

        return typeNode;
    }

    @Override
    public TypeNode visitReferenceType(ZenScriptParser.ReferenceTypeContext ctx) {
        if (ctx == null) return null;

        Type type = new ReferenceType(ctx.getText());

        TypeNode typeNode = new TypeNode(type);
        typeNode.setSourcePosition(ctx);
        addAstNode(typeNode);

        return typeNode;
    }

    @Override
    public TypeNode visitFunctionType(ZenScriptParser.FunctionTypeContext ctx) {
        if (ctx == null) return null;

        Type type = new FunctionType(ctx.getText());

        TypeNode functionTypeNode = new TypeNode(type);
        functionTypeNode.setSourcePosition(ctx);
        addAstNode(functionTypeNode);

        return functionTypeNode;
    }

    @Override
    public TypeNode visitArrayType(ZenScriptParser.ArrayTypeContext ctx) {
        if (ctx == null) return null;

        Type baseType = visitType(ctx.BaseType).getType();
        ArrayType arrayType = new ArrayType(baseType);

        TypeNode typeNode = new TypeNode(arrayType);
        addAstNode(typeNode);
        typeNode.setSourcePosition(ctx);

        return typeNode;
    }

    @Override
    public TypeNode visitListType(ZenScriptParser.ListTypeContext ctx) {
        if (ctx == null) return null;

        Type baseType = visitType(ctx.BaseType).getType();
        ListType listType = new ListType(baseType);

        TypeNode typeNode = new TypeNode(listType);
        addAstNode(typeNode);
        typeNode.setSourcePosition(ctx);

        return typeNode;
    }

    @Override
    public TypeNode visitMapType(ZenScriptParser.MapTypeContext ctx) {
        if (ctx == null) return null;

        Type keyType = visitType(ctx.KeyType).getType();
        Type ValueType = visitType(ctx.ValueType).getType();
        MapType mapType = new MapType(keyType, ValueType);

        TypeNode typeNode = new TypeNode(mapType);
        addAstNode(typeNode);
        typeNode.setSourcePosition(ctx);

        return typeNode;
    }

    @Override
    public IdentifierNode visitIdentifier(ZenScriptParser.IdentifierContext ctx) {
        if (ctx == null) return null;

        IdentifierNode idNode = new IdentifierNode(ctx.getText());
        idNode.setSourcePosition(ctx);
        addAstNode(idNode);

        return idNode;
    }

}
