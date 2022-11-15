package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;

/**
 * arr[1]
 */
public class MemberIndexExpressionNode extends ASTNode implements Expression {

    private Expression left;
    private Expression index;

    public MemberIndexExpressionNode() {
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getIndex() {
        return index;
    }

    public void setIndex(Expression index) {
        this.index = index;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Expression) {
            if (left == null) {
                left = (Expression) node;
            } else if (index == null) {
                index = (Expression) node;
            }
        }
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
