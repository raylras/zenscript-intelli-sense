package raylras.zen.ast.stmt;

import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.ASTVisitor;
import raylras.zen.ast.VariableNode;
import raylras.zen.ast.expr.Expression;

public class VarStatement extends Statement {

    private final VariableNode variableNode;
    @Nullable
    private final Expression expr;

    public VarStatement(VariableNode variableNode, @Nullable Expression expr) {
        this.variableNode = variableNode;
        this.expr = expr;
    }

    public VariableNode getVariableNode() {
        return variableNode;
    }

    @Nullable
    public Expression getExpr() {
        return expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitVarStatement(this);
        variableNode.accept(visitor);
        if (expr != null) {
            expr.accept(visitor);
        }
    }

}
