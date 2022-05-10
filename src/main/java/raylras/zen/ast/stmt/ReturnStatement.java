package raylras.zen.ast.stmt;

import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.ASTVisitor;
import raylras.zen.ast.expr.Expression;

public class ReturnStatement extends Statement {

    @Nullable
    private final Expression expr;

    public ReturnStatement(@Nullable Expression expr) {
        this.expr = expr;
    }

    @Nullable
    public Expression getExpr() {
        return expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitReturnStatement(this);
        if (expr != null) {
            expr.accept(visitor);
        }
    }

}
