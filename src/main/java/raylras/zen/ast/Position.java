package raylras.zen.ast;

import org.jetbrains.annotations.NotNull;

public final class Position implements Comparable<Position> {

    private final int line;
    private final int column;

    public Position() {
        this(-1, -1);
    }

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }


    @Override
    public String toString() {
        return String.format("<%d:%d>", line, column);
    }

    @Override
    public int compareTo(@NotNull Position other) {
        return this.line == other.line ? this.column - other.column : this.line - other.line;
    }

}
