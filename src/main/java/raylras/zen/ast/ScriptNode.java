package raylras.zen.ast;

import raylras.zen.ast.scope.Scope;
import raylras.zen.ast.scope.VariableScope;
import raylras.zen.ast.stmt.Statement;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ScriptNode extends ASTNode implements Scope, IErrorNode {

    private final URI uri;
    private final List<ImportNode> imports;
    private final List<FunctionNode> functions;
    private final List<Statement> statements;
    private final VariableScope variables;
    private Scope parent;

    public ScriptNode(URI uri) {
        this.uri = uri;
        this.imports = new ArrayList<>();
        this.functions = new ArrayList<>();
        this.statements = new ArrayList<>();
        this.variables = new VariableScope();
    }

    public URI getUri() {
        return uri;
    }

    public List<ImportNode> getImports() {
        return imports;
    }

    public List<FunctionNode> getFunctions() {
        return functions;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void addImport(ImportNode node) {
        imports.add(node);
    }

    public void addFunction(FunctionNode node) {
        functions.add(node);
    }

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    @Override
    public Scope getParent() {
        return parent;
    }

    @Override
    public void setParent(Scope parent) {
        this.parent = parent;
    }

    @Override
    public ASTNode resolve(String name) {
        return variables.resolve(name);
    }

    @Override
    public void define(String name, ASTNode node) {
        variables.define(name, node);
    }

    @Override
    public List<ErrorNode> getErrors() {
        return variables.getErrors();
    }

}
