package raylras.zen.ast.stmt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class IfElseStatement extends BaseNode implements Statement {

    @NotNull
    private final Expression condition;
    @NotNull
    private final Statement thenStmt;
    @Nullable
    private final Statement elseStmt;

    public IfElseStatement(@NotNull Expression condition, @NotNull Statement thenStmt, @Nullable Statement elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @NotNull
    public Expression getCondition() {
        return condition;
    }

    @NotNull
    public Statement getThenStmt() {
        return thenStmt;
    }

    public Optional<Statement> getElseStmt() {
        return Optional.ofNullable(elseStmt);
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        ArrayList<Node> children = new ArrayList<>(2 + (elseStmt == null ? 0 : 1));
        children.add(condition);
        children.add(thenStmt);
        if (elseStmt != null) {
            children.add(elseStmt);
        }
        return Collections.unmodifiableList(children);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("if ").append(condition).append(" {...}");
        if (elseStmt != null) {
            builder.append(" {...}");
        }
        return builder.toString();
    }

}
