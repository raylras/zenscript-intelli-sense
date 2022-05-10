package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class BracketHandlerExpression extends Expression {

    private final String literal;

    public BracketHandlerExpression(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitBracketHandlerExpression(this);
    }

    @Override
    public String toString() {
        return literal;
    }

}
