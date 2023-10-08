package raylras.zen.dap.debugserver.variable;

import raylras.zen.dap.debugserver.runtime.DebugObjectManager;

import java.util.Collections;
import java.util.List;

public class ErrorVariableProxy implements VariableProxy{
    private final String name;
    private final String text;

    public ErrorVariableProxy(String name, String text) {
        this.name = name;
        this.text = text;
    }

    @Override
    public List<VariableProxy> getChildren(DebugObjectManager manager) {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue(DebugObjectManager manager) {
        return "<" + text + ">";
    }
}
