package raylras.zen.ast.expr;

public class ExpressionUnary extends Expression {

    private Expression expr;
    private Operator operator;

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

}
