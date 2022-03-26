package raylras.zen.ast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class NativeClassNode extends ClassNode {

    public final Class<?> nativeClass;
    public List<Method> zenMethods;
    public List<String> zenGetters;
    public List<String> zenSetters;

    public NativeClassNode(Class<?> nativeClass) {
        this.nativeClass = nativeClass;
        this.zenMethods = new ArrayList<>();
        this.zenGetters = new ArrayList<>();
        this.zenSetters = new ArrayList<>();
    }

    @Override
    public String getClassName() {
        return nativeClass.getName();
    }

}
