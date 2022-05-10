package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class BooleanLiteralExpression extends Expression {

    private final String literal;

    public BooleanLiteralExpression(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitBooleanLiteralExpression(this);
    }

    @Override
    public String toString() {
        return literal;
    }

}
