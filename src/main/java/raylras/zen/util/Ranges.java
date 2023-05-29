package raylras.zen.util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.Position;

public class Ranges {

    public static org.eclipse.lsp4j.Range toLSPRange(Range range) {
        return new org.eclipse.lsp4j.Range(new Position(range.startLine, range.startColumn), new Position(range.endLine, range.endColumn));
    }

    public static boolean isRangeContainsPosition(Range range, int line, int column) {
        if (line < range.startLine || range.endLine < line) {
            return false;
        }
        if (line == range.endLine) {
            return range.startColumn <= column && column <= range.endColumn;
        }
        return true;
    }

    public static Range from(ParseTree node) {
        if (node instanceof ParserRuleContext) {
            return from((ParserRuleContext) node);
        }
        if (node instanceof TerminalNode) {
            return from((TerminalNode) node);
        }
        if (node instanceof Token) {
            return from((Token) node);
        }
        return Range.NO_RANGE;
    }

    public static Range from(ParserRuleContext node) {
        int startLine = node.start.getLine() - Range.ANTLR_FIRST_LINE;
        int startColumn = node.start.getCharPositionInLine();
        int endLine = node.stop.getLine() - Range.ANTLR_FIRST_LINE;
        int endColumn = node.stop.getCharPositionInLine() + node.stop.getText().length();
        return new Range(startLine, startColumn, endLine, endColumn);
    }

    public static Range from(TerminalNode node) {
        return from(node.getSymbol());
    }

    public static Range from(Token node) {
        int startLine = node.getLine() - Range.ANTLR_FIRST_LINE;
        int startColumn = node.getCharPositionInLine();
        int endColumn = startColumn + node.getText().length();
        return new Range(startLine, startColumn, startLine, endColumn);
    }

    public static Range from(org.eclipse.lsp4j.Range range) {
        int startLine = range.getStart().getLine();
        int startColumn = range.getStart().getCharacter();
        int endLine = range.getEnd().getLine();
        int endColumn = range.getEnd().getCharacter();
        return new Range(startLine, startColumn, endLine, endColumn);
    }

    public static Range from(org.eclipse.lsp4j.Position position) {
        int startLine = position.getLine();
        int startColumn = position.getCharacter();
        return new Range(startLine, startColumn, startLine, startColumn);
    }

}
