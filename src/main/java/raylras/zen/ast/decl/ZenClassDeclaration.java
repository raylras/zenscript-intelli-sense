package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.*;
import raylras.zen.ast.stmt.VariableDeclStatement;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public final class ZenClassDeclaration extends BaseNode implements Declaration, HasID {

    @NotNull
    private final IDNode id;
    @NotNull
    private final List<VariableDeclStatement> properties;
    @NotNull
    private final List<ConstructorDeclaration> constructors;
    @NotNull
    private final List<FunctionDeclaration> functions;

    public ZenClassDeclaration(
            @NotNull IDNode id,
            @NotNull List<VariableDeclStatement> properties,
            @NotNull List<ConstructorDeclaration> constructors,
            @NotNull List<FunctionDeclaration> functions) {
        this.id = id;
        this.properties = properties;
        this.constructors = constructors;
        this.functions = functions;
    }

    @NotNull
    @Override
    public IDNode getId() {
        return id;
    }

    @NotNull
    public List<VariableDeclStatement> getProperties() {
        return properties;
    }

    @NotNull
    public List<ConstructorDeclaration> getConstructors() {
        return constructors;
    }

    @NotNull
    public List<FunctionDeclaration> getFunctions() {
        return functions;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.of(id, properties, constructors, functions)
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
        return "zenClass " + id + " {...}";
    }

}
