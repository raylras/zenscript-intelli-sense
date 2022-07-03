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

    public List<Node> getNodeAtPosition(Position pos) {
        Queue<Node> queue = new ArrayDeque<>();
        findAtLine(pos).ifPresent(queue::add);
        Deque<Node> result = new ArrayDeque<>();
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.getRange().contains(pos)) {
                result.addFirst(node);
                queue.clear();
                queue.addAll(node.getChildren());
            }
        }
        return result.stream().toList();
    }

    // Binary search, find the first node on the specified line.
    // If no node is found, return Optional.empty().
    private Optional<Node> findAtLine(Position pos) {
        List<? extends Node> nodeList = getChildren();
        Node result = null;
        int start = 0;
        int end = nodeList.size() - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            Node midNode = nodeList.get(mid);
            if (midNode.getRange().line() <= pos.line()) {
                if (midNode.getRange().lastLine() >= pos.line()) {
                    result = midNode;
                    break;
                } else {
                    start = mid + 1;
                }
            } else {
                end = mid - 1;
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.of(imports, functions, zenClasses, statements)
                .flatMap(Collection::stream)
                .sorted()
                .toList();
    }

    @Override
    public String toString() {
        return uri == null ? "Unknown Source" : uri.toString();
    }

}
