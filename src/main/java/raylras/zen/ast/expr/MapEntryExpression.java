package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class MapEntryExpression extends Expression {

    private final Expression key;
    private final Expression value;

    public MapEntryExpression(Expression key, Expression value) {
        this.key = key;
        this.value = value;
    }

    public Expression getKey() {
        return key;
    }

    public Expression getValue() {
        return value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitMapEntryExpression(this);
        key.accept(visitor);
        value.accept(visitor);
    }

}
