package raylras.zen.ast.stmt;

import raylras.zen.ast.ASTVisitor;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.expr.Expression;

public class WhileStatement extends Statement {

    private final Expression condition;
    private final BlockNode blockNode;

    public WhileStatement(Expression condition, BlockNode blockNode) {
        this.condition = condition;
        this.blockNode = blockNode;
    }

    public Expression getCondition() {
        return condition;
    }

    public BlockNode getBlockNode() {
        return blockNode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitWhileStatement(this);
        condition.accept(visitor);
        blockNode.accept(visitor);
    }

}
