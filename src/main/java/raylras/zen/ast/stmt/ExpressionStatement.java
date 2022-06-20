package raylras.zen.ast.stmt;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;

public final class ExpressionStatement extends BaseNode implements Statement {

    @NotNull
    private final Expression expr;

    public ExpressionStatement(@NotNull Expression expr) {
        this.expr = expr;
    }

    @NotNull
    public Expression getExpr() {
        return expr;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        return Collections.singletonList(expr);
    }

    @Override
    public String toString() {
        return expr + ";";
    }

}
