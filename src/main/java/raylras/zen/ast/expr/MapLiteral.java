package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class MapLiteral extends BaseNode implements Expression {

    @NotNull
    private final List<MapEntryExpression> entries;

    public MapLiteral(@NotNull List<MapEntryExpression> entries) {
        this.entries = entries;
    }

    @NotNull
    public List<MapEntryExpression> getEntries() {
        return entries;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Collections.unmodifiableList(entries);
    }

    @Override
    public String toString() {
        return "{" + entries.stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";
    }

}
