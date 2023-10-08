package raylras.zen.dap.debugserver.variable;

import com.sun.jdi.*;
import raylras.zen.dap.debugserver.runtime.DebugObjectManager;
import raylras.zen.dap.debugserver.runtime.VariableFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListView implements VariableProxy {
    private final ObjectReference ref;
    private final String name;

    protected ListView(String name, ObjectReference ref) {
        this.name = name;
        this.ref = ref;
    }

    @Override
    public List<VariableProxy> getChildren(DebugObjectManager manager) {
        ReferenceType type = ref.referenceType();
        ThreadReference thread = manager.getOwnerThread(this);
        Method sizeMethod = type.methodsByName("size", "()I").get(0);
        int length;
        try {
            length = ((IntegerValue) ref.invokeMethod(thread, sizeMethod, Collections.emptyList(), ObjectReference.INVOKE_SINGLE_THREADED)).value();
        } catch (Exception ignored) {
            VariableProxy error = manager.getVariableFactory().createError(name, "failed to evaluate", thread);
            return Collections.singletonList(error);
        }
        List<VariableProxy> variables = new ArrayList<>(length);
        Method getMethod = type.methodsByName("get").get(0);
        VirtualMachine virtualMachine = ref.virtualMachine();
        for (int i = 0; i < length; i++) {
            try {
                Value value = ref.invokeMethod(thread, getMethod, Collections.singletonList(virtualMachine.mirrorOf(i)), ObjectReference.INVOKE_SINGLE_THREADED);
                VariableProxy proxy = manager.getVariableFactory().createValueProxy(String.valueOf(i), value, thread);
                variables.add(proxy);
            } catch (Exception ignored) {
                VariableProxy error = manager.getVariableFactory().createError(String.valueOf(i), "failed to evaluate", thread);
                variables.add(error);
            }
        }
        return variables;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public String getValue(DebugObjectManager manager) {
        StringBuilder builder = new StringBuilder();
        ReferenceType type = ref.referenceType();
        ThreadReference thread = manager.getOwnerThread(this);
        Method sizeMethod = type.methodsByName("size", "()I").get(0);
        int length;
        try {
            length = ((IntegerValue) ref.invokeMethod(thread, sizeMethod, Collections.emptyList(), ObjectReference.INVOKE_SINGLE_THREADED)).value();
        } catch (Exception ignored) {
            return "<failed to evaluate>";
        }
        VirtualMachine virtualMachine = ref.virtualMachine();
        builder.append("[");
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            if (i > 10) {
                builder.append("...");
                break;
            }
            Method getMethod = type.methodsByName("get").get(0);
            try {
                Value value = ref.invokeMethod(thread, getMethod, Collections.singletonList(virtualMachine.mirrorOf(i)), ObjectReference.INVOKE_SINGLE_THREADED);
                builder.append(VariableFormatter.format(value, thread));
            } catch (Exception ignored) {

                builder.append("<failed to evaluate>");
            }
        }
        builder.append("]");

        return builder.toString();
    }


}
