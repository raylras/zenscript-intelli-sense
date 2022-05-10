package raylras.zen.ast.expr;

import raylras.zen.ast.ASTVisitor;

public class ThisExpression extends Expression {

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitThisExpression(this);
    }

}
