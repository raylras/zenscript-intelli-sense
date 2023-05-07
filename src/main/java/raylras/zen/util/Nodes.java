package raylras.zen.util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayDeque;
import java.util.Queue;

public class Nodes {

    public static ParseTree getNodeAtPosition(ParseTree start, int line, int column) {
        Queue<ParseTree> queue = new ArrayDeque<>();
        queue.add(start);
        ParseTree found = null;
        while (!queue.isEmpty()) {
            ParseTree node = queue.poll();
            Range range = getRange(node);
            if (Ranges.isRangeContainsPosition(range, line, column)) {
                found = node;
                queue.clear();
                for (int i = 0; i < node.getChildCount(); i++) {
                    queue.add(node.getChild(i));
                }
            }
        }
        return found;
    }

    public static Range getRange(ParseTree node) {
        if (node instanceof ParserRuleContext) {
            return getRange((ParserRuleContext) node);
        }
        if (node instanceof TerminalNode) {
            return getRange(((TerminalNode) node).getSymbol());
        }
        if (node instanceof Token) {
            return getRange((Token) node);
        }
        return Range.NO_RANGE;
    }

    public static Range getRange(ParserRuleContext node) {
        int startLine = node.start.getLine();
        int startColumn = node.start.getCharPositionInLine();
        int endLine = node.stop.getLine();
        int endColumn = node.stop.getCharPositionInLine();
        return new Range(startLine, startColumn, endLine, endColumn);
    }

    public static Range getRange(Token node) {
        int startLine = node.getLine();
        int startColumn = node.getCharPositionInLine();
        int endColumn = startColumn + node.getText().length();
        return new Range(startLine, startColumn, startLine, endColumn);
    }

}
