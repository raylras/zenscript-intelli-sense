package raylras.zen.ast.expr;

import java.util.List;

public class ExpressionCall extends Expression {

    private Expression expr;
    private List<Expression> args;

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    public List<Expression> getArgs() {
        return args;
    }

    public void setArgs(List<Expression> args) {
        this.args = args;
    }

}
