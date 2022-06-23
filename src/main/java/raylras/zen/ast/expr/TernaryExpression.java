package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;
import java.util.stream.Stream;

public final class TernaryExpression extends BaseNode implements Expression {

    @NotNull
    private final Expression condition;
    @NotNull
    private final Expression thenExpr;
    @NotNull
    private final Expression elseExpr;

    public TernaryExpression(@NotNull Expression condition, @NotNull Expression thenExpr, @NotNull Expression elseExpr) {
        this.condition = condition;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }

    @NotNull
    public Expression getCondition() {
        return condition;
    }

    @NotNull
    public Expression getThenExpr() {
        return thenExpr;
    }

    @NotNull
    public Expression getElseExpr() {
        return elseExpr;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.of(condition, thenExpr, elseExpr).toList();
    }

    @Override
    public String toString() {
        return condition + " ? " + thenExpr + " : " + elseExpr;
    }

}
