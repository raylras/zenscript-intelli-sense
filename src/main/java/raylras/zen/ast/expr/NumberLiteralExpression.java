package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class NumberLiteralExpression extends Expression {

    private final String literal;

    public NumberLiteralExpression(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitNumberLiteralExpression(this);
    }

    @Override
    public String toString() {
        return literal;
    }

}
