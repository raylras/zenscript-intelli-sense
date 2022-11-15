package raylras.zen.ast;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.ast.decl.*;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.stmt.*;
import raylras.zen.ast.type.Declarator;
import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;
import raylras.zen.cst.ZenScriptParserBaseListener;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.CommonUtils;
import raylras.zen.util.Stack;

public class ASTBuilder extends ZenScriptParserBaseListener {

    private final String uri;
    private final Stack<ASTNode> nodeStack;
    private CompilationUnitNode compilationUnit;

    public ASTBuilder(String uri) {
        this.uri = uri;
        this.nodeStack = new ArrayStack<>();
    }

    public CompilationUnitNode compilationUnit() {
        return compilationUnit;
    }

    private void push(ASTNode node, ParserRuleContext ctx) {
        node.setTextRange(CommonUtils.getTextRange(ctx));
        ASTNode parent = nodeStack.peek();
        node.setParent(parent);
        parent.addChild(node);
        nodeStack.push(node);
    }

    private void push(ASTNode node, Token token) {
        node.setTextRange(CommonUtils.getTextRange(token));
        ASTNode parent = nodeStack.peek();
        node.setParent(parent);
        parent.addChild(node);
        nodeStack.push(node);
    }

    private void pop() {
        nodeStack.pop();
    }

    @Override
    public void enterCompilationUnit(ZenScriptParser.CompilationUnitContext ctx) {
        compilationUnit = new CompilationUnitNode(uri);
        compilationUnit.setTextRange(CommonUtils.getTextRange(ctx));
        nodeStack.push(compilationUnit);
    }

    @Override
    public void exitCompilationUnit(ZenScriptParser.CompilationUnitContext ctx) {
        pop();
    }

