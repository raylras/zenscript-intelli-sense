package raylras.zen.ast;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Environment {

    public final URI pathToScriptsFolder;
    public final Map<String, NativeClassNode> nativeClassMap;
    public final Map<String, VariableNode> globalVariables;

    public Environment(URI pathToScriptsFolder) {
        this.pathToScriptsFolder = pathToScriptsFolder;
        this.nativeClassMap = new HashMap<>();
        this.globalVariables = new HashMap<>();
    }

    public NativeClassNode getNativeType(String zenClassName) {
        return nativeClassMap.get(zenClassName);
    }

    public void addNativeClass(String zenClassName, Class<?> nativeClass) {
        nativeClassMap.put(zenClassName, new NativeClassNode(nativeClass));
    }

}
