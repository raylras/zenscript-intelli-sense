package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.LocatableID;
import raylras.zen.ast.Node;
import raylras.zen.ast.Range;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class ImportDeclaration extends BaseNode implements Declaration, LocatableID {

    @NotNull
    private final String reference;
    @Nullable
    private final AliasDeclaration alias;

    private Range idRange;

    public ImportDeclaration(@NotNull String ref, @Nullable AliasDeclaration alias) {
        this.reference = ref;
        this.alias = alias;
    }

    @NotNull
    public String getReference() {
        return reference;
    }

    public Optional<AliasDeclaration> getAlias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public Range getIdRange() {
        return idRange;
    }

    public void setIdRange(Range idRange) {
        this.idRange = idRange;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return alias == null ? Collections.emptyList() : Collections.singletonList(alias);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("import ").append(reference);
        if (alias != null) {
            builder.append(" as ").append(alias);
        }
        builder.append(";");
        return builder.toString();
    }

}
