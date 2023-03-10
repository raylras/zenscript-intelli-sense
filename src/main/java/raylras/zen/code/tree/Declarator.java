package raylras.zen.code.tree;

public enum Declarator {

    VAR("var"),
    VAL("val"),
    STATIC("static"),
    GLOBAL("global"),
    NONE(""),
    INVALID("INVALID");

    public final String literal;

    Declarator(String literal) {
        this.literal = literal;
    }

    @Override
    public String toString() {
        return literal;
    }

}
