package raylras.zen.dap.debugserver.runtime;

import com.sun.jdi.*;

import java.util.Collections;

public class DebugAPIAdapter {

    public static ClassType findClass(VirtualMachine vm, String fqn) {
        return (ClassType) vm.classesByName(fqn).get(0);
    }

    public static ArrayReference iterableToArray(ObjectReference iterable, ThreadReference threadReference) {
        ClassType adapter = findClass(iterable.virtualMachine(), "youyihj.probezs.util.DebugAPIAdapter");
        Method iterableToArray = adapter.methodsByName("iterableToArray").get(0);

        try {
            return (ArrayReference) adapter.invokeMethod(threadReference, iterableToArray, Collections.singletonList(iterable), ClassType.INVOKE_SINGLE_THREADED);
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] memberSignatures(ReferenceType referenceType, ThreadReference threadReference) {
        ClassType adapter = findClass(referenceType.virtualMachine(), "youyihj.probezs.util.DebugAPIAdapter");
        Method iterableToArray = adapter.methodsByName("memberSignatures").get(0);

        try {
            ArrayReference arrayReference = (ArrayReference) adapter.invokeMethod(threadReference, iterableToArray, Collections.singletonList(referenceType.classObject()), ClassType.INVOKE_SINGLE_THREADED);
            int length = arrayReference.length();
            String[] result = new String[length];
            for (int i = 0; i < length; i++) {
                result[i] = ((StringReference) arrayReference.getValue(i)).value();
            }
            return result;
        } catch (Exception e) {
            return new String[0];
        }
    }

    public static String toString(ObjectReference objectReference, ThreadReference threadReference) {
        ClassType object = findClass(objectReference.virtualMachine(), "java.lang.Object");
        Method toString = objectReference.referenceType().methodsByName("toString").get(0);
        try {
            StringReference str = (StringReference) objectReference.invokeMethod(threadReference, toString, Collections.emptyList(), ClassType.INVOKE_SINGLE_THREADED);
            return str.value();
        } catch (Exception ignored) {
            return null;
        }
    }
}
