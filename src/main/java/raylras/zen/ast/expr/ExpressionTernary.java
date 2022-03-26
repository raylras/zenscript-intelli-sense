package raylras.zen.ast.expr;

public class ExpressionTernary extends Expression {

    private Expression first;
    private Expression second;
    private Expression third;

    public Expression getFirst() {
        return first;
    }

    public void setFirst(Expression first) {
        this.first = first;
    }

    public Expression getSecond() {
        return second;
    }

    public void setSecond(Expression second) {
        this.second = second;
    }

    public Expression getThird() {
        return third;
    }

    public void setThird(Expression third) {
        this.third = third;
    }

}
