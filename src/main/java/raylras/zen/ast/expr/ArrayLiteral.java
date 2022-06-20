package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ArrayLiteral extends BaseNode implements Expression {

    @NotNull
    private final List<Expression> elements;

    public ArrayLiteral(@NotNull List<Expression> elements) {
        this.elements = elements;
    }

    @NotNull
    public List<Expression> getElements() {
        return elements;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        return Collections.unmodifiableList(elements);
    }

    @Override
    public String toString() {
        return "[" + elements.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
    }

}
