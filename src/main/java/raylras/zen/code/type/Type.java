package raylras.zen.code.type;

public abstract class Type {

    public abstract Kind getKind();

    public enum Kind {
        ANY, FUNCTION, NUMBER, STRING, ARRAY, LIST, MAP, BOOL,
        CLASS, PACKAGE, VOID,
        NONE
    }

}
