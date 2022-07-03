package raylras.zen.ast;

import java.net.URI;
import java.util.LinkedList;

public record Symbol(String name, Node node, URI uri, LinkedList<Node> references) {

    public LinkedList<Node> getReferences() {
        return references;
    }

    public void addReference(Node node) {
        references.add(node);
    }

    public static Symbol create(String name, Node node, URI uri) {
        return new Symbol(name, node, uri, new LinkedList<>());
    }

}
