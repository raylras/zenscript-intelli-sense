package raylras.zen.dap.debugserver.handler;

import com.sun.jdi.*;
import org.eclipse.lsp4j.debug.Scope;
import org.eclipse.lsp4j.debug.ScopePresentationHint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.dap.debugserver.DebugAdapterContext;
import raylras.zen.dap.debugserver.runtime.DebugObjectManager;
import raylras.zen.dap.debugserver.runtime.StackFrameManager;
import raylras.zen.dap.debugserver.variable.VariableProxy;
import raylras.zen.dap.debugserver.variable.VariableProxyFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScopeHandler {
    private static final Logger logger = LoggerFactory.getLogger(ScopeHandler.class);

    public static List<Scope> scopes(int frameId, DebugAdapterContext context) {
        DebugObjectManager debugObjectManager = context.getDebugObjectManager();
        StackFrameManager stackFrameManager = context.getStackFrameManager();
        StackFrame stackFrame = stackFrameManager.getById(frameId);
        if (stackFrame == null) {
            logger.warn("trying to fetch scope of invalid stack frame {}", frameId);
            return Collections.emptyList();
        }
        ThreadReference thread = stackFrame.thread();

        VariableProxyFactory factory = debugObjectManager.getVariableFactory();

        List<Scope> scopes = new ArrayList<>();


        List<VariableProxy> localVariables = new ArrayList<>();
        List<VariableProxy> argumentVariables = new ArrayList<>();

        try {
            ObjectReference thisObject = stackFrame.thisObject();
            if (thisObject != null) {
                VariableProxy proxy = factory.createValueProxy("this", thisObject, thread);
                localVariables.add(proxy);
            }
        } catch (Exception e) {
            logger.error("failed to get this of " + frameId, e);
        }
        try {
            for (LocalVariable visibleVariable : stackFrame.visibleVariables()) {
                Value value = stackFrame.getValue(visibleVariable);
                if (value == null) {
                    logger.error("failed to get local variables of " + visibleVariable.name());
                    continue;
                }
                VariableProxy proxy = factory.createValueProxy(visibleVariable.name(), value, thread);
                if (visibleVariable.isArgument()) {
                    argumentVariables.add(proxy);
                } else {
                    localVariables.add(proxy);
                }
            }
        } catch (AbsentInformationException ignored) {
            logger.warn("the stack frame '{}' does not have local variable info.", frameId);
        } catch (Exception e) {
            logger.error("failed to get local variables of " + frameId, e);
        }

        // locals
        Scope locals = new Scope();
        locals.setName("Locals");
        locals.setPresentationHint(ScopePresentationHint.LOCALS);

        VariableProxy localScope = factory.createScope("Locals", localVariables, thread);
        locals.setVariablesReference(debugObjectManager.getId(localScope));
        scopes.add(locals);

        // arguments
        Scope arguments = new Scope();
        arguments.setName("Arguments");
        arguments.setPresentationHint(ScopePresentationHint.ARGUMENTS);

        VariableProxy argumentScope = factory.createScope("Arguments", argumentVariables, thread);
        arguments.setVariablesReference(debugObjectManager.getId(argumentScope));
        scopes.add(arguments);




        return scopes;

    }


}
