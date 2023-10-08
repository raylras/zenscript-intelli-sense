package raylras.zen.dap.debugserver.variable;

import com.sun.jdi.Value;
import raylras.zen.dap.debugserver.runtime.DebugObjectManager;

import java.util.Collections;
import java.util.List;

public class PrimitiveValueProxy implements VariableProxy {
    private final Value value;
    private final String name;

    protected PrimitiveValueProxy(String name, Value value) {
        this.name = name;
        this.value = value;
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
        return value.toString();
    }


}
