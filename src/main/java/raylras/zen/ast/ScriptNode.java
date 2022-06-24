package raylras.zen.ast;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.decl.FunctionDeclaration;
import raylras.zen.ast.decl.ImportDeclaration;
import raylras.zen.ast.decl.ZenClassDeclaration;
import raylras.zen.ast.stmt.Statement;
import raylras.zen.ast.stmt.VariableDeclStatement;
import raylras.zen.ast.visit.NodeVisitor;

import java.net.URI;
import java.util.*;
import java.util.stream.Stream;

public final class ScriptNode extends BaseNode {

    @NotNull
    private final List<ImportDeclaration> imports;
    @NotNull
    private final List<FunctionDeclaration> functions;
    @NotNull
    private final List<ZenClassDeclaration> zenClasses;
    @NotNull
    private final List<Statement> statements;

    private URI uri;

    public ScriptNode(
            @NotNull List<ImportDeclaration> imports,
            @NotNull List<FunctionDeclaration> functions,
            @NotNull List<ZenClassDeclaration> zenClasses,
            @NotNull List<Statement> statements) {
        this.imports = imports;
        this.functions = functions;
        this.zenClasses = zenClasses;
        this.statements = statements;
    }

    @NotNull
    public List<ImportDeclaration> getImports() {
        return imports;
    }

    @NotNull
    public List<FunctionDeclaration> getFunctions() {
        return functions;
    }

    @NotNull
    public List<ZenClassDeclaration> getZenClasses() {
        return zenClasses;
    }

    @NotNull
    public List<Statement> getStatements() {
        return statements;
    }

    public List<VariableDeclStatement> getStatics() {
        return statements.stream()
                .filter(stmt -> stmt instanceof VariableDeclStatement)
                .map(stmt -> (VariableDeclStatement) stmt)
                .filter(VariableDeclStatement::isStatic)
                .toList();
    }

    public List<VariableDeclStatement> getGlobals() {
        return statements.stream()
                .filter(stmt -> stmt instanceof VariableDeclStatement)
                .map(stmt -> (VariableDeclStatement) stmt)
                .filter(VariableDeclStatement::isGlobal)
                .toList();
    }

    public URI getURI() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public Optional<Node> getNodeAtPosition(Position pos) {
        Queue<Node> queue = new ArrayDeque<>(getChildren());
        List<Node> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.getRange().contains(pos)) {
                queue.clear();
                result.add(node);
                var children = node.getChildren();
                if (!children.isEmpty()) {
                    queue.addAll(children);
                }
            }
        }
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(result.size() - 1));
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.of(imports, functions, zenClasses, statements)
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public String toString() {
        return uri == null ? "Unknown Source" : uri.toString();
    }

}
