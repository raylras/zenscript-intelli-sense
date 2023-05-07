package raylras.zen.util;

public class Range {

    public static final int FIRST_LINE = 0;
    public static final int FIRST_COLUMN = 0;
    public static final int MAX_LINE = Integer.MAX_VALUE;
    public static final int MAX_COLUMN = Integer.MAX_VALUE;
    public static final int NO_LINE = -1;
    public static final int NO_COLUMN = -1;
    public static final Range NO_RANGE = new Range(NO_LINE, NO_COLUMN, NO_LINE, NO_COLUMN);

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

    @Override
    public String toString() {
        return "(range " + startLine + ":" + startColumn + "-" + endLine + ":" + endColumn + ')';
    }

}
