package raylras.zen.dap.debugserver.variable;

import raylras.zen.dap.debugserver.runtime.DebugObjectManager;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class LazyProxy implements VariableProxy {
    private final Supplier<VariableProxy> compute;
    private final String name;

    protected LazyProxy(String name, Supplier<VariableProxy> compute) {
        this.compute = compute;
        this.name = name;
    }

    @Override
    public List<VariableProxy> getChildren(DebugObjectManager manager) {
        VariableProxy variableProxy = compute.get();
        return Collections.singletonList(variableProxy);
    }
    @Override
    public String getName() {
        return name;
    }


    @Override
    public String getValue(DebugObjectManager manager) {
        return "<lazy>";
    }
}
