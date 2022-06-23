package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;

public final class UnaryExpression extends BaseNode implements Expression {

    @NotNull
    private final Expression expr;
    @NotNull
    private final Operator.Unary operator;

    public UnaryExpression(@NotNull Expression expr, @NotNull Operator.Unary operator) {
        this.expr = expr;
        this.operator = operator;
    }

    @NotNull
    public Expression getExpr() {
        return expr;
    }

    @NotNull
    public Operator.Unary getOperator() {
        return operator;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Collections.singletonList(expr);
    }

    @Override
    public String toString() {
        return operator.toString() + expr;
    }

}
