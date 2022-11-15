package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Statement;
import raylras.zen.ast.type.TopLevel;

public class ExpressionStatementNode extends ASTNode implements Statement, TopLevel {

    private Expression expr;

    public ExpressionStatementNode() {
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Expression) {
            expr = (Expression) node;
        }
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
