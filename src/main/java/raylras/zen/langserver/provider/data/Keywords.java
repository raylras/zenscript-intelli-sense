package raylras.zen.langserver.provider.data;

public class Keywords {

    public static final String[] ALL = makeAllKeywords();
    public static final String[] CLASS_BODY = makeClassBodyKeywords();
    public static final String[] LOCAL_STATEMENT = makeLocalStatementKeywords();
    public static final String[] TOPLEVEL_STATEMENT = makeToplevelStatementKeywords();


    public static final String IMPORT = "import";

    public static final String VAR = "var";
    public static final String VAL = "val";
    public static final String GLOBAL = "global";
    public static final String STATIC = "static";
    public static final String FUNCTION = "function";
    public static final String ZEN_CLASS = "zenClass";
    public static final String ZEN_CONSTRUCTOR = "zenConstructor";

    public static final String IF = "if";
    public static final String ELSE = "else";
    public static final String FOR = "for";
    public static final String WHILE = "while";
    public static final String BREAK = "break";
    public static final String CONTINUE = "continue";
    public static final String RETURN = "return";

    public static final String AS = "as";
    public static final String IN = "in";
    public static final String HAS = "has";
    public static final String INSTANCEOF = "instanceof";
    public static final String THIS = "this";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String NULL = "null";

    public static final String ANY = "any";
    public static final String BYTE = "byte";
    public static final String SHORT = "short";
    public static final String INT = "int";
    public static final String LONG = "long";
    public static final String FLOAT = "float";
    public static final String DOUBLE = "double";
    public static final String BOOL = "bool";
    public static final String VOID = "void";
    public static final String STRING = "string";

    public static final String EXPAND = "$expand";

    private static String[] makeAllKeywords() {
        return new String[]{
                IMPORT,
                VAR, VAL, GLOBAL, STATIC, FUNCTION, ZEN_CLASS, ZEN_CONSTRUCTOR,
                IF, ELSE, FOR, WHILE, BREAK, CONTINUE, RETURN,
                AS, IN, HAS, INSTANCEOF, THIS, TRUE, FALSE, NULL,
                ANY, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BOOL, VOID, STRING,
                EXPAND,
        };
    }

    private static String[] makeClassBodyKeywords() {
        return new String[]{
                VAR, VAL, STATIC, FUNCTION, ZEN_CONSTRUCTOR
        };
    }

    private static String[] makeToplevelStatementKeywords() {
        return new String[]{
                IMPORT,
                VAR, VAL, STATIC, GLOBAL, FUNCTION, ZEN_CLASS,
                IF, ELSE, FOR, WHILE,
                EXPAND
        };
    }

    private static String[] makeLocalStatementKeywords() {
        return new String[]{
                VAR, VAL,
                IF, ELSE, FOR, WHILE, BREAK, CONTINUE, RETURN,
                THIS
        };
    }

}
