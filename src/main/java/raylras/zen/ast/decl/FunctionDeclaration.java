package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.*;
import raylras.zen.ast.type.FunctionType;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FunctionDeclaration extends BaseNode implements Declaration, LocatableID {

    @NotNull
    private final String name;
    @NotNull
    private final List<ParameterDeclaration> parameters;
    @Nullable
    private final TypeDeclaration resultType;
    @NotNull
    private final BlockNode block;

    private Range idRange;

    public FunctionDeclaration(@NotNull String name, @NotNull List<ParameterDeclaration> parameters, @Nullable TypeDeclaration resultType, @NotNull BlockNode block) {
        this.name = name;
        this.parameters = parameters;
        this.resultType = resultType;
        this.block = block;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public List<ParameterDeclaration> getParameters() {
        return parameters;
    }

    public Optional<TypeDeclaration> getResultType() {
        return Optional.ofNullable(resultType);
    }

    @NotNull
    public BlockNode getBlock() {
        return block;
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
        return Stream.concat(
                parameters.stream(),
                Stream.of(resultType, block).filter(Objects::nonNull)
        ).toList();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("function ").append(name);
        builder.append("(");
        builder.append(parameters.stream().map(Object::toString).collect(Collectors.joining(", ")));
        builder.append(")");
        if (getType() instanceof FunctionType type) {
            builder.append(" as ").append(type.result());
        }
        builder.append(" {...}");
        return builder.toString();
    }

}
