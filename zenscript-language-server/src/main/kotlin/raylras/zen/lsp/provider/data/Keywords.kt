package raylras.zen.lsp.provider.data

object Keywords {
    val ALL: Array<String> = makeAllKeywords()
    val CLASS_BODY: Array<String> = makeClassBodyKeywords()
    val STATEMENT: Array<String> = makeStatementKeywords()
    val LOCAL_STATEMENT: Array<String> = makeLocalStatementKeywords()
    val TOPLEVEL_STATEMENT: Array<String> = makeToplevelStatementKeywords()


    const val IMPORT: String = "import"

    const val VAR: String = "var"
    const val VAL: String = "val"
    const val GLOBAL: String = "global"
    const val STATIC: String = "static"
    const val FUNCTION: String = "function"
    const val ZEN_CLASS: String = "zenClass"
    const val ZEN_CONSTRUCTOR: String = "zenConstructor"

    const val IF: String = "if"
    const val ELSE: String = "else"
    const val FOR: String = "for"
    const val WHILE: String = "while"
    const val BREAK: String = "break"
    const val CONTINUE: String = "continue"
    const val RETURN: String = "return"

    const val AS: String = "as"
    const val IN: String = "in"
    const val HAS: String = "has"
    const val INSTANCEOF: String = "instanceof"
    const val THIS: String = "this"
    const val TRUE: String = "true"
    const val FALSE: String = "false"
    const val NULL: String = "null"

    const val ANY: String = "any"
    const val BYTE: String = "byte"
    const val SHORT: String = "short"
    const val INT: String = "int"
    const val LONG: String = "long"
    const val FLOAT: String = "float"
    const val DOUBLE: String = "double"
    const val BOOL: String = "bool"
    const val VOID: String = "void"
    const val STRING: String = "string"

    const val EXPAND: String = "\$expand"

    private fun makeAllKeywords(): Array<String> {
        return arrayOf(
            IMPORT,
            VAR, VAL, GLOBAL, STATIC, FUNCTION, ZEN_CLASS, ZEN_CONSTRUCTOR,
            IF, ELSE, FOR, WHILE, BREAK, CONTINUE, RETURN,
            AS, IN, HAS, INSTANCEOF, THIS, TRUE, FALSE, NULL,
            ANY, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BOOL, VOID, STRING,
            EXPAND,
        )
    }

    private fun makeClassBodyKeywords(): Array<String> {
        return arrayOf(
            VAR, VAL, STATIC, FUNCTION, ZEN_CONSTRUCTOR
        )
    }

    private fun makeStatementKeywords(): Array<String> {
        return arrayOf(
            IMPORT,
            VAR, VAL, STATIC, GLOBAL, FUNCTION, ZEN_CLASS, ZEN_CONSTRUCTOR,
            IF, ELSE, FOR, WHILE, BREAK, CONTINUE, RETURN,
            EXPAND
        )
    }

    private fun makeToplevelStatementKeywords(): Array<String> {
        return arrayOf(
            IMPORT,
            VAR, VAL, STATIC, GLOBAL, FUNCTION, ZEN_CLASS,
            IF, ELSE, FOR, WHILE,
            EXPAND
        )
    }

    private fun makeLocalStatementKeywords(): Array<String> {
        return arrayOf(
            VAR, VAL,
            IF, ELSE, FOR, WHILE, BREAK, CONTINUE, RETURN,
            THIS
        )
    }
}
