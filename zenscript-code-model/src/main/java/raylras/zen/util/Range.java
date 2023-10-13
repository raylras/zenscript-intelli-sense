package raylras.zen.util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public record Range(Position start, Position end) {

    public static final int FIRST_LINE = 0;
    public static final int FIRST_COLUMN = 0;
    public static final int MAX_LINE = Integer.MAX_VALUE;
    public static final int MAX_COLUMN = Integer.MAX_VALUE;
    public static final int NO_LINE = -1;
    public static final int NO_COLUMN = -1;
    public static final Range NO_RANGE = new Range(Position.NO_POSITION, Position.NO_POSITION);

    public static final int ANTLR_FIRST_LINE = 1; // org.antlr.v4.runtime.Token.getLine()
    public static final int ANTLR_FIRST_COLUMN = 0; // org.antlr.v4.runtime.Token.getCharPositionInLine()
    public static final int LSP4J_FIRST_LINE = 0; // org.eclipse.lsp4j.Position.getLine()
    public static final int LSP4J_FIRST_COLUMN = 0; // org.eclipse.lsp4j.Position.getCharacter()

    public static Range of(int startLine, int startColumn, int endLine, int endColumn) {
        return new Range(Position.of(startLine, startColumn), Position.of(endLine, endColumn));
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
        return NO_RANGE;
    }

    public static Range of(ParserRuleContext cst) {
        if (cst == null) {
            return NO_RANGE;
        }

        int startLine;
        int startColumn;
        if (cst.start != null) {
            startLine = cst.start.getLine() - ANTLR_FIRST_LINE;
            startColumn = cst.start.getCharPositionInLine();
        } else {
            startLine = Range.NO_LINE;
            startColumn = Range.NO_COLUMN;
        }

        int endLine;
        int endColumn;
        if (cst.stop != null) {
            endLine = cst.stop.getLine() - ANTLR_FIRST_LINE;
            endColumn = cst.stop.getCharPositionInLine() + cst.stop.getText().length();
        } else {
            endLine = Range.NO_LINE;
            endColumn = Range.NO_COLUMN;
        }
        return Range.of(startLine, startColumn, endLine, endColumn);
    }

    public static Range of(TerminalNode node) {
        if (node == null) {
            return NO_RANGE;
        }
        return of(node.getSymbol());
    }

    public static Range of(Token token) {
        if (token == null) {
            return NO_RANGE;
        }
        int startLine = token.getLine() - ANTLR_FIRST_LINE;
        int startColumn = token.getCharPositionInLine();
        int endColumn = startColumn + token.getText().length();
        return Range.of(startLine, startColumn, startLine, endColumn);
    }

    public static Range of(org.eclipse.lsp4j.Range range) {
        if (range == null) {
            return NO_RANGE;
        }
        int startLine = range.getStart().getLine();
        int startColumn = range.getStart().getCharacter();
        int endLine = range.getEnd().getLine();
        int endColumn = range.getEnd().getCharacter();
        return Range.of(startLine, startColumn, endLine, endColumn);
    }

    public boolean contains(Range that) {
        if (this.start.line() > that.start.line() || this.end.line() < that.end.line()) {
            return false;
        }
        if (this.start.line() == that.start.line() && this.start.column() > that.start.column()) {
            return false;
        }
        if (this.end.line() == that.end.line() && this.end.column() < that.end.column()) {
            return false;
        }
        return true;
    }

    public boolean contains(Position pos) {
        return this.contains(new Range(pos, pos));
    }

    public org.eclipse.lsp4j.Range toLspRange() {
        return new org.eclipse.lsp4j.Range(start.toLspPosition(), end.toLspPosition());
    }

    @Override
    public String toString() {
        return "(" + start.line() + ":" + start.column() + ")-(" + end.line() + ":" + end.column() + ')';
    }

}
