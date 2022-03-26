package raylras.zen.ast.scope;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ErrorNode;
import raylras.zen.ast.VariableNode;
import raylras.zen.verify.EnumError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableScope extends ASTNode implements Scope<VariableNode> {

    private final Map<String, VariableNode> variables;
    private final List<ErrorNode> errorNodes;
    private Scope<?> parent;

    public VariableScope() {
        this.variables = new HashMap<>();
        this.errorNodes = new ArrayList<>();
    }

    @Override
    public Scope<?> getParent() {
        return parent;
    }

    @Override
    public void setParent(Scope<?> parent) {
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
    public void define(String name, VariableNode node) {
        ASTNode same = resolve(name);
        if (same == null) {
            variables.put(name, node);
        } else {
            errorNodes.add(new ErrorNode(same, EnumError.ALREADY_DEFINED));
        }
    }

}
