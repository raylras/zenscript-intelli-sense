package raylras.zen.ast;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jetbrains.annotations.NotNull;
import raylras.zen.util.PosUtils;

public record Range(int line, int column, int lastLine, int lastColumn) {

    public Range() {
        this(-1, -1, -1, -1);
    }

    public boolean contains(@NotNull Position pos) {
        int line = pos.line();
        int column = pos.column();
        if (this.line <= line && line < this.lastLine) {
            return true;
        } else if (this.line == line && line == this.lastLine) {
            if (this.column <= column && column < this.lastColumn) {
                return true;
            } else return this.column == column && column == this.lastColumn;
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

    @Override
    public String toString() {
        return String.format("<%d:%d-%d:%d>", line, column, lastLine, lastColumn);
    }

    public static Range of(ParserRuleContext ctx) {
        return PosUtils.makeASTRange(ctx);
    }

}
