package raylras.zen.util;

public class Range {

    public static final int FIRST_LINE = 0;
    public static final int FIRST_COLUMN = 0;
    public static final int MAX_LINE = Integer.MAX_VALUE;
    public static final int MAX_COLUMN = Integer.MAX_VALUE;
    public static final int NO_LINE = -1;
    public static final int NO_COLUMN = -1;
    public static final Range NO_RANGE = new Range(NO_LINE, NO_COLUMN, NO_LINE, NO_COLUMN);

    public static final int ANTLR_FIRST_LINE = 1; // org.antlr.v4.runtime.Token.getLine()
    public static final int ANTLR_FIRST_COLUMN = 0; // org.antlr.v4.runtime.Token.getCharPositionInLine()
    public static final int LSP4J_FIRST_LINE = 0; // org.eclipse.lsp4j.Position.getLine()
    public static final int LSP4J_FIRST_COLUMN = 0; // org.eclipse.lsp4j.Position.getCharacter()

    public final int startLine;
    public final int startColumn;
    public final int endLine;
    public final int endColumn;

    public Range(int startLine, int startColumn, int endLine, int endColumn) {
        this.startLine = startLine;
        this.startColumn = startColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

    public boolean contains(Range that) {
        if (this.startLine > that.startLine || this.endLine < that.endLine) {
            return false;
        }
        if (this.startLine == that.startLine && this.startColumn > that.startColumn) {
            return false;
        }
        if (this.endLine == that.endLine && this.endColumn < that.endColumn) {
            return false;
        }
        return true;
    }

    public org.eclipse.lsp4j.Range toLspRange() {
        return Ranges.toLSPRange(this);
    }

    @Override
    public String toString() {
        return "(" + startLine + ":" + startColumn + ")-(" + endLine + ":" + endColumn + ')';
    }

}
