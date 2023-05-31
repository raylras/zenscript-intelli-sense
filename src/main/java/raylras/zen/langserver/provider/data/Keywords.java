package raylras.zen.langserver.provider.data;

public class Keywords {

    public static final String[] ALL = makeAllKeywords();
    public static final String[] CLASS_BODY = makeClassBodyKeywords();
    public static final String[] LOCAL_STATEMENT = makeLocalStatementKeywords();
    public static final String[] TOPLEVEL_STATEMENT = makeToplevelStatementKeywords();

    private static String[] makeAllKeywords() {
        return new String[]{
                "var", "val", "global", "static", "import", "function",
                "as", "in", "has", "instanceof", "this", "super",
                "any", "byte", "short", "int", "long", "float", "double",
                "bool", "void", "string", "if", "else", "for", "do", "while",
                "break", "continue", "return", "frigginClass", "frigginConstructor",
                "zenClass", "zenConstructor", "$expand",
                "true", "false", "null"
        };
    }

    private static String[] makeClassBodyKeywords() {
        return new String[]{
                "var", "val", "static", "function",
                "zenConstructor"
        };
    }

    private static String[] makeToplevelStatementKeywords() {
        return new String[]{
                "import",
                "var", "val", "static", "global", "function",
                "if", "else", "for", "while",
                "zenClass", "$expand"
        };
    }

    private static String[] makeLocalStatementKeywords() {
        return new String[]{
                "var", "val",
                "if", "else", "for", "while",
                "this",
                "break", "continue", "return",
        };
    }

}
