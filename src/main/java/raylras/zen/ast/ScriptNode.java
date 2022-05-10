package raylras.zen.ast;

import raylras.zen.ast.stmt.Statement;

import java.net.URI;
import java.util.List;

public class ScriptNode extends ASTNode {

    private final List<ImportNode> importNodeList;
    private final List<FunctionNode> functionNodeList;
    private final List<ZenClassNode> zenClassNodeList;
    private final List<Statement> statementList;

    private final URI uri;

    public ScriptNode(List<ImportNode> importNodeList, List<FunctionNode> functionNodeList, List<ZenClassNode> zenClassNodeList, List<Statement> statementList, URI uri) {
        this.importNodeList = importNodeList;
        this.functionNodeList = functionNodeList;
        this.zenClassNodeList = zenClassNodeList;
        this.statementList = statementList;
        this.uri = uri;
    }

    public List<ImportNode> getImportNodeList() {
        return importNodeList;
    }

    public List<FunctionNode> getFunctionNodeList() {
        return functionNodeList;
    }

    public List<ZenClassNode> getZenClassNodeList() {
        return zenClassNodeList;
    }

    public List<Statement> getStatementList() {
        return statementList;
    }

    public URI getURI() {
        return uri;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitScriptNode(this);
        importNodeList.forEach(node -> node.accept(visitor));
        functionNodeList.forEach(node -> node.accept(visitor));
        zenClassNodeList.forEach(node -> node.accept(visitor));
        statementList.forEach(node -> node.accept(visitor));
    }

    @Override
    public String toString() {
        return uri.toString();
    }

}
