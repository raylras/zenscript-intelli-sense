package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.*;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.*;
import java.util.stream.Stream;

public final class ParameterDeclaration extends BaseNode implements Declaration, Variable, HasID {

    @NotNull
    private final IDNode id;
    @Nullable
    private final TypeDeclaration typeDecl;
    @Nullable
    private final Expression defaultValue;

    public ParameterDeclaration(@NotNull IDNode id, @Nullable TypeDeclaration typeDecl, @Nullable Expression defaultValue) {
        this.id = id;
        this.typeDecl = typeDecl;
        this.defaultValue = defaultValue;
    }

    @NotNull
    @Override
    public IDNode getId() {
        return id;
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
        return Stream.of(id, typeDecl, defaultValue)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(id);
        if (getType() != null) {
            builder.append(" as ").append(getType());
        }
        if (defaultValue != null) {
            builder.append(" = ").append(defaultValue);
        }
        return builder.toString();
    }

}
