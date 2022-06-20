package raylras.zen.ast;

import org.jetbrains.annotations.NotNull;
import raylras.zen.util.PosUtils;

public final class Range {

    private final int line;
    private final int column;
    private final int lastLine;
    private final int lastColumn;

    public Range() {
        this(-1, -1, -1, -1);
    }

    public Range(int line, int column, int lastLine, int lastColumn) {
        this.line = line;
        this.column = column;
        this.lastLine = lastLine;
        this.lastColumn = lastColumn;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getLastLine() {
        return lastLine;
    }

    public int getLastColumn() {
        return lastColumn;
    }

    public boolean contains(@NotNull Position pos) {
        return this.line <= pos.getLine()
                && this.lastLine >= pos.getLine()
                && this.column <= pos.getColumn()
                && this.lastColumn >= pos.getColumn();
    }

    public boolean contains(@NotNull Range other) {
        return this.line <= other.getLine()
                && this.lastLine >= other.getLastLine()
                && this.column <= other.getColumn()
                && this.lastColumn >= other.getLastColumn();
    }

    public org.eclipse.lsp4j.Range toLSPRange() {
        return PosUtils.toLSPRange(this);
    }

    @Override
    public String toString() {
        return String.format("<%d:%d-%d:%d>", line, column, lastLine, lastColumn);
    }

}
