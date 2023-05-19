package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.Visitor;
import raylras.zen.code.data.CompletionData;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.util.Range;

import java.util.List;

/**
 * This class resolve for function invocation data
 */
public class FunctionInvocationResolver extends AbstractPositionSearchResolver<ZenScriptParser.CallExprContext> {
    public FunctionInvocationResolver(Range cursorPos) {
        super(cursorPos);
    }


    public ZenScriptParser.CallExprContext resolve(ParseTree node) {
        return node.accept(this);
    }


    @Override
    public ZenScriptParser.CallExprContext visitCallExpr(ZenScriptParser.CallExprContext ctx) {
        if (!isNodeContainsCursor(ctx.PAREN_OPEN().getSymbol(), ctx.PAREN_CLOSE().getSymbol())) {
            return visitChildren(ctx);
        }

        // handle possible inner call in FunctionExpr

        ZenScriptParser.CallExprContext result = ctx;

        List<ZenScriptParser.ExpressionContext> expressions = ctx.expression();
        for (int i = 1; i < expressions.size(); i++) {
            ZenScriptParser.ExpressionContext expressionContext = expressions.get(i);

            ZenScriptParser.CallExprContext possibleInner = expressionContext.accept(this);

            if (possibleInner != null) {
                result = possibleInner;
                break;
            }
        }

        return result;

    }
}
