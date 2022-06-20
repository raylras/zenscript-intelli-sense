package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MemberIndexExpression extends BaseNode implements Expression {

    @NotNull
    private final Expression left;
    @NotNull
    private final Expression index;

    public MemberIndexExpression(@NotNull Expression left, @NotNull Expression index) {
        this.left = left;
        this.index = index;
    }

    @NotNull
    public Expression getLeft() {
        return left;
    }

    @NotNull
    public Expression getIndex() {
        return index;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        ArrayList<Node> children = new ArrayList<>(2);
        children.add(left);
        children.add(index);
        return Collections.unmodifiableList(children);
    }

    @Override
    public String toString() {
        return left + "[" + index + "]";
    }

}
