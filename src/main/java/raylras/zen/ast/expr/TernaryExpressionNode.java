package raylras.zen.ast.expr;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Node;
import raylras.zen.util.CommonUtils;

import java.util.List;

/**
 * expr ? thenExpr : elseExpr
 */
public class TernaryExpressionNode extends ASTNode implements Expression {

    private Expression expr;
    private Expression thenExpr;
    private Expression elseExpr;

    public TernaryExpressionNode() {
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    public Expression getThenExpr() {
        return thenExpr;
    }

    public void setThenExpr(Expression thenExpr) {
        this.thenExpr = thenExpr;
    }

    public Expression getElseExpr() {
        return elseExpr;
    }

    public void setElseExpr(Expression elseExpr) {
        this.elseExpr = elseExpr;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Expression) {
            if (expr == null) {
                expr = (Expression) node;
            } else if (thenExpr == null) {
                thenExpr = (Expression) node;
            } else if (elseExpr == null) {
                elseExpr = (Expression) node;
            }
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(expr, thenExpr, elseExpr);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
