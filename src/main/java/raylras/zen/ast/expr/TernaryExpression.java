package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public List<Node> getChildren() {
        ArrayList<Node> children = new ArrayList<>(3);
        children.add(condition);
        children.add(thenExpr);
        children.add(elseExpr);
        return Collections.unmodifiableList(children);
    }

    @Override
    public String toString() {
        return condition + " ? " + thenExpr + " : " + elseExpr;
    }

}
