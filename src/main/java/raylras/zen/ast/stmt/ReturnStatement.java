package raylras.zen.ast.stmt;

import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class ReturnStatement extends BaseNode implements Statement {

    @Nullable
    private final Expression expr;

    public ReturnStatement(@Nullable Expression expr) {
        this.expr = expr;
    }

    public Optional<Expression> getExpr() {
        return Optional.ofNullable(expr);
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        if (expr == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(expr);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("return");
        if (expr != null) {
            builder.append(" ").append(expr);
        }
        builder.append(";");
        return builder.toString();
    }

}
