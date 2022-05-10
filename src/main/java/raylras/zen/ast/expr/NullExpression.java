package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class NullExpression extends Expression {

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitNullExpression(this);
    }

}
