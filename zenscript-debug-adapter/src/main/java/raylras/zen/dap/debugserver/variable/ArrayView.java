package raylras.zen.dap.debugserver.variable;

import com.sun.jdi.ArrayReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Value;
import raylras.zen.dap.debugserver.runtime.DebugObjectManager;
import raylras.zen.dap.debugserver.runtime.VariableFormatter;

import java.util.ArrayList;
import java.util.List;

public class ArrayView implements VariableProxy {
    private final ArrayReference ref;
    private final String name;
    private boolean virtual = false;

    protected ArrayView(String name, ArrayReference ref) {
        this.ref = ref;
        this.name = name;
    }

    @Override
    public List<VariableProxy> getChildren(DebugObjectManager manager) {
        int length = ref.length();
        List<VariableProxy> variables = new ArrayList<>(length);
        ThreadReference ownerThread = manager.getOwnerThread(this);
        for (int i = 0; i < length; i++) {
            VariableProxy proxy = manager.getVariableFactory().createValueProxy(String.valueOf(i), ref.getValue(i), ownerThread);
            variables.add(proxy);
        }
        return variables;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue(DebugObjectManager manager) {
        ThreadReference ownerThread = manager.getOwnerThread(this);
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(ref.length());
        builder.append(") ");
        builder.append("[");
        for (int i = 0; i < ref.length(); i++) {
            if (i != 0) {
                builder.append(", ");
            }
            if (i > 10) {
                builder.append("...");
                break;
            }
            Value value = ref.getValue(i);
            builder.append(VariableFormatter.format(value, ownerThread));
        }
        builder.append("]");

        return builder.toString();
    }

    @Override
    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }
}
