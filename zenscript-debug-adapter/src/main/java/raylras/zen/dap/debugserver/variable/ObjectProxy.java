package raylras.zen.dap.debugserver.variable;

import com.sun.jdi.*;
import raylras.zen.dap.debugserver.runtime.DebugAPIAdapter;
import raylras.zen.dap.debugserver.runtime.DebugObjectManager;
import raylras.zen.dap.debugserver.runtime.VariableFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectProxy implements VariableProxy {
    private final ObjectReference ref;
    private final String name;

    protected ObjectProxy(String name, ObjectReference objectReference) {
        this.ref = objectReference;
        this.name = name;
    }


    @Override
    public List<VariableProxy> getChildren(DebugObjectManager manager) {
        List<VariableProxy> result = new ArrayList<>();
        VariableProxyFactory factory = manager.getVariableFactory();
        ThreadReference ownerThread = manager.getOwnerThread(this);

        ReferenceType referenceType = ref.referenceType();

        if (referenceType instanceof ClassType classType) {
            ClassType current = classType;
            while (current != null && !"java.lang.Object".equals(current.name())) {
                collectMembers(manager, current, ownerThread, factory, result);
                current = current.superclass();
            }

            for (InterfaceType interfaceType : classType.allInterfaces()) {
                collectMembers(manager, interfaceType, ownerThread, factory, result);
            }

        }

        if (factory.isIterable(referenceType)) {
            VariableProxy lazyIterable = factory.createLazy("[[Enumerate Items]]", () -> {
                ArrayReference arrayReference = DebugAPIAdapter.iterableToArray(ref, ownerThread);
                ArrayView arrayProxy = factory.createArrayProxy("[[Enumerate Items]]", arrayReference, ownerThread);
                arrayProxy.setVirtual(true);
                return arrayProxy;
            }, ownerThread);
            result.add(lazyIterable);
        }

        JavaFieldView fieldView = factory.createFieldView("[[Java Fields]]", ref, ownerThread);
        if (!fieldView.isEmpty()) {
            result.add(fieldView);
        }

        return result;
    }

    private void collectMembers(DebugObjectManager manager, ReferenceType referenceType, ThreadReference ownerThread, VariableProxyFactory factory, List<VariableProxy> result) {
        String[] members = DebugAPIAdapter.memberSignatures(referenceType, ownerThread);
        for (String member : members) {
            String[] split = member.split(":");

            if (split.length < 2) {
                continue;
            }
            String name = split[0];

            try {

                if (split.length == 2) {
                    String fieldName = split[1];
                    Field field = referenceType.fieldByName(fieldName);
                    Value value = ref.getValue(field);
                    VariableProxy proxy = factory.createValueProxy(name, value, ownerThread);
                    result.add(proxy);
                } else {
                    String methodName = split[1];
                    String methodSignature = split[2];

                    try {
                        Method method = referenceType.methodsByName(methodName, methodSignature).get(0);
                        Value value = ref.invokeMethod(ownerThread, method, Collections.emptyList(), ObjectReference.INVOKE_SINGLE_THREADED);
                        VariableProxy proxy = factory.createValueProxy(name, value, ownerThread);
                        result.add(proxy);
                    } catch (Exception e) {
                        VariableProxy error = manager.getVariableFactory().createError(name, "failed to evaluate: " + name + ":" + e.getMessage(), ownerThread);
                        result.add(error);
                    }
                }
            } catch (Exception e) {
                VariableProxy err = manager.getVariableFactory().createError(name, "failed to evaluate: " + e.getMessage(), ownerThread);
                result.add(err);
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue(DebugObjectManager manager) {
        ThreadReference ownerThread = manager.getOwnerThread(this);
        return VariableFormatter.format(ref, ownerThread);
    }


}
