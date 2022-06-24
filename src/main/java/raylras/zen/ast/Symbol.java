package raylras.zen.ast;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public final class Symbol {

    @NotNull
    private final String name;
    @NotNull
    private final Node node;
    @NotNull
    private final URI uri;
    private final List<Node> references;

    public Symbol(@NotNull String name, @NotNull Node node, @NotNull URI uri) {
        this.name = name;
        this.node = node;
        this.uri = uri;
        this.references = new LinkedList<>();
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Node getNode() {
        return node;
    }

    @NotNull
    public URI getUri() {
        return uri;
    }

    public List<? extends Node> getReferences() {
        return references;
    }

    public void addReference(@NotNull Node node) {
        references.add(node);
    }

}
