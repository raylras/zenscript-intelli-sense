package raylras.zen.ast;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.stmt.Statement;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;

public final class BlockNode extends BaseNode implements Statement {

    @NotNull
    private final List<Statement> statements;

    public BlockNode(@NotNull List<Statement> statements) {
        this.statements = statements;
    }

    @NotNull
    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        return Collections.unmodifiableList(statements);
    }

    @Override
    public String toString() {
        return "{...}";
    }

}
