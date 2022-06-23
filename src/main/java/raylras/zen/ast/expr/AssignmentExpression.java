package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;
import java.util.stream.Stream;

public final class AssignmentExpression extends BaseNode implements Expression {

    @NotNull
    private final Expression left;
    @NotNull
    private final Expression right;
    private final Operator.Assignment operator;

    public AssignmentExpression(@NotNull Expression left, @NotNull Expression right, Operator.Assignment operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @NotNull
    public Expression getLeft() {
        return left;
    }

    @NotNull
    public Expression getRight() {
        return right;
    }

    public Operator.Assignment getOperator() {
        return operator;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.of(left, right).toList();
    }

    @Override
    public String toString() {
        return left + operator.toString() + right;
    }

}
