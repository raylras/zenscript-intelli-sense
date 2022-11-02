package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

public class NullLiteralExpressionNode extends ASTNode implements LiteralExpressionNode {

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "null";
    }

}
