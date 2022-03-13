package raylras.zen.ast.type;


import raylras.zen.ast.NativeClassNode;

public class TypeNativeClass extends Type {

    private final String className;
    private NativeClassNode nativeClass;

    public TypeNativeClass(String className) {
        this.className = className;
    }

    public NativeClassNode getNativeClass() {
        return nativeClass;
    }

    public void setNativeClass(NativeClassNode nativeClass) {
        this.nativeClass = nativeClass;
    }

    @Override
    public String toString() {
        return className;
    }

}
