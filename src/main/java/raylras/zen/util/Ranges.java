package raylras.zen.util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.Position;

public class Ranges {

    public static boolean isRangeContainsLineAndColumn(Range range, int line, int column) {
        return range.contains(new Range(line, column, line, column));
    }

    public static boolean contains(ParseTree a, ParseTree b) {
        return of(a).contains(of(b));
    }

    public static boolean contains(Token token, ParseTree cst) {
        return of(token).contains(of(cst));
    }

    public static Range of(ParseTree cst) {
        if (cst instanceof ParserRuleContext) {
            return of((ParserRuleContext) cst);
        }
        if (cst instanceof TerminalNode) {
            return of((TerminalNode) cst);
        }
        if (cst instanceof Token) {
            return of((Token) cst);
        }
        return Range.NO_RANGE;
    }

    public static Range of(ParserRuleContext cst) {
        if (cst == null) {
            return Range.NO_RANGE;
        }
        int startLine = cst.start.getLine() - Range.ANTLR_FIRST_LINE;
        int startColumn = cst.start.getCharPositionInLine();
        int endLine = cst.stop.getLine() - Range.ANTLR_FIRST_LINE;
        int endColumn = cst.stop.getCharPositionInLine() + cst.stop.getText().length();
        return new Range(startLine, startColumn, endLine, endColumn);
    }

    public static Range of(TerminalNode node) {
        if (node == null) {
            return Range.NO_RANGE;
        }
        return of(node.getSymbol());
    }

    public static Range of(Token token) {
        if (token == null) {
            return Range.NO_RANGE;
        }
        int startLine = token.getLine() - Range.ANTLR_FIRST_LINE;
        int startColumn = token.getCharPositionInLine();
        int endColumn = startColumn + token.getText().length();
        return new Range(startLine, startColumn, startLine, endColumn);
    }

    public static Range of(org.eclipse.lsp4j.Range range) {
        int startLine = range.getStart().getLine();
        int startColumn = range.getStart().getCharacter();
        int endLine = range.getEnd().getLine();
        int endColumn = range.getEnd().getCharacter();
        return new Range(startLine, startColumn, endLine, endColumn);
    }

    public static Range of(org.eclipse.lsp4j.Position position) {
        int startLine = position.getLine();
        int startColumn = position.getCharacter();
        return new Range(startLine, startColumn, startLine, startColumn);
    }

    public static org.eclipse.lsp4j.Range toLSPRange(ParseTree cst) {
        return toLSPRange(of(cst));
    }

    public static org.eclipse.lsp4j.Range toLSPRange(Range range) {
        return new org.eclipse.lsp4j.Range(new Position(range.startLine, range.startColumn), new Position(range.endLine, range.endColumn));
    }

}
