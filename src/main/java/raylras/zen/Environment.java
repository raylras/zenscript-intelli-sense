package raylras.zen;

import raylras.zen.ast.FunctionNode;
import raylras.zen.ast.NativeClassNode;
import raylras.zen.ast.VariableNode;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Environment {

    public final URI pathToScriptsFolder;
    public final Map<String, NativeClassNode> nativeClasses;
    public final Map<String, VariableNode> globalVariables;
    public final Map<String, FunctionNode> globalFunctions;

    public Environment(URI pathToScriptsFolder) {
        this.pathToScriptsFolder = pathToScriptsFolder;
        this.nativeClasses = new HashMap<>();
        this.globalVariables = new HashMap<>();
        this.globalFunctions = new HashMap<>();
    }

    public NativeClassNode getNativeType(String zenClassName) {
        return nativeClasses.get(zenClassName);
    }

    public void addNativeClass(String zenClassName, Class<?> nativeClass) {
        nativeClasses.put(zenClassName, new NativeClassNode(nativeClass));
    }

    public void addGlobalVariable(String varName, VariableNode variableNode) {
        globalVariables.put(varName, variableNode);
    }

}
