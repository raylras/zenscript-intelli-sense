package raylras.zen.ast.decl;

import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.Symbol;
import raylras.zen.ast.Symbolized;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class TypeDeclaration extends BaseNode implements Declaration, Symbolized {

    @Nullable
    private final Symbol symbol;

    public TypeDeclaration(@Nullable Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public Optional<Symbol> getSymbol() {
        return Optional.ofNullable(symbol);
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return getType() == null ? null : getType().toString();
    }

}
