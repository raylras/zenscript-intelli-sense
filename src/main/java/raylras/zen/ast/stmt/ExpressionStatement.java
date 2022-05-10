package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTVisitor;
import raylras.zen.ast.expr.Expression;

public class ExpressionStatement extends Statement {

    private final Expression expr;

    public ExpressionStatement(Expression expr) {
        this.expr = expr;
    }

    public Expression getExpr() {
        return expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitExpressionStatement(this);
        expr.accept(visitor);
    }

}
