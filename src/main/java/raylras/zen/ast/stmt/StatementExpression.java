package raylras.zen.ast.stmt;

import raylras.zen.ast.expr.Expression;

public class StatementExpression extends Statement {

    private Expression expr;


    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

}
