package raylras.zen.ast;

import raylras.zen.ast.stmt.Statement;

import java.net.URI;
import java.util.List;

public class ScriptNode extends ASTNode {

    private List<ImportNode> imports;
    private List<FunctionNode> functions;
    private List<ZenClassNode> zenClasses;
    private List<Statement> statements;

    private URI uri;

    public List<ImportNode> getImports() {
        return imports;
    }

    public void setImports(List<ImportNode> imports) {
        this.imports = imports;
    }

    public List<FunctionNode> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionNode> functions) {
        this.functions = functions;
    }

    public List<ZenClassNode> getZenClasses() {
        return zenClasses;
    }

    public void setZenClasses(List<ZenClassNode> zenClasses) {
        this.zenClasses = zenClasses;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public void accept(ASTVisitor<?> visitor) {
        imports.forEach(visitor::visitImport);
        functions.forEach(visitor::visitFunction);
        zenClasses.forEach(visitor::visitZenClass);
        statements.forEach(visitor::visitStatement);
    }

}
