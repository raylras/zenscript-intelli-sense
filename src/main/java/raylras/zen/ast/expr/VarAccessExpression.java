package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.Symbol;
import raylras.zen.ast.Symbolized;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class VarAccessExpression extends BaseNode implements Expression, Symbolized {

    @NotNull
    private final String name;
    @Nullable
    private Symbol symbol;

    public VarAccessExpression(@NotNull String name) {
        this.name = name;
    }

    @Override
    public Optional<Symbol> getSymbol() {
        return Optional.ofNullable(symbol);
    }

    public void setSymbol(@Nullable Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return name;
    }

}
