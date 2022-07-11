package raylras.zen.ast.decl;

import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.Symbol;
import raylras.zen.ast.HasSymbol;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;

public final class  TypeDeclaration extends BaseNode implements Declaration, HasSymbol {

    @Nullable
    private final Symbol symbol;

    public TypeDeclaration(@Nullable Symbol symbol) {
        this.symbol = symbol;
    }

    @Nullable
    @Override
    public Symbol getSymbol() {
        return symbol;
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
        return getType() == null ? null : getType().toString();
    }

}
