package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Literal;

public class NullLiteralExpressionNode extends ASTNode implements Literal, Expression {

    @Override
    public void addChild(ASTNode node) {
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
