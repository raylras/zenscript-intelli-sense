package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.*;
import raylras.zen.ast.type.FunctionType;
import raylras.zen.ast.type.Type;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>(parameters.size() + (resultType == null ? 0 : 1) + 1);
        children.addAll(parameters);
        if (resultType != null) {
            children.add(resultType);
        }
        children.add(block);
        return Collections.unmodifiableList(children);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("function ").append(name);
        builder.append("(");
        builder.append(parameters.stream().map(Object::toString).collect(Collectors.joining(", ")));
        builder.append(")");
        Type type = getType();
        if (type != null && type.getClass() == FunctionType.class) {
            builder.append(" as ").append(((FunctionType) type).getResult());
        }
        builder.append(" {...}");
        return builder.toString();
    }

}
