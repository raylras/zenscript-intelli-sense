package raylras.zen.dap.debugserver.runtime;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Value;

public class VariableFormatter {

    public static String format(Value value, ThreadReference threadReference) {
        if (value instanceof ObjectReference objectReference) {
            return DebugAPIAdapter.toString(objectReference, threadReference);
        }
        return value.toString();
    }
}
