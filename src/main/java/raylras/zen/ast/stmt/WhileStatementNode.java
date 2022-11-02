package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.expr.ExpressionNode;
import raylras.zen.ast.ASTNodeVisitor;

/**
 * while (expr) { stmt; }
 */
public class WhileStatementNode extends ASTNode implements StatementNode {

    private final ExpressionNode expr;
    private final BlockNode block;

    public WhileStatementNode(ExpressionNode expr, BlockNode block) {
        this.expr = expr;
        this.block = block;
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    public BlockNode getBlock() {
        return block;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "while " + expr + " {...}";
    }

}
