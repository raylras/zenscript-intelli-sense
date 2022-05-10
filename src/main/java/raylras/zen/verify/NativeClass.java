package raylras.zen.verify;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

// TODO: native class
public class NativeClass {

    public final Class<?> nativeClass;
    public List<Method> zenMethods;
    public List<String> zenGetters;
    public List<String> zenSetters;

    public NativeClass(Class<?> nativeClass) {
        this.nativeClass = nativeClass;
        this.zenMethods = new ArrayList<>();
        this.zenGetters = new ArrayList<>();
        this.zenSetters = new ArrayList<>();
    }

}
