package raylras.zen.ast.stmt;

import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.ASTVisitor;
import raylras.zen.ast.expr.Expression;

public class IfStatement extends Statement {

    private final Expression condition;
    private final Statement thenStmt;
    @Nullable
    private final Statement elseStmt;

    public IfStatement(Expression condition, Statement thenStmt, @Nullable Statement elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getThenStmt() {
        return thenStmt;
    }

    @Nullable
    public Statement getElseStmt() {
        return elseStmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitIfStatement(this);
        condition.accept(visitor);
        thenStmt.accept(visitor);
        if (elseStmt != null) {
            elseStmt.accept(visitor);
        }
    }

}
