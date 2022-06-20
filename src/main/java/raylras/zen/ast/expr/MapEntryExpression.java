package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MapEntryExpression extends BaseNode implements Expression {

    @NotNull
    private final Expression key;
    @NotNull
    private final Expression value;

    public MapEntryExpression(@NotNull Expression key, @NotNull Expression value) {
        this.key = key;
        this.value = value;
    }

    @NotNull
    public Expression getKey() {
        return key;
    }

    @NotNull
    public Expression getValue() {
        return value;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        ArrayList<Node> children = new ArrayList<>(2);
        children.add(key);
        children.add(value);
        return Collections.unmodifiableList(children);
    }

    @Override
    public String toString() {
        return key + " : " + value;
    }

}
