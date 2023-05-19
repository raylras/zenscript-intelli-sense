package raylras.zen.code.resolve;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.Visitor;
import raylras.zen.code.data.CompletionData;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;

public abstract class AbstractPositionSearchResolver<T> extends Visitor<T> {
    protected final Range cursorPos;

    protected AbstractPositionSearchResolver(Range cursorPos) {
        this.cursorPos = cursorPos;
    }

    @Override
    protected T aggregateResult(T aggregate, T nextResult) {
        if (nextResult == null) {
            return aggregate;
        }
        return nextResult;
    }

    @Override
    protected boolean shouldVisitNextChild(RuleNode node, T currentResult) {
        return currentResult == null;
    }


    @Override
    public T visitChildren(RuleNode node) {
        // skip not target position
        if (!isNodeContainsCursor(node)) {
            return null;
        }
        return super.visitChildren(node);
    }


    protected boolean isNodeContainsCursor(ParseTree node) {
        if (node == null) {
            return false;
        }
        Range nodeRange = Ranges.from(node);
        return Ranges.isRangeContainsPosition(nodeRange, cursorPos.startLine, cursorPos.startColumn);
    }

    protected boolean isNodeContainsCursor(ParseTree node, TerminalNode possibleNextDOT) {
        if (possibleNextDOT != null) {
            return isNodeContainsCursor(possibleNextDOT);
        }
        return isNodeContainsCursor(node);

    }

    // if node is possibly unavailable, calculate range
    protected boolean isNodeContainsCursor(ParserRuleContext parentNode, Token previousToken) {
        Range nodeRange = new Range(
            previousToken.getLine() - 1, previousToken.getCharPositionInLine() + 1,
            parentNode.stop.getLine() - 1, parentNode.stop.getCharPositionInLine() + parentNode.stop.getText().length());
        return Ranges.isRangeContainsPosition(nodeRange, cursorPos.startLine, cursorPos.startColumn);
    }


    protected boolean isNodeContainsCursor(Token headToken, Token tailToken) {
        Range nodeRange = new Range(
            headToken.getLine() - 1, headToken.getCharPositionInLine() + 1,
            tailToken.getLine() - 1, tailToken.getCharPositionInLine() + tailToken.getText().length() - 1);
        return Ranges.isRangeContainsPosition(nodeRange, cursorPos.startLine, cursorPos.startColumn);
    }

}
