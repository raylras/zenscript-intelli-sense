package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.*;
import raylras.zen.ast.type.FunctionType;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FunctionDeclaration extends BaseNode implements Declaration, HasID {

    @NotNull
    private final IDNode id;
    @NotNull
    private final List<ParameterDeclaration> parameters;
    @Nullable
    private final TypeDeclaration resultDecl;
    @NotNull
    private final BlockNode block;

    public FunctionDeclaration(@NotNull IDNode id, @NotNull List<ParameterDeclaration> parameters, @Nullable TypeDeclaration resultDecl, @NotNull BlockNode block) {
        this.id = id;
        this.parameters = parameters;
        this.resultDecl = resultDecl;
        this.block = block;
    }

    @NotNull
    @Override
    public IDNode getId() {
        return id;
    }

    @NotNull
    public List<ParameterDeclaration> getParameters() {
        return parameters;
    }

    public Optional<TypeDeclaration> getResultDecl() {
        return Optional.ofNullable(resultDecl);
    }

    @NotNull
    public BlockNode getBlock() {
        return block;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.of(id, parameters, resultDecl, block)
                .filter(Objects::nonNull)
                .<Node>mapMulti((obj, consumer) -> {
                    if (obj instanceof Collection<?> c) {
                        c.forEach(element -> consumer.accept((Node) element));
                    } else {
                        consumer.accept((Node) obj);
                    }
                })
                .toList();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("function ").append(id.getName());
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
