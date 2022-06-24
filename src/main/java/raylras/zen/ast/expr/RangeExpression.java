package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;
import java.util.stream.Stream;

public final class RangeExpression extends BaseNode implements Expression {

    @NotNull
    private final Expression from;
    @NotNull
    private final Expression to;

    public RangeExpression(@NotNull Expression from, @NotNull Expression to) {
        this.from = from;
        this.to = to;
    }

    @NotNull
    public Expression getFrom() {
        return from;
    }

    @NotNull
    public Expression getTo() {
        return to;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return List.of(from, to);
    }

    @Override
    public String toString() {
        return from + ".." + to;
    }

}
