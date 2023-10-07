package raylras.zen.dap.debugserver.variable;

import raylras.zen.dap.debugserver.runtime.DebugObjectManager;

import java.util.List;

public class ScopeProxy implements VariableProxy {
    private final List<VariableProxy> variables;
    private final String name;

    protected ScopeProxy(String name, List<VariableProxy> variables) {
        this.variables = variables;
        this.name = name;
    }

    @Override
    public List<VariableProxy> getChildren(DebugObjectManager manager) {
        return variables;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue(DebugObjectManager manager) {
        return name;
    }
}
