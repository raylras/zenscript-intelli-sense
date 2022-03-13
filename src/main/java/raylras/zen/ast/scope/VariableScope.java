package raylras.zen.ast.scope;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.EnumError;
import raylras.zen.ast.ErrorNode;
import raylras.zen.ast.IErrorNode;
import raylras.zen.ast.stmt.Statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableScope implements Scope, IErrorNode {

    private Scope parent;
    private Map<String, ASTNode> variables;
    private List<ErrorNode> errorNodes;

    public VariableScope() {
        this.variables = new HashMap<>();
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
        ASTNode result = variables.get(name);
        if (result == null && parent != null) {
            result = parent.resolve(name);
        }
        return result;
    }

    @Override
    public void define(String name, ASTNode node) {
        ASTNode same = resolve(name);
        if (same == null) {
            variables.put(name, node);
        } else {
            errorNodes.add(new ErrorNode(same, EnumError.ALREADY_DEFINED));
        }
    }

    @Override
    public void addStatement(Statement statement) {
        throw new RuntimeException("Cannot add a statement to VariableScope!");
    }

    @Override
    public List<ErrorNode> getErrors() {
        return errorNodes;
    }

}
