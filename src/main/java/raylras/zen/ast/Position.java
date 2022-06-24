package raylras.zen.ast;

import org.jetbrains.annotations.NotNull;
import raylras.zen.util.PosUtils;

public record Position(int line, int column) implements Comparable<Position> {

    public Position() {
        this(-1, -1);
    }

    @Override
    public String toString() {
        return String.format("<%d:%d>", line, column);
    }

    @Override
    public int compareTo(@NotNull Position other) {
        return this.line == other.line ? this.column - other.column : this.line - other.line;
    }

    public static Position of(org.eclipse.lsp4j.Position lspPos) {
        return PosUtils.toASTPosition(lspPos);
    }

}
