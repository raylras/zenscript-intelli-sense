package raylras.zen.ast.expr;

public class ExpressionParens extends Expression {

    private Expression expr;

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

}
