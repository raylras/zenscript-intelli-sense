package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;

public final class MemberAccess extends BaseNode implements Expression {

    @NotNull
    private final Expression left;
    @NotNull
    private final String right;

    public MemberAccess(@NotNull Expression left, @NotNull String right) {
        this.left = left;
        this.right = right;
    }

    @NotNull
    public Expression getLeft() {
        return left;
    }

    @NotNull
    public String getRight() {
        return right;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        return Collections.singletonList(left);
    }

    @Override
    public String toString() {
        return left + "." + right;
    }

}
