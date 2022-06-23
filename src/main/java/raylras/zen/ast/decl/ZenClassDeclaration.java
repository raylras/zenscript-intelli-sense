package raylras.zen.ast.decl;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.LocatableID;
import raylras.zen.ast.Node;
import raylras.zen.ast.Range;
import raylras.zen.ast.stmt.VariableDeclStatement;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public final class ZenClassDeclaration extends BaseNode implements Declaration, LocatableID {

    @NotNull
    private final String name;
    @NotNull
    private final List<VariableDeclStatement> properties;
    @NotNull
    private final List<ConstructorDeclaration> constructors;
    @NotNull
    private final List<FunctionDeclaration> functions;

    private Range idRange;

    public ZenClassDeclaration(
            @NotNull String name,
            @NotNull List<VariableDeclStatement> properties,
            @NotNull List<ConstructorDeclaration> constructors,
            @NotNull List<FunctionDeclaration> functions) {
        this.name = name;
        this.properties = properties;
        this.constructors = constructors;
        this.functions = functions;
    }

    @NotNull
    public String getName() {
        return name;
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
    public Range getIdRange() {
        return idRange;
    }

    public void setIDRange(Range idRange) {
        this.idRange = idRange;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.of(properties, constructors, functions)
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public String toString() {
        return "zenClass " + name + " {...}";
    }

}
