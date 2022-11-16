package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Node;
import raylras.zen.util.CommonUtils;

import java.util.List;

/**
 * a + b
 * a = b
 * a += b
 */
public class BinaryExpressionNode extends ASTNode implements Expression {

    private Expression left;
    private Expression right;
    private Operator operator;

    public BinaryExpressionNode(Operator operator) {
        this.operator = operator;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Expression) {
            if (left == null) {
                left = (Expression) node;
            } else if (right == null) {
                right = (Expression) node;
            }
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(left, right);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
