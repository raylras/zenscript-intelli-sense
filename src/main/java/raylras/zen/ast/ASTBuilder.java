package raylras.zen.ast;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.ast.decl.*;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.stmt.*;
import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;
import raylras.zen.cst.ZenScriptParserBaseListener;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.Stack;

public class ASTBuilder extends ZenScriptParserBaseListener {

    private final Stack<ASTNode> nodeStack;
    private CompilationUnitNode compilationUnit;

    public ASTBuilder() {
        this.nodeStack = new ArrayStack<>();
    }

    public CompilationUnitNode compilationUnit() {
        return compilationUnit;
    }

    private void push(ASTNode child) {
        ASTNode parent = nodeStack.peek();
        child.setParent(parent);
        parent.addChild(child);
        nodeStack.push(child);
    }

    private void pop() {
        nodeStack.pop();
    }

    @Override
    public void enterCompilationUnit(ZenScriptParser.CompilationUnitContext ctx) {
        compilationUnit = new CompilationUnitNode();
        nodeStack.push(compilationUnit);
    }

    @Override
    public void exitCompilationUnit(ZenScriptParser.CompilationUnitContext ctx) {
        pop();
    }

    @Override
    public void enterImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        push(new ImportDeclarationNode());
    }

    @Override
    public void exitImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        pop();
    }

    @Override
    public void enterAlias(ZenScriptParser.AliasContext ctx) {
        push(new AliasNode());
    }

    @Override
    public void exitAlias(ZenScriptParser.AliasContext ctx) {
        pop();
    }

    @Override
    public void enterFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        push(new FunctionDeclarationNode());
    }

    @Override
    public void exitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        pop();
    }

    @Override
    public void enterParameter(ZenScriptParser.ParameterContext ctx) {
        push(new ParameterDeclarationNode());
    }

    @Override
    public void exitParameter(ZenScriptParser.ParameterContext ctx) {
        pop();
    }

    @Override
    public void enterTypeAnnotation(ZenScriptParser.TypeAnnotationContext ctx) {
        push(new TypeAnnotationNode());
    }

    @Override
    public void exitTypeAnnotation(ZenScriptParser.TypeAnnotationContext ctx) {
        pop();
    }

    @Override
    public void enterZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx) {
        push(new ZenClassDeclarationNode());
    }

    @Override
    public void exitZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx) {
        pop();
    }

    @Override
    public void enterConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        push(new ConstructorDeclarationNode());
    }

    @Override
    public void exitConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        pop();
    }

    @Override
    public void enterBlock(ZenScriptParser.BlockContext ctx) {
        push(new BlockNode());
    }

    @Override
    public void exitBlock(ZenScriptParser.BlockContext ctx) {
        pop();
    }

    @Override
    public void enterVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
        push(new VariableDeclarationNode());
    }

    @Override
    public void exitVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
        pop();
    }

    @Override
    public void enterBlockStatement(ZenScriptParser.BlockStatementContext ctx) {
        push(new BreakStatementNode());
    }

    @Override
    public void exitBlockStatement(ZenScriptParser.BlockStatementContext ctx) {
        pop();
    }

    @Override
    public void enterReturnStatement(ZenScriptParser.ReturnStatementContext ctx) {
        push(new ReturnStatementNode());
    }

    @Override
    public void exitReturnStatement(ZenScriptParser.ReturnStatementContext ctx) {
        pop();
    }

    @Override
    public void enterBreakStatement(ZenScriptParser.BreakStatementContext ctx) {
        push(new BreakStatementNode());
    }

    @Override
    public void exitBreakStatement(ZenScriptParser.BreakStatementContext ctx) {
        pop();
    }

    @Override
    public void enterContinueStatement(ZenScriptParser.ContinueStatementContext ctx) {
        push(new ContinueStatementNode());
    }

    @Override
    public void exitContinueStatement(ZenScriptParser.ContinueStatementContext ctx) {
        pop();
    }

    @Override
    public void enterIfElseStatement(ZenScriptParser.IfElseStatementContext ctx) {
        push(new IfElseStatementNode());
    }

    @Override
    public void exitIfElseStatement(ZenScriptParser.IfElseStatementContext ctx) {
        pop();
    }

    @Override
    public void enterForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        push(new ForeachStatementNode());
    }

    @Override
    public void exitForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        pop();
    }

    @Override
    public void enterWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        push(new WhileStatementNode());
    }

    @Override
    public void exitWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        pop();
    }

    @Override
    public void enterExpressionStatement(ZenScriptParser.ExpressionStatementContext ctx) {
        push(new ExpressionStatementNode());
    }

    @Override
    public void exitExpressionStatement(ZenScriptParser.ExpressionStatementContext ctx) {
        pop();
    }

    @Override
    public void enterMemberAccessExpression(ZenScriptParser.MemberAccessExpressionContext ctx) {
        push(new MemberAccessExpressionNode());
    }

    @Override
    public void exitMemberAccessExpression(ZenScriptParser.MemberAccessExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterMapLiteralExpression(ZenScriptParser.MapLiteralExpressionContext ctx) {
        push(new MapLiteralExpressionNode());
    }

    @Override
    public void exitMapLiteralExpression(ZenScriptParser.MapLiteralExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterBracketHandlerExpression(ZenScriptParser.BracketHandlerExpressionContext ctx) {
        push(new BracketHandlerExpressionNode(ctx.getText()));
    }

    @Override
    public void exitBracketHandlerExpression(ZenScriptParser.BracketHandlerExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterArrayLiteralExpression(ZenScriptParser.ArrayLiteralExpressionContext ctx) {
        push(new ArrayLiteralExpressionNode());
    }

    @Override
    public void exitArrayLiteralExpression(ZenScriptParser.ArrayLiteralExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterUnaryExpression(ZenScriptParser.UnaryExpressionContext ctx) {
        push(new UnaryExpressionNode(Operator.of(ctx.Operator.getText())));
    }

    @Override
    public void exitUnaryExpression(ZenScriptParser.UnaryExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterIntRangeExpression(ZenScriptParser.IntRangeExpressionContext ctx) {
        push(new IntRangeExpressionNode());
    }

    @Override
    public void exitIntRangeExpression(ZenScriptParser.IntRangeExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterMemberIndexExpression(ZenScriptParser.MemberIndexExpressionContext ctx) {
        push(new MemberIndexExpressionNode());
    }

    @Override
    public void exitMemberIndexExpression(ZenScriptParser.MemberIndexExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterParensExpression(ZenScriptParser.ParensExpressionContext ctx) {
        push(new ParensExpressionNode());
    }

    @Override
    public void exitParensExpression(ZenScriptParser.ParensExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterArgumentsExpression(ZenScriptParser.ArgumentsExpressionContext ctx) {
        push(new ArgumentsExpressionNode());
    }

    @Override
    public void exitArgumentsExpression(ZenScriptParser.ArgumentsExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterThisExpression(ZenScriptParser.ThisExpressionContext ctx) {
        push(new ThisExpressionNode());
    }

    @Override
    public void exitThisExpression(ZenScriptParser.ThisExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterTypeAssertionExpression(ZenScriptParser.TypeAssertionExpressionContext ctx) {
        push(new TypeAssertionExpressionNode());
    }

    @Override
    public void exitTypeAssertionExpression(ZenScriptParser.TypeAssertionExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterFunctionExpression(ZenScriptParser.FunctionExpressionContext ctx) {
        push(new FunctionExpressionNode());
    }

    @Override
    public void exitFunctionExpression(ZenScriptParser.FunctionExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterBinaryExpression(ZenScriptParser.BinaryExpressionContext ctx) {
        push(new BinaryExpressionNode(Operator.of(ctx.Operator.getText())));
    }

    @Override
    public void exitBinaryExpression(ZenScriptParser.BinaryExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterAssignmentExpression(ZenScriptParser.AssignmentExpressionContext ctx) {
        push(new BinaryExpressionNode(Operator.of(ctx.Operator.getText())));
    }

    @Override
    public void exitAssignmentExpression(ZenScriptParser.AssignmentExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterTernaryExpression(ZenScriptParser.TernaryExpressionContext ctx) {
        push(new TernaryExpressionNode());
    }

    @Override
    public void exitTernaryExpression(ZenScriptParser.TernaryExpressionContext ctx) {
        pop();
    }

    @Override
    public void enterMapEntry(ZenScriptParser.MapEntryContext ctx) {
        push(new MapEntryExpressionNode());
    }

    @Override
    public void exitMapEntry(ZenScriptParser.MapEntryContext ctx) {
        pop();
    }

    @Override
    public void enterTypeName(ZenScriptParser.TypeNameContext ctx) {
        push(new TypeNameNode(ctx.getText()));
    }

    @Override
    public void exitTypeName(ZenScriptParser.TypeNameContext ctx) {
        pop();
    }

    @Override
    public void visitTerminal(TerminalNode terminal) {
        Token token = terminal.getSymbol();
        if (token.getType() == ZenScriptLexer.IDENTIFIER) {
            IdentifierNode node = new IdentifierNode(token.getText());
            push(node);
            pop();
        } else if (token.getType() == ZenScriptLexer.INT_LITERAL
                || token.getType() == ZenScriptLexer.LONG_LITERAL
                || token.getType() == ZenScriptLexer.HEX_LITERAL
                || token.getType() == ZenScriptLexer.FLOAT_LITERAL
                || token.getType() == ZenScriptLexer.DOUBLE_LITERAL) {
            NumericLiteralNode node = new NumericLiteralNode(token.getText());
            push(node);
            pop();
        } else if (token.getType() == ZenScriptLexer.BOOL_LITERAL) {
            BoolLiteralNode node = new BoolLiteralNode(token.getText());
            push(node);
            pop();
        } else if (token.getType() == ZenScriptLexer.STRING_LITERAL) {
            StringLiteralNode node = new StringLiteralNode(token.getText());
            push(node);
            pop();
        }
    }

}
