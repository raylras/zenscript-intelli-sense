package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.*;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;

public final class AliasDeclaration extends BaseNode implements Declaration {

    @NotNull
    private final IDNode id;

    public AliasDeclaration(@NotNull IDNode id) {
        this.id = id;
    }

    @NotNull
    public IDNode getId() {
        return id;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return List.of(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
