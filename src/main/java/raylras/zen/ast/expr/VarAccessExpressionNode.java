package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

public class VarAccessExpressionNode extends ASTNode implements ExpressionNode {

    private final String name;

    public VarAccessExpressionNode(String name) {
        this.name = name;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return name;
    }

}
