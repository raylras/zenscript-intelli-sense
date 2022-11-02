package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

public class MapEntryExpressionNode extends ASTNode implements ExpressionNode {

    private final ExpressionNode key;
    private final ExpressionNode value;

    public MapEntryExpressionNode(ExpressionNode key, ExpressionNode value) {
        this.key = key;
        this.value = value;
    }

    public ExpressionNode getKey() {
        return key;
    }

    public ExpressionNode getValue() {
        return value;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return key + " : " + value;
    }

}
