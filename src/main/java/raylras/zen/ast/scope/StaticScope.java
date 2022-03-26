package raylras.zen.ast.scope;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ErrorNode;
import raylras.zen.ast.FunctionNode;
import raylras.zen.ast.VariableNode;
import raylras.zen.verify.EnumError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StaticScope extends ASTNode implements Scope<ASTNode> {

    private final Map<String, ASTNode> statics;
    private final List<ErrorNode> errorNodes;
    private Scope<?> parent;

    public StaticScope() {
        this.statics = new HashMap<>();
        this.errorNodes = new ArrayList<>();
    }

    public Map<String, ASTNode> getStatics() {
        return statics;
    }

    public List<FunctionNode> getFunctionNodes() {
        return statics.values().stream()
                .filter(astNode -> astNode instanceof FunctionNode)
                .map(astNode -> (FunctionNode) astNode)
                .collect(Collectors.toList());
    }

    public List<VariableNode> getVariableNodes() {
        return statics.values().stream()
                .filter(astNode -> astNode instanceof VariableNode)
                .map(astNode -> (VariableNode) astNode)
                .collect(Collectors.toList());
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
        ASTNode result = statics.get(name);
        if (result == null && parent != null) {
            result = parent.resolve(name);
        }
        return result;
    }

    @Override
    public void define(String name, ASTNode node) {
        ASTNode same = resolve(name);
        if (same == null) {
            statics.put(name, node);
        } else {
            errorNodes.add(new ErrorNode(node, same, EnumError.ALREADY_DEFINED));
        }
    }

}
