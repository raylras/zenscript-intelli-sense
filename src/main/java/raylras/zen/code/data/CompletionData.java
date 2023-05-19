package raylras.zen.code.data;

import org.antlr.v4.runtime.ParserRuleContext;
import raylras.zen.code.parser.ZenScriptParser;

public class CompletionData {
    public final CompletionKind kind;
    public final ParserRuleContext node;
    public final String completingString;

    public CompletionData(CompletionKind kind, ParserRuleContext node, String completingString) {
        this.kind = kind;
        this.node = node;
        this.completingString = completingString;
    }

    public ZenScriptParser.ExpressionContext getQualifierExpression() {
        ZenScriptParser.ExpressionContext qualifierExpr;
        if (node instanceof ZenScriptParser.MemberAccessExprContext) {
            return ((ZenScriptParser.MemberAccessExprContext) node).Left;
        } else if (node instanceof ZenScriptParser.ArrayIndexExprContext) {
            return ((ZenScriptParser.ArrayIndexExprContext) node).Left;
        } else if (node instanceof ZenScriptParser.IntRangeExprContext) {
            return ((ZenScriptParser.IntRangeExprContext) node).From;
        }
        return null;
    }

    public static final CompletionData NONE = new CompletionData(CompletionKind.NONE, null, "");

}
