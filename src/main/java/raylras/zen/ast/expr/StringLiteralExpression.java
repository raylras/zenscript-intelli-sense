package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class StringLiteralExpression extends Expression {

    private final String literal;

    public StringLiteralExpression(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitStringLiteralExpression(this);
    }

    @Override
    public String toString() {
        return literal;
    }

}
