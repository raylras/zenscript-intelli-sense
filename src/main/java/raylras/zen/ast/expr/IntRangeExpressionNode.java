package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Node;
import raylras.zen.util.CommonUtils;

import java.util.List;

/**
 * 1 .. 2
 * 1 to 2
 */
public class IntRangeExpressionNode extends ASTNode implements Expression {

    private Expression from;
    private Expression to;

    public IntRangeExpressionNode() {
    }

    public Expression getFrom() {
        return from;
    }

    public void setFrom(Expression from) {
        this.from = from;
    }

    public Expression getTo() {
        return to;
    }

    public void setTo(Expression to) {
        this.to = to;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Expression) {
            if (from == null) {
                from = (Expression) node;
            } else if (to == null) {
                to = (Expression) node;
            }
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(from, to);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
