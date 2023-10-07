package raylras.zen.dap.debugserver.variable;

import com.sun.jdi.*;
import raylras.zen.dap.debugserver.runtime.DebugObjectManager;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class VariableProxyFactory {

    private final DebugObjectManager manager;

    public VariableProxyFactory(DebugObjectManager manager) {
        this.manager = manager;
    }

    public boolean isList(Type type) {
        if (!(type instanceof ClassType classType)) {
            return false;
        }
        return classType.allInterfaces().stream().anyMatch(it -> "java.util.List".equals(it.name()));
    }

    public boolean isMap(Type type) {

        if (!(type instanceof ClassType classType)) {
            return false;
        }
        return classType.allInterfaces().stream().anyMatch(it -> "java.util.Map".equals(it.name()));
    }

    public boolean isIterable(Type type) {
        if (!(type instanceof ClassType classType)) {
            return false;
        }
        return classType.allInterfaces().stream().anyMatch(it -> "java.lang.Iterable".equals(it.name()));
    }

    public VariableProxy createValueProxy(String name, Value value, ThreadReference ownerThread) {
        if (value instanceof PrimitiveValue || value instanceof StringReference) {
            return createPrimitiveProxy(name, value, ownerThread);
        }
        if (value instanceof ArrayReference arrayReference) {
            return createArrayProxy(name, arrayReference, ownerThread);
        }
        if(value == null) {
            return createNull(name, ownerThread);
        }

        Type type = value.type();
        if (isList(type)) {
            return createListProxy(name, (ObjectReference) value, ownerThread);
        }
        if (isMap(type)) {
            return createMapProxy(name, (ObjectReference) value, ownerThread);
        }
        return createObject(name, value, ownerThread);

    }

    private VariableProxy createObject(String name, Value value, ThreadReference ownerThread) {
        if (!(value instanceof ObjectReference objectReference)) {
            return createNull(name, ownerThread);
        }
        return manager.put(new ObjectProxy(name, objectReference), ownerThread);
    }

    public VariableProxy createNull(String name, ThreadReference ownerThread) {

        VariableProxy variableProxy = new VariableProxy() {
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
                return "null";
            }
        };
        return manager.put(variableProxy, ownerThread);
    }

    public VariableProxy createPrimitiveProxy(String name, Value primitiveValue, ThreadReference ownerThread) {

        return manager.put(new PrimitiveValueProxy(name, primitiveValue), ownerThread);
    }

    public JavaFieldView createFieldView(String name, ObjectReference data, ThreadReference ownerThread) {
        return manager.put(new JavaFieldView(name, data), ownerThread);
    }

    public VariableProxy createMapEntryProxy(String name, ObjectReference ref, ThreadReference ownerThread) {

        return manager.put(new MapEntryProxy(name, ref), ownerThread);
    }

    public ArrayView createArrayProxy(String name, ArrayReference ref, ThreadReference ownerThread) {
        return manager.put(new ArrayView(name, ref), ownerThread);
    }

    public VariableProxy createListProxy(String name, ObjectReference ref, ThreadReference ownerThread) {
        return manager.put(new ListView(name, ref), ownerThread);
    }

    public VariableProxy createMapProxy(String name, ObjectReference ref, ThreadReference ownerThread) {
        return manager.put(new MapView(name, ref), ownerThread);
    }

    public VariableProxy createLazy(String name, Supplier<VariableProxy> supplier, ThreadReference ownerThread) {
        return manager.put(new LazyProxy(name, supplier), ownerThread);
    }

    public VariableProxy createScope(String name, List<VariableProxy> proxyList, ThreadReference ownerThread) {
        return manager.put(new ScopeProxy(name, proxyList), ownerThread);
    }

    public VariableProxy createError(String name, String reason, ThreadReference ownerThread) {

        return manager.put(new ErrorVariableProxy(name, reason), ownerThread);
    }

}
