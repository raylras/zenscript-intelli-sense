package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Node;
import raylras.zen.util.CommonUtils;

import java.util.List;

public class UnaryExpressionNode extends ASTNode implements Expression {

    private Expression expr;
    private Operator operator;

    public UnaryExpressionNode(Operator operator) {
        this.operator = operator;
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
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
            expr = (Expression) node;
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(expr);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
