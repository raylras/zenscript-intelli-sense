package raylras.zen.dap.debugserver.variable;

import com.sun.jdi.*;
import raylras.zen.dap.debugserver.runtime.DebugObjectManager;
import raylras.zen.dap.debugserver.runtime.VariableFormatter;

import java.util.Collections;
import java.util.List;

public class MapEntryProxy implements VariableProxy {
    private final ObjectReference ref;
    private final String name;

    protected MapEntryProxy(String name, ObjectReference ref) {
        this.ref = ref;
        this.name = name;
    }

    @Override
    public List<VariableProxy> getChildren(DebugObjectManager manager) {
        ReferenceType referenceType = ref.referenceType();
        ThreadReference thread = manager.getOwnerThread(this);
        try {
            Method getKey = referenceType.methodsByName("getKey").get(0);
            Method getValue = referenceType.methodsByName("getValue").get(0);

            Value key = ref.invokeMethod(thread, getKey, Collections.emptyList(), ObjectReference.INVOKE_SINGLE_THREADED);
            Value value = ref.invokeMethod(thread, getValue, Collections.emptyList(), ObjectReference.INVOKE_SINGLE_THREADED);

            return List.of(
                    manager.getVariableFactory().createValueProxy("key", key, thread),
                    manager.getVariableFactory().createValueProxy("value", value, thread)
            );
        } catch (Exception e) {
            VariableProxy error = manager.getVariableFactory().createError(name, "failed to evaluate", thread);
            return Collections.singletonList(error);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue(DebugObjectManager manager) {
        try {
            ReferenceType referenceType = ref.referenceType();
            ThreadReference thread = manager.getOwnerThread(this);
            Method getKey = referenceType.methodsByName("getKey").get(0);
            Method getValue = referenceType.methodsByName("getValue").get(0);

            Value key = ref.invokeMethod(thread, getKey, Collections.emptyList(), ObjectReference.INVOKE_SINGLE_THREADED);
            Value value = ref.invokeMethod(thread, getValue, Collections.emptyList(), ObjectReference.INVOKE_SINGLE_THREADED);
            return VariableFormatter.format(key, thread) + " --> " + VariableFormatter.format(value, thread);
        } catch (Exception e) {
            return "<failed to evaluate>";
        }
    }

    @Override
    public boolean isVirtual() {
        return true;
    }
}
