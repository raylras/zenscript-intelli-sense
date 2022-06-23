package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ArgumentsExpression extends BaseNode implements Expression {

    @NotNull
    private final Expression left;
    @NotNull
    private final List<Expression> arguments;

    public ArgumentsExpression(@NotNull Expression left, @NotNull List<Expression> arguments) {
        this.left = left;
        this.arguments = arguments;
    }

    @NotNull
    public Expression getLeft() {
        return left;
    }

    @NotNull
    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.concat(
                Stream.of(left),
                arguments.stream()
        ).toList();
    }

    @Override
    public String toString() {
        return left + "(" + arguments.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
    }

}
