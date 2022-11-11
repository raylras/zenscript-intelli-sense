package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.TypeNameNode;

public class TypeAssertionExpressionNode extends ASTNode implements ExpressionNode {

    private ExpressionNode expr;
    private TypeNameNode typeName;

    public TypeAssertionExpressionNode() {
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    public TypeNameNode getTypeName() {
        return typeName;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof ExpressionNode) {
            if (expr == null) {
                expr = (ExpressionNode) node;
            }
        } else if (node.getClass() == TypeNameNode.class) {
            if (typeName == null) {
                typeName = (TypeNameNode) node;
            }
        }
    }

    @Override
    public String toString() {
        return expr + " as " + typeName;
    }

}
