package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.*;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.*;
import java.util.stream.Stream;

public final class ParameterDeclaration extends BaseNode implements Declaration, Variable, LocatableID {

    @NotNull
    private final String name;
    @Nullable
    private final TypeDeclaration typeDecl;
    @Nullable
    private final Expression defaultValue;

    private Range idRange;

    public ParameterDeclaration(@NotNull String name, @Nullable TypeDeclaration typeDecl, @Nullable Expression defaultValue) {
        this.name = name;
        this.typeDecl = typeDecl;
        this.defaultValue = defaultValue;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public Optional<TypeDeclaration> getTypeDecl() {
        return Optional.ofNullable(typeDecl);
    }

    public Optional<Expression> getDefaultValue() {
        return Optional.ofNullable(defaultValue);
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.of(typeDecl, defaultValue)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Range getIdRange() {
        return idRange;
    }

    public void setIDRange(Range idRange) {
        this.idRange = idRange;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        if (getType() != null) {
            builder.append(" as ").append(getType());
        }
        if (defaultValue != null) {
            builder.append(" = ").append(defaultValue);
        }
        return builder.toString();
    }

}
