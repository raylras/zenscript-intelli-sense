package raylras.zen.ast.expr;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.Symbol;
import raylras.zen.ast.HasSymbol;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;

public final class VarAccessExpression extends BaseNode implements Expression, HasSymbol {

    @NotNull
    private final String name;
    @Nullable
    private Symbol symbol;

    public VarAccessExpression(@NotNull String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(@Nullable Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return List.of();
    }

    @Override
    public String toString() {
        return name;
    }

}
