package raylras.zen.dap.debugserver.variable;

import com.sun.jdi.*;
import raylras.zen.dap.debugserver.runtime.DebugObjectManager;

import java.util.ArrayList;
import java.util.List;

public class JavaFieldView implements VariableProxy {
    private final ObjectReference data;
    private final String name;

    protected JavaFieldView(String name, ObjectReference data) {
        this.data = data;
        this.name = name;
    }

    public boolean isEmpty() {
        return data.referenceType().visibleFields().isEmpty();
    }

    @Override
    public List<VariableProxy> getChildren(DebugObjectManager manager) {
        List<VariableProxy> variables = new ArrayList<>();
        ReferenceType referenceType = data.referenceType();
        ThreadReference ownerThread = manager.getOwnerThread(this);
        for (Field field : data.referenceType().visibleFields()) {
            Value value;
            if (field.isStatic()) {
                value = referenceType.getValue(field);
            } else {
                value = data.getValue(field);
            }
            VariableProxy proxy = manager.getVariableFactory().createValueProxy(field.name(), value, ownerThread);
            variables.add(proxy);
        }
        return variables;
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
        return "";
    }

}
