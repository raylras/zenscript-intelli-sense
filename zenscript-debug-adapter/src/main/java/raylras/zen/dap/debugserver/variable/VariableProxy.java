package raylras.zen.dap.debugserver.variable;

import raylras.zen.dap.debugserver.runtime.DebugObjectManager;

import java.util.List;

public interface VariableProxy {

    List<VariableProxy> getChildren(DebugObjectManager manager);


    String getName();
    String getValue(DebugObjectManager manager);
    default String getType() {
        return null;
    }

    default boolean isVirtual() {
        return false;
    }
}
