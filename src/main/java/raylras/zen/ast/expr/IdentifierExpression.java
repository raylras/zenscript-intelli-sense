package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class IdentifierExpression extends Expression {

    private final String name;

    public IdentifierExpression(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitIdentifierExpression(this);
    }

}
