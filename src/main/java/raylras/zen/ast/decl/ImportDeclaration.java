package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.*;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public final class ImportDeclaration extends BaseNode implements Declaration, HasID {

    @NotNull
    private final IDNode id;
    @Nullable
    private final AliasDeclaration alias;

    public ImportDeclaration(@NotNull IDNode ref, @Nullable AliasDeclaration alias) {
        this.id = ref;
        this.alias = alias;
    }

    @NotNull
    @Override
    public IDNode getId() {
        return id;
    }

    public Optional<AliasDeclaration> getAlias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.of(id, alias).filter(Objects::nonNull).toList();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("import ").append(id);
        if (alias != null) {
            builder.append(" as ").append(alias);
        }
        builder.append(";");
        return builder.toString();
    }

}
