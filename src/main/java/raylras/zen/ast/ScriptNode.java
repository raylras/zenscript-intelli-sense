package raylras.zen.ast;

import raylras.zen.ast.decl.FunctionDeclaration;
import raylras.zen.ast.decl.ImportDeclaration;
import raylras.zen.ast.decl.ZenClassDeclaration;
import raylras.zen.ast.stmt.Statement;
import raylras.zen.ast.stmt.VariableDeclStatement;
import raylras.zen.ast.visit.NodeVisitor;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ScriptNode extends BaseNode {

    private final List<ImportDeclaration> imports;
    private final List<FunctionDeclaration> functions;
    private final List<ZenClassDeclaration> zenClasses;
    private final List<Statement> statements;

    private URI uri;

    public ScriptNode(List<ImportDeclaration> imports, List<FunctionDeclaration> functions, List<ZenClassDeclaration> zenClasses, List<Statement> statements) {
        this.imports = imports;
        this.functions = functions;
        this.zenClasses = zenClasses;
        this.statements = statements;
    }

    public List<ImportDeclaration> getImports() {
        return imports;
    }

    public List<FunctionDeclaration> getFunctions() {
        return functions;
    }

    public List<ZenClassDeclaration> getZenClasses() {
        return zenClasses;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public List<VariableDeclStatement> getStatics() {
        return statements.stream()
                .filter(stmt -> stmt.getClass() == VariableDeclStatement.class)
                .map(stmt -> (VariableDeclStatement) stmt)
                .filter(VariableDeclStatement::isStatic)
                .collect(Collectors.toList());
    }

    public List<VariableDeclStatement> getGlobals() {
        return statements.stream()
                .filter(stmt -> stmt.getClass() == VariableDeclStatement.class)
                .map(stmt -> (VariableDeclStatement) stmt)
                .filter(VariableDeclStatement::isGlobal)
                .collect(Collectors.toList());
    }

    public URI getURI() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        ArrayList<Node> children = new ArrayList<>(imports.size() + functions.size() + zenClasses.size() + statements.size());
        children.addAll(imports);
        children.addAll(functions);
        children.addAll(zenClasses);
        children.addAll(statements);
        return Collections.unmodifiableList(children);
    }

    @Override
    public String toString() {
        return uri == null ? "Unknown Source" : uri.toString();
    }

}
