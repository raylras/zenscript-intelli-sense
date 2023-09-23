package raylras.zen.util;

/**
 * @see Range
 */
public record Position(int line, int column) {

    public static final Position NO_POSITION = new Position(Range.NO_LINE, Range.NO_COLUMN);

    public static Position of(int line, int column) {
        return new Position(line, column);
    }

    public static Position of(org.eclipse.lsp4j.Position pos) {
        return new Position(pos.getLine(), pos.getCharacter());
    }

    public org.eclipse.lsp4j.Position toLspPosition() {
        return new org.eclipse.lsp4j.Position(line, column);
    }

    @Override
    public String toString() {
        return "(" + line + ":" + column + ')';
    }


}
