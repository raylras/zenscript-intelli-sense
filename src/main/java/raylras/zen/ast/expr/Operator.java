package raylras.zen.ast.expr;

public enum Operator {
    ADD,
    SUB,
    MUL
    ;

    public static Operator get(String operator) {
        switch (operator) {
            case "+": return ADD;
            case "-": return SUB;
            case "*": return MUL;
            default: return null;
        }
    }

}