    @Override
    public void enterImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        push(new ImportDeclarationNode(), ctx);
    }

    @Override
    public void exitImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        pop();
    }

    @Override
    public void enterAlias(ZenScriptParser.AliasContext ctx) {
        push(new AliasNode(), ctx);
    }

    @Override
    public void exitAlias(ZenScriptParser.AliasContext ctx) {
        pop();
    }

    @Override
    public void enterFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        push(new FunctionDeclarationNode(), ctx);
    }

    @Override
    public void exitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        pop();
    }

    @Override
    public void enterParameter(ZenScriptParser.ParameterContext ctx) {
        push(new ParameterDeclarationNode(), ctx);
    }

    @Override
    public void exitParameter(ZenScriptParser.ParameterContext ctx) {
        pop();
    }

    @Override
    public void enterTypeAnnotation(ZenScriptParser.TypeAnnotationContext ctx) {
        push(new TypeAnnotationNode(), ctx);
    }

    @Override
    public void exitTypeAnnotation(ZenScriptParser.TypeAnnotationContext ctx) {
        pop();
    }

    @Override
    public void enterZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx) {
        push(new ZenClassDeclarationNode(), ctx);
    }

    @Override
    public void exitZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx) {
        pop();
    }

    @Override
    public void enterConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        push(new ConstructorDeclarationNode(), ctx);
    }

    @Override
    public void exitConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        pop();
    }

    @Override
    public void enterVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
        VariableDeclarationNode node = new VariableDeclarationNode();
        switch (ctx.Declarator.getType()) {
            case ZenScriptLexer.VAR:
                node.setDeclarator(Declarator.VAR);
                break;
            case ZenScriptLexer.VAL:
                node.setDeclarator(Declarator.VAL);
                break;
            case ZenScriptLexer.GLOBAL:
                node.setDeclarator(Declarator.GLOBAL);
                break;
            case ZenScriptLexer.STATIC:
                node.setDeclarator(Declarator.STATIC);
                break;
            default:
                node.setDeclarator(Declarator.NONE);
                break;
        }
        push(node, ctx);
    }

    @Override
    public void exitVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
        pop();
    }

    @Override
    public void enterBlockStatement(ZenScriptParser.BlockStatementContext ctx) {
        push(new BlockStatementNode(), ctx);
    }

    @Override
    public void exitBlockStatement(ZenScriptParser.BlockStatementContext ctx) {
        pop();
    }

    @Override
    public void enterReturnStatement(ZenScriptParser.ReturnStatementContext ctx) {
        push(new ReturnStatementNode(), ctx);
    }

    @Override
    public void exitReturnStatement(ZenScriptParser.ReturnStatementContext ctx) {
        pop();
    }

    @Override
    public void enterBreakStatement(ZenScriptParser.BreakStatementContext ctx) {
        push(new BreakStatementNode(), ctx);
    }

    @Override
    public void exitBreakStatement(ZenScriptParser.BreakStatementContext ctx) {
        pop();
    }

    @Override
    public void enterContinueStatement(ZenScriptParser.ContinueStatementContext ctx) {
        push(new ContinueStatementNode(), ctx);
    }

    @Override
    public void exitContinueStatement(ZenScriptParser.ContinueStatementContext ctx) {
        pop();
    }

    @Override
    public void enterIfElseStatement(ZenScriptParser.IfElseStatementContext ctx) {
        push(new IfElseStatementNode(), ctx);
    }

    @Override
    public void exitIfElseStatement(ZenScriptParser.IfElseStatementContext ctx) {
        pop();
    }

    @Override
    public void enterForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        push(new ForeachStatementNode(), ctx);
    }

    @Override
    public void exitForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        pop();
    }

    @Override
    public void enterWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        push(new WhileStatementNode(), ctx);
    }

    @Override
    public void exitWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        pop();
    }

    @Override
    public void enterExpressionStatement(ZenScriptParser.ExpressionStatementContext ctx) {
        push(new ExpressionStatementNode(), ctx);
    }

    @Override
    public void exitExpressionStatement(ZenScriptParser.ExpressionStatementContext ctx) {
        pop();
    }

    @Override
    public void enterMemberAccessExpression(ZenScriptParser.MemberAccessExpressionContext ctx) {
        push(new MemberAccessExpressionNode(), ctx);
    }

    @Override
    public void exitMemberAccessExpression(ZenScriptParser.MemberAccessExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterMapLiteralExpression(ZenScriptParser.MapLiteralExpressionContext ctx) {
        push(new MapLiteralExpressionNode(), ctx);
    }

    @Override
    public void exitMapLiteralExpression(ZenScriptParser.MapLiteralExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterBracketHandlerExpression(ZenScriptParser.BracketHandlerExpressionContext ctx) {
        push(new BracketHandlerExpressionNode(ctx.getText()), ctx);
    }

    @Override
    public void exitBracketHandlerExpression(ZenScriptParser.BracketHandlerExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterArrayLiteralExpression(ZenScriptParser.ArrayLiteralExpressionContext ctx) {
        push(new ArrayLiteralExpressionNode(), ctx);
    }

    @Override
    public void exitArrayLiteralExpression(ZenScriptParser.ArrayLiteralExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterUnaryExpression(ZenScriptParser.UnaryExpressionContext ctx) {
        push(new UnaryExpressionNode(Operator.of(ctx.Operator.getText())), ctx);
    }

    @Override
    public void exitUnaryExpression(ZenScriptParser.UnaryExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterIntRangeExpression(ZenScriptParser.IntRangeExpressionContext ctx) {
        push(new IntRangeExpressionNode(), ctx);
    }

    @Override
    public void exitIntRangeExpression(ZenScriptParser.IntRangeExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterMemberIndexExpression(ZenScriptParser.MemberIndexExpressionContext ctx) {
        push(new MemberIndexExpressionNode(), ctx);
    }

    @Override
    public void exitMemberIndexExpression(ZenScriptParser.MemberIndexExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterParensExpression(ZenScriptParser.ParensExpressionContext ctx) {
        push(new ParensExpressionNode(), ctx);
    }

    @Override
    public void exitParensExpression(ZenScriptParser.ParensExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterCallExpression(ZenScriptParser.CallExpressionContext ctx) {
        push(new CallExpressionNode(), ctx);
    }

    @Override
    public void exitCallExpression(ZenScriptParser.CallExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterThisExpression(ZenScriptParser.ThisExpressionContext ctx) {
        push(new ThisExpressionNode(), ctx);
    }

    @Override
    public void exitThisExpression(ZenScriptParser.ThisExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterTypeAssertionExpression(ZenScriptParser.TypeAssertionExpressionContext ctx) {
        push(new TypeAssertionExpressionNode(), ctx);
    }

    @Override
    public void exitTypeAssertionExpression(ZenScriptParser.TypeAssertionExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterFunctionExpression(ZenScriptParser.FunctionExpressionContext ctx) {
        push(new FunctionExpressionNode(), ctx);
    }

    @Override
    public void exitFunctionExpression(ZenScriptParser.FunctionExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterBinaryExpression(ZenScriptParser.BinaryExpressionContext ctx) {
        push(new BinaryExpressionNode(Operator.of(ctx.Operator.getText())), ctx);
    }

    @Override
    public void exitBinaryExpression(ZenScriptParser.BinaryExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterAssignmentExpression(ZenScriptParser.AssignmentExpressionContext ctx) {
        push(new BinaryExpressionNode(Operator.of(ctx.Operator.getText())), ctx);
    }

    @Override
    public void exitAssignmentExpression(ZenScriptParser.AssignmentExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterTernaryExpression(ZenScriptParser.TernaryExpressionContext ctx) {
        push(new TernaryExpressionNode(), ctx);
    }

    @Override
    public void exitTernaryExpression(ZenScriptParser.TernaryExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterMapEntry(ZenScriptParser.MapEntryContext ctx) {
        push(new MapEntryNode(), ctx);
    }

    @Override
    public void exitMapEntry(ZenScriptParser.MapEntryContext ctx) {
        pop();
    }

    @Override
    public void enterTypeName(ZenScriptParser.TypeNameContext ctx) {
        push(new TypeNameNode(ctx.getText()), ctx);
    }

    @Override
    public void exitTypeName(ZenScriptParser.TypeNameContext ctx) {
        pop();
    }

    @Override
    public void visitTerminal(TerminalNode terminal) {
        Token token = terminal.getSymbol();
        ASTNode node;
        switch (token.getType()) {
            case ZenScriptLexer.IDENTIFIER:
                node = new IdentifierNode(token.getText());
                break;
            case ZenScriptLexer.INT_LITERAL:
            case ZenScriptLexer.HEX_LITERAL:
                node = new NumericLiteralExpressionNode(token.getText());
                ((NumericLiteralExpressionNode)node).setNumericKind(NumericKind.INT);
                break;
            case ZenScriptLexer.LONG_LITERAL:
                node = new NumericLiteralExpressionNode(token.getText());
                ((NumericLiteralExpressionNode)node).setNumericKind(NumericKind.LONG);
                break;
            case ZenScriptLexer.FLOAT_LITERAL:
                node = new NumericLiteralExpressionNode(token.getText());
                ((NumericLiteralExpressionNode)node).setNumericKind(NumericKind.FLOAT);
                break;
            case ZenScriptLexer.DOUBLE_LITERAL:
                node = new NumericLiteralExpressionNode(token.getText());
                ((NumericLiteralExpressionNode)node).setNumericKind(NumericKind.DOUBLE);
                break;
            case ZenScriptLexer.BOOL_LITERAL:
                node = new BoolLiteralNode(token.getText());
                break;
            case ZenScriptLexer.STRING_LITERAL:
                node = new StringLiteralExpressionNode(token.getText());
                break;
            default:
                node = null;
                break;
        }
        if (node != null) {
            push(node, token);
            pop();
        }
    }

}
