package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;

public class MapEntryExpressionNode extends ASTNode implements ExpressionNode {

    private ExpressionNode key;
    private ExpressionNode value;

    public MapEntryExpressionNode() {
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
    public void addChild(ASTNode node) {
        if (node instanceof ExpressionNode) {
            if (key == null) {
                key = (ExpressionNode) node;
            } else if (value == null) {
                value = (ExpressionNode) node;
            }
        }
    }

    @Override
    public String toString() {
        return key + " : " + value;
    }

}
