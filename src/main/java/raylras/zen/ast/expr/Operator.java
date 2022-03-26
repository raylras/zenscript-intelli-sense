package raylras.zen.ast.expr;

public enum Operator {

    NOT, NEG,
    ADD, SUB, MUL, DIV, MOD, CAT, INSTANCEOF, CONTAINS,
    LESS, GREATER, EQUALS, NOT_EQUALS, LESS_EQUALS, GREATER_EQUALS,
    OR, AND, XOR, OR_OR, AND_AND,
    ASSIGN, ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, AND_ASSIGN, OR_ASSIGN, XOR_ASSIGN, MOD_ASSIGN, CAT_ASSIGN,
    ;

    public static Operator getUnary(String operator) {
        switch (operator) {
            case "!": return NOT;
            case "-": return NEG;
            default: return null;
        }
    }

    public static Operator getBinary(String operator) {
        switch (operator) {
            case "+": return ADD;
            case "-": return SUB;
            case "*": return MUL;
            case "/": return DIV;
            case "%": return MOD;
            case "~": return CAT;
            case "<": return LESS;
            case ">": return GREATER;
            case "==": return EQUALS;
            case "!=": return NOT_EQUALS;
            case "<=": return LESS_EQUALS;
            case ">=": return GREATER_EQUALS;
            case "|": return OR;
            case "&": return AND;
            case "^": return XOR;
            case "||": return OR_OR;
            case "&&": return AND_AND;
            case "instanceof": return INSTANCEOF;
            case "in": case "has": return CONTAINS;
            default: return null;
        }
    }

    public static Operator getAssign(String operator) {
        switch (operator) {
            case "=": return ASSIGN;
            case "+=": return ADD_ASSIGN;
            case "-=": return SUB_ASSIGN;
            case "*=": return MUL_ASSIGN;
            case "/=": return DIV_ASSIGN;
            case "%/": return MOD_ASSIGN;
            case "~=": return CAT_ASSIGN;
            case "&=": return AND_ASSIGN;
            case "|=": return OR_ASSIGN;
            case "^=": return XOR_ASSIGN;
            default: return null;
        }
    }

}
