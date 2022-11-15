package raylras.zen.ast;

import java.util.Objects;

public class TextRange {
    public int startLine;
    public int startColumn;
    public int endLine;
    public int endColumn;

    public TextRange(int startLine, int startColumn, int endLine, int endColumn) {
        this.startLine = startLine;
        this.startColumn = startColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextRange textRange = (TextRange) o;
        return startLine == textRange.startLine && startColumn == textRange.startColumn && endLine == textRange.endLine && endColumn == textRange.endColumn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startLine, startColumn, endLine, endColumn);
    }

    @Override
    public String toString() {
        return "Start:" + startLine + ":" + startColumn + ", End:" + endLine + ":" + endColumn;
    }

}
