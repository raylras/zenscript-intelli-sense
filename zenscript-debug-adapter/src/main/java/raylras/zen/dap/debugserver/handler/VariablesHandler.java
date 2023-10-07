package raylras.zen.dap.debugserver.handler;

import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablePresentationHint;
import org.eclipse.lsp4j.debug.VariablePresentationHintKind;
import org.eclipse.lsp4j.debug.VariablesArguments;
import raylras.zen.dap.debugserver.DebugAdapterContext;
import raylras.zen.dap.debugserver.runtime.DebugObjectManager;
import raylras.zen.dap.debugserver.variable.ErrorVariableProxy;
import raylras.zen.dap.debugserver.variable.LazyProxy;
import raylras.zen.dap.debugserver.variable.PrimitiveValueProxy;
import raylras.zen.dap.debugserver.variable.VariableProxy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 传入 Scope： 显示 Scope 下面的变量
 * 传入 PrimitiveType 或 String：直接显示值
 * 传入 ReferenceType：
 * - 根据 Type 计算 @ZenGetter 之类的值显示
 * - 添加 virtual member: __java_object__：显示 java 的 fields
 * - 如果是 数组、List、iterator等，添加 virtual member： __展开迭代器__
 */
public class VariablesHandler {


    public static List<Variable> variables(VariablesArguments args, DebugAdapterContext context) {
        DebugObjectManager debugObjectManager = context.getDebugObjectManager();
        VariableProxy proxy = debugObjectManager.getById(args.getVariablesReference());

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

        if(proxy.isVirtual()) {
            presentationHint.setKind(VariablePresentationHintKind.VIRTUAL);
        }
        if (proxy instanceof LazyProxy) {
            presentationHint.setLazy(true);
        }
        if(proxy instanceof PrimitiveValueProxy || proxy instanceof ErrorVariableProxy) {
            variable.setNamedVariables(0);
        }
        variable.setPresentationHint(presentationHint);

        return variable;
    }


}
