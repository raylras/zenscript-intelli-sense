package raylras.zen.ast;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jetbrains.annotations.NotNull;
import raylras.zen.util.PosUtils;

public record Range(int line, int column, int lastLine, int lastColumn) implements Comparable<Range> {

    public Range() {
        this(-1, -1, -1, -1);
    }

    public boolean contains(@NotNull Position pos) {
        if (this.line < pos.line() && pos.line() < this.lastLine) {
            return true;
        } else if (this.line == pos.line() && this.lastLine == pos.line()) {
            return this.column <= pos.column() && pos.column() <= this.lastColumn;
        } else if (this.line == pos.line()) {
            return this.column <= pos.column();
        } else if (this.lastLine == pos.line()) {
            return pos.column() <= this.lastColumn;
        } else {
            return false;
        }
    }

    public boolean contains(@NotNull Range range) {
        return this.line <= range.line()
                && this.lastLine >= range.lastLine()
                && this.column <= range.column()
                && this.lastColumn >= range.lastColumn();
    }

    public org.eclipse.lsp4j.Range toLSPRange() {
        return PosUtils.toLSPRange(this);
    }

    public static Range of(ParserRuleContext ctx) {
        return PosUtils.makeASTRange(ctx);
    }

    @Override
    public String toString() {
        return String.format("<%d:%d-%d:%d>", line, column, lastLine, lastColumn);
    }

    @Override
    public int compareTo(@NotNull Range other) {
        if (this.line < other.line) {
            return -1;
        } else if (this.line > other.line) {
            return 1;
        } else {
            return Integer.compare(this.column, other.column);
        }
    }

}
