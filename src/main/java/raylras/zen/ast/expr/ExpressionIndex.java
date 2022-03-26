package raylras.zen.ast.expr;

public class ExpressionIndex extends Expression {

    private Expression expr;
    private Expression index;

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    public Expression getIndex() {
        return index;
    }

    public void setIndex(Expression index) {
        this.index = index;
    }

}
