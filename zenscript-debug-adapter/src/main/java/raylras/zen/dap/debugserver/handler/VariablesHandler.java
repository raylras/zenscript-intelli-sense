package raylras.zen.dap.debugserver.handler;

import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablePresentationHint;
import org.eclipse.lsp4j.debug.VariablePresentationHintKind;
import org.eclipse.lsp4j.debug.VariablesArguments;
import raylras.zen.dap.debugserver.DebugAdapterContext;
import raylras.zen.dap.debugserver.runtime.DebugObjectManager;
import raylras.zen.dap.debugserver.variable.LazyProxy;
import raylras.zen.dap.debugserver.variable.VariableProxy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class VariablesHandler {


    public static List<Variable> variables(VariablesArguments args, DebugAdapterContext context) {
        DebugObjectManager debugObjectManager = context.getDebugObjectManager();
        VariableProxy proxy = debugObjectManager.getById(args.getVariablesReference());
        if (proxy == null) {
            return Collections.emptyList();
        }

        List<VariableProxy> children = proxy.getChildren(debugObjectManager);

        return children.stream().map(it -> toDAPVariable(it, debugObjectManager)).collect(Collectors.toList());
    }


    private static Variable toDAPVariable(VariableProxy proxy, DebugObjectManager manager) {
        int id = manager.getId(proxy);
        Variable variable = new Variable();

        variable.setVariablesReference(id);
        variable.setName(proxy.getName());
//        variable.setEvaluateName(proxy.getEvaluateName());
        variable.setType(proxy.getType());
        variable.setValue(proxy.getValue(manager));

        VariablePresentationHint presentationHint = new VariablePresentationHint();

        if (proxy.isVirtual()) {
            presentationHint.setKind(VariablePresentationHintKind.VIRTUAL);
        }
        if (proxy instanceof LazyProxy) {
            presentationHint.setLazy(true);
        }
        variable.setPresentationHint(presentationHint);

        return variable;
    }


}
