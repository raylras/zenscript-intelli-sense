package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class RangeExpression extends Expression {

    private final Expression from;
    private final Expression to;

    public RangeExpression(Expression from, Expression to) {
        this.from = from;
        this.to = to;
    }

    public Expression getFrom() {
        return from;
    }

    public Expression getTo() {
        return to;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitRangeExpression(this);
        from.accept(visitor);
        to.accept(visitor);
    }

}
