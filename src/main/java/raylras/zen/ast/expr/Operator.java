package raylras.zen.ast.expr;

public final class Operator {

    public enum Unary {
        NOT, NEG, PLUS
    }

    public enum Binary {
        ADD, SUB, MUL, DIV, MOD, CAT, INSTANCEOF, CONTAINS,
        LESS, GREATER, EQUALS, NOT_EQUALS, LESS_EQUALS, GREATER_EQUALS,
        OR, AND, XOR, OR_OR, AND_AND
    }

    public enum Assignment {
        ASSIGN, ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, DIV_ASSIGN,
        AND_ASSIGN, OR_ASSIGN, XOR_ASSIGN, MOD_ASSIGN, CAT_ASSIGN
    }

    public static Operator.Unary getUnary(String operator) {
        switch (operator) {
            case "!": return Unary.NOT;
            case "-": return Unary.NEG;
            case "+": return Unary.PLUS;
            default: return null;
        }
    }

    public static Operator.Binary getBinary(String operator) {
        switch (operator) {
            case "+": return Binary.ADD;
            case "-": return Binary.SUB;
            case "*": return Binary.MUL;
            case "/": return Binary.DIV;
            case "%": return Binary.MOD;
            case "~": return Binary.CAT;
            case "<": return Binary.LESS;
            case ">": return Binary.GREATER;
            case "==": return Binary.EQUALS;
            case "!=": return Binary.NOT_EQUALS;
            case "<=": return Binary.LESS_EQUALS;
            case ">=": return Binary.GREATER_EQUALS;
            case "|": return Binary.OR;
            case "&": return Binary.AND;
            case "^": return Binary.XOR;
            case "||": return Binary.OR_OR;
            case "&&": return Binary.AND_AND;
            case "instanceof": return Binary.INSTANCEOF;
            case "in": case "has": return Binary.CONTAINS;
            default: return null;
        }
    }

    public static Operator.Assignment getAssignment(String operator) {
        switch (operator) {
            case "=": return  Assignment.ASSIGN;
            case "+=": return Assignment.ADD_ASSIGN;
            case "-=": return Assignment.SUB_ASSIGN;
            case "*=": return Assignment.MUL_ASSIGN;
            case "/=": return Assignment.DIV_ASSIGN;
            case "%/": return Assignment.MOD_ASSIGN;
            case "~=": return Assignment.CAT_ASSIGN;
            case "&=": return Assignment.AND_ASSIGN;
            case "|=": return Assignment.OR_ASSIGN;
            case "^=": return Assignment.XOR_ASSIGN;
            default: return null;
        }
    }

}
