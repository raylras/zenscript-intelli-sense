package raylras.zen.dap.debugserver.variable;

import com.sun.jdi.*;
import raylras.zen.dap.debugserver.runtime.DebugObjectManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapView implements VariableProxy {
    private final ObjectReference ref;
    private final String name;

    protected MapView(String name, ObjectReference ref) {
        this.ref = ref;
        this.name = name;
    }

    @Override
    public List<VariableProxy> getChildren(DebugObjectManager manager) {
        ThreadReference thread = manager.getOwnerThread(this);
        try {
            ReferenceType mapType = ref.referenceType();
            Method entrySetMethod = mapType.methodsByName("entrySet", "()Ljava/util/Set").get(0);
            ObjectReference entrySet = (ObjectReference) ref.invokeMethod(thread, entrySetMethod, Collections.emptyList(), ObjectReference.INVOKE_SINGLE_THREADED);

            ReferenceType entrySetType = entrySet.referenceType();
            Method toArrayMethod = entrySetType.methodsByName("toArray", "()[Ljava/lang/Object").get(0);
            ArrayReference entryArray = (ArrayReference) entrySet.invokeMethod(thread, toArrayMethod, Collections.emptyList(), ObjectReference.INVOKE_SINGLE_THREADED);
            int length = entryArray.length();
            List<VariableProxy> variables = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                VariableProxy proxy = manager.getVariableFactory().createMapEntryProxy(String.valueOf(i), (ObjectReference) entryArray.getValue(i), thread);
                variables.add(proxy);
            }
            return variables;
        } catch (Exception ignored) {
            VariableProxy error = manager.getVariableFactory().createError(name, "failed to evaluate", thread);
            return Collections.singletonList(error);
        }
    }
    @Override
    public String getName() {
        return name;
    }


    @Override
    public boolean isVirtual() {
        return true;
    }

    @Override
    public String getValue(DebugObjectManager manager) {
        return "Map";
    }
}
