package raylras.zen.ast.visit;

import raylras.zen.antlr.ZenScriptLexer;
import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.antlr.ZenScriptParserBaseVisitor;
import raylras.zen.ast.*;
import raylras.zen.ast.decl.ParameterDeclaration;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.type.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ExpressionVisitor extends ZenScriptParserBaseVisitor<Expression> {

    private final ASTBuilder builder;

    public ExpressionVisitor(ASTBuilder builder) {
        this.builder = builder;
    }

    public Expression visitExpression(ZenScriptParser.ExpressionContext ctx) {
        if (ctx == null) return null;
        return ctx.accept(this);
    }

    @Override
    public Expression visitDefaultValue(ZenScriptParser.DefaultValueContext ctx) {
        if (ctx == null) return null;
        return ctx.accept(this);
    }

    @Override
    public MemberAccess visitMemberAccessExpression(ZenScriptParser.MemberAccessExpressionContext ctx) {
        if (ctx == null) return null;

        Expression left = ctx.Left.accept(this);
        String right = ctx.Right.getText();

        MemberAccess memberAccess = new MemberAccess(left, right);
        memberAccess.setRange(Range.of(ctx));

        return memberAccess;
    }

    @Override
    public MapLiteral visitMapLiteralExpression(ZenScriptParser.MapLiteralExpressionContext ctx) {
        if (ctx == null) return null;

        List<MapEntryExpression> entries = ctx.mapEntry().stream().map(this::visitMapEntry).collect(Collectors.toList());

        MapLiteral mapExpr = new MapLiteral(entries);
        mapExpr.setRange(Range.of(ctx));

        return mapExpr;
    }

    @Override
    public BracketHandler visitBracketHandlerExpression(ZenScriptParser.BracketHandlerExpressionContext ctx) {
        if (ctx == null) return null;

        String literal = ctx.getText();

        BracketHandler bracketExpr = new BracketHandler(literal);
        bracketExpr.setRange(Range.of(ctx));

        return bracketExpr;
    }

    @Override
    public TypeCastExpression visitTypeCastExpression(ZenScriptParser.TypeCastExpressionContext ctx) {
        if (ctx == null) return null;

        Expression expr = ctx.expression().accept(this);
        Type type = builder.getTypeVisitor().visitType(ctx.type());

        TypeCastExpression castExpr = new TypeCastExpression(expr);
        expr.setType(type);
        castExpr.setType(type);
        castExpr.setRange(Range.of(ctx));

        return castExpr;
    }

    @Override
    public Expression visitLiteralExpression(ZenScriptParser.LiteralExpressionContext ctx) {
        if (ctx == null) return null;

        Expression literal = null;
        Type type = null;
        switch (ctx.literal().getStart().getType()) {
            case ZenScriptLexer.BOOLEAN_LITERAL -> {
                literal = new BoolLiteral(ctx.getText());
                type = Types.BOOL;
            }
            case ZenScriptLexer.DECIMAL_LITERAL, ZenScriptLexer.HEX_LITERAL -> {
                literal = new IntLiteral(ctx.getText());
                type = ctx.getText().toUpperCase().endsWith("L") ? Types.LONG : Types.INT;
            }
            case ZenScriptLexer.FLOATING_LITERAL -> {
                literal = new FloatLiteral(ctx.getText());
                type = ctx.getText().toUpperCase().endsWith("F") ? Types.FLOAT : Types.DOUBLE;
            }
            case ZenScriptLexer.STRING_LITERAL -> {
                literal = new StringLiteral(ctx.getText());
                type = Types.STRING;
            }
            case ZenScriptLexer.NULL_LITERAL -> literal = new NullExpression();
        }
        if (literal != null) {
            literal.setType(type);
            literal.setRange(Range.of(ctx));
        }

        return literal;
    }

    @Override
    public ArrayLiteral visitArrayLiteralExpression(ZenScriptParser.ArrayLiteralExpressionContext ctx) {
        if (ctx == null) return null;

        List<Expression> elements = ctx.expression().stream().map(this::visitExpression).collect(Collectors.toList());

        ArrayLiteral arrayExpr = new ArrayLiteral(elements);
        arrayExpr.setRange(Range.of(ctx));

        return arrayExpr;
    }

    @Override
    public UnaryExpression visitUnaryExpression(ZenScriptParser.UnaryExpressionContext ctx) {
        if (ctx == null) return null;

        Expression expr = ctx.expression().accept(this);
        Operator.Unary operator = Operator.getUnary(ctx.Operator.getText());

        UnaryExpression unary = new UnaryExpression(expr, operator);
        unary.setRange(Range.of(ctx));

        return unary;
    }

    @Override
    public RangeExpression visitRangeExpression(ZenScriptParser.RangeExpressionContext ctx) {
        if (ctx == null) return null;

        Expression from = ctx.From.accept(this);
        Expression to = ctx.To.accept(this);

        RangeExpression rangeExpr = new RangeExpression(from, to);
        rangeExpr.setRange(Range.of(ctx));

        return rangeExpr;
    }

    @Override
    public MemberIndexExpression visitMemberIndexExpression(ZenScriptParser.MemberIndexExpressionContext ctx) {
        if (ctx == null) return null;

        Expression left = ctx.Left.accept(this);
        Expression index = ctx.Index.accept(this);

        MemberIndexExpression indexExpr = new MemberIndexExpression(left, index);
        indexExpr.setRange(Range.of(ctx));

        return indexExpr;
    }

    @Override
    public Expression visitParensExpression(ZenScriptParser.ParensExpressionContext ctx) {
        if (ctx == null) return null;
        return ctx.expression().accept(this);
    }

    @Override
    public ArgumentsExpression visitArgumentsExpression(ZenScriptParser.ArgumentsExpressionContext ctx) {
        if (ctx == null) return null;

        Expression left = ctx.Left.accept(this);
        List<Expression> arguments = ctx.expression().stream().skip(1).map(this::visitExpression).collect(Collectors.toList());

        ArgumentsExpression argsExpr = new ArgumentsExpression(left, arguments);
        argsExpr.setRange(Range.of(ctx));

        return argsExpr;
    }

    @Override
    public ThisExpression visitThisExpression(ZenScriptParser.ThisExpressionContext ctx) {
        if (ctx == null) return null;

        ThisExpression thisExpr = new ThisExpression();
        thisExpr.setRange(Range.of(ctx));

        return thisExpr;
    }

    @Override
    public FunctionExpression visitFunctionExpression(ZenScriptParser.FunctionExpressionContext ctx) {
        if (ctx == null) return null;

        builder.pushScope();
        List<ParameterDeclaration> params = ctx.formalParameter().stream().map(builder.getDeclVisitor()::visitFormalParameter).collect(Collectors.toList());
        BlockNode block = builder.getStmtVisitor().visitBlock(ctx.block());
        builder.popScope();

        FunctionExpression funcExpr = new FunctionExpression(params, block);
        funcExpr.setRange(Range.of(ctx));

        return funcExpr;
    }

    @Override
    public BinaryExpression visitBinaryExpression(ZenScriptParser.BinaryExpressionContext ctx) {
        if (ctx == null) return null;

        Expression left = ctx.Left.accept(this);
        Expression right = ctx.Right.accept(this);
        Operator.Binary operator = Operator.getBinary(ctx.Operator.getText());

        BinaryExpression binary = new BinaryExpression(left, right, operator);
        binary.setRange(Range.of(ctx));

        return binary;
    }

    @Override
    public AssignmentExpression visitAssignmentExpression(ZenScriptParser.AssignmentExpressionContext ctx) {
        if (ctx == null) return null;

        Expression left = ctx.Left.accept(this);
        Expression right = ctx.Right.accept(this);
        Operator.Assignment operator = Operator.getAssignment(ctx.Operator.getText());

        AssignmentExpression assignExpr = new AssignmentExpression(left, right, operator);
        assignExpr.setRange(Range.of(ctx));

        return assignExpr;
    }

    @Override
    public VarAccessExpression visitVarAccessExpression(ZenScriptParser.VarAccessExpressionContext ctx) {
        if (ctx == null) return null;

        Optional<Symbol> symbol = builder.findSymbolInCurrentScope(ctx.getText());

        VarAccessExpression varAccess = new VarAccessExpression(ctx.identifier().getText());
        varAccess.setSymbol(symbol.orElse(null));
        varAccess.setType(symbol.map(Symbol::node).map(Node::getType).orElse(null));
        varAccess.setRange(Range.of(ctx));

        return varAccess;
    }

    @Override
    public TernaryExpression visitTrinaryExpression(ZenScriptParser.TrinaryExpressionContext ctx) {
        if (ctx == null) return null;

        Expression condition = ctx.Condition.accept(this);
        Expression thenExpr = ctx.Then.accept(this);
        Expression elseExpr = ctx.Else.accept(this);

        TernaryExpression ternary = new TernaryExpression(condition, thenExpr, elseExpr);
        ternary.setRange(Range.of(ctx));

        return ternary;
    }

    @Override
    public MapEntryExpression visitMapEntry(ZenScriptParser.MapEntryContext ctx) {
        if (ctx == null) return null;

        Expression key;
        Expression value;

        // if the name of key or value is found in the symbol table,
        // it is a var access expr, otherwise it is a string literal expr

        if (ctx.Key.getClass() == ZenScriptParser.VarAccessExpressionContext.class) {
            ZenScriptParser.IdentifierContext idCtx = ((ZenScriptParser.VarAccessExpressionContext) ctx.Key).identifier();
            key = builder.findSymbolInCurrentScope(idCtx.getText())
                    .map(symbol -> ctx.Key.accept(this))
                    .orElseGet(() -> {
                        StringLiteral stringExpr = new StringLiteral(idCtx.getText());
                        stringExpr.setRange(Range.of(idCtx));
                        return stringExpr;
                    });
        } else {
            key = ctx.Key.accept(this);
        }

        if (ctx.Value.getClass() == ZenScriptParser.VarAccessExpressionContext.class) {
            ZenScriptParser.IdentifierContext idCtx = ((ZenScriptParser.VarAccessExpressionContext) ctx.Value).identifier();
            value = builder.findSymbolInCurrentScope(idCtx.getText())
                    .map(symbol -> ctx.Value.accept(this))
                    .orElseGet(() -> {
                        StringLiteral stringExpr = new StringLiteral(idCtx.getText());
                        stringExpr.setRange(Range.of(idCtx));
                        return stringExpr;
                    });
        } else {
            value = ctx.Value.accept(this);
        }

        MapEntryExpression entry = new MapEntryExpression(key, value);
        entry.setRange(Range.of(ctx));

        return entry;
    }

}
