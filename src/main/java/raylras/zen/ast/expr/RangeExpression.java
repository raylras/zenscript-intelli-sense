package raylras.zen.ast.expr;

import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;
import java.util.stream.Stream;

public final class RangeExpression extends BaseNode implements Expression {

    private final Expression from;
    private final Expression to;

    public RangeExpression(Expression from, Expression to) {
        this.from = from;
        this.to = to;
    }

    public Expression getFrom() {
        return from;
    }

    public Expression getTo() {
        return to;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.of(from, to).toList();
    }

    @Override
    public String toString() {
        return from + ".." + to;
    }

}
