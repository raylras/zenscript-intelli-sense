package raylras.zen.ast.visit;

import raylras.zen.antlr.ZenScriptLexer;
import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.antlr.ZenScriptParserBaseVisitor;
import raylras.zen.ast.ASTBuilder;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.Range;
import raylras.zen.ast.decl.TypeDeclaration;
import raylras.zen.ast.decl.VariableDeclaration;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.stmt.*;

import java.util.List;
import java.util.stream.Collectors;

public final class StatementVisitor extends ZenScriptParserBaseVisitor<Statement> {

    private final ASTBuilder builder;

    public StatementVisitor(ASTBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Statement visitStatement(ZenScriptParser.StatementContext ctx) {
        if (ctx == null) return null;
        return super.visitStatement(ctx);
    }

    @Override
    public BlockNode visitBlock(ZenScriptParser.BlockContext ctx) {
        if (ctx == null) return null;

        builder.pushScope();
        List<Statement> statements = ctx.statement().stream().map(this::visitStatement).collect(Collectors.toList());
        builder.popScope();

        BlockNode block = new BlockNode(statements);
        block.setRange(Range.of(ctx));

        return block;
    }

    @Override
    public BlockNode visitBlockStatement(ZenScriptParser.BlockStatementContext ctx) {
        if (ctx == null) return null;
        return this.visitBlock(ctx.block());
    }

    @Override
    public ReturnStatement visitReturnStatement(ZenScriptParser.ReturnStatementContext ctx) {
        if (ctx == null) return null;

        Expression expr = builder.getExprVisitor().visitExpression(ctx.expression());

        ReturnStatement returnStmt = new ReturnStatement(expr);
        returnStmt.setRange(Range.of(ctx));

        return returnStmt;
    }

    @Override
    public BreakStatement visitBreakStatement(ZenScriptParser.BreakStatementContext ctx) {
        if (ctx == null) return null;

        BreakStatement breakStmt = new BreakStatement();
        breakStmt.setRange(Range.of(ctx));

        return breakStmt;
    }

    @Override
    public ContinueStatement visitContinueStatement(ZenScriptParser.ContinueStatementContext ctx) {
        if (ctx == null) return null;

        ContinueStatement contStmt = new ContinueStatement();
        contStmt.setRange(Range.of(ctx));

        return contStmt;
    }

    @Override
    public IfElseStatement visitIfElseStatement(ZenScriptParser.IfElseStatementContext ctx) {
        if (ctx == null) return null;

        Expression condition = builder.getExprVisitor().visitExpression(ctx.expression());
        Statement thenStmt = ctx.statement(0).accept(this);
        Statement elseStmt = this.visitStatement(ctx.statement(1));

        IfElseStatement ifStmt = new IfElseStatement(condition, thenStmt, elseStmt);
        ifStmt.setRange(Range.of(ctx));

        return ifStmt;
    }

    @Override
    public ForeachStatement visitForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        if (ctx == null) return null;

        builder.pushScope();
        List<VariableDeclaration> varDecls = ctx.identifier().stream().map(builder.getDeclVisitor()::visitVariableDeclaration).collect(Collectors.toList());
        Expression expr = builder.getExprVisitor().visitExpression(ctx.expression());
        BlockNode block = this.visitBlock(ctx.block());
        builder.popScope();

        ForeachStatement foreachStmt = new ForeachStatement(varDecls, expr, block);
        foreachStmt.setRange(Range.of(ctx));

        return foreachStmt;
    }

    @Override
    public WhileStatement visitWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        if (ctx == null) return null;

        Expression condition = builder.getExprVisitor().visitExpression(ctx.expression());
        BlockNode block = this.visitBlock(ctx.block());

        WhileStatement whileStmt = new WhileStatement(condition, block);
        whileStmt.setRange(Range.of(ctx));

        return whileStmt;
    }

    @Override
    public VariableDeclStatement visitVariableDeclStatement(ZenScriptParser.VariableDeclStatementContext ctx) {
        if (ctx == null) return null;

        String name = ctx.identifier().getText();
        Expression expr = builder.getExprVisitor().visitExpression(ctx.expression());
        TypeDeclaration typeDecl = builder.getDeclVisitor().visitTypeDeclaration(ctx.type());

        VariableDeclStatement varDecl = new VariableDeclStatement(name, typeDecl, expr);
        varDecl.setRange(Range.of(ctx));
        if (typeDecl != null) {
            varDecl.setType(typeDecl.getType());
        } else {
            varDecl.setType(expr.getType());
        }
        varDecl.setIdRange(Range.of(ctx.identifier()));
        switch (ctx.Modifier.getType()) {
            case ZenScriptLexer.GLOBAL:
                varDecl.setGlobal(true);
            case ZenScriptLexer.STATIC:
                varDecl.setStatic(true);
            case ZenScriptLexer.VAL:
                varDecl.setFinal(true);
        }

        builder.addSymbolToCurrentScope(name, varDecl);

        return varDecl;
    }

    @Override
    public ExpressionStatement visitExpressionStatement(ZenScriptParser.ExpressionStatementContext ctx) {
        if (ctx == null) return null;

        Expression expr = builder.getExprVisitor().visitExpression(ctx.expression());

        ExpressionStatement exprStmt = new ExpressionStatement(expr);
        exprStmt.setRange(Range.of(ctx));

        return exprStmt;
    }

}
