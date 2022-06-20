package raylras.zen.ast.type;

public final class ArrayType implements Type {

    private final Type base;
    private int extent = -1;

    public ArrayType(Type base) {
        this.base = base;
    }

    public Type getBase() {
        return base;
    }

    public int getExtent() {
        return extent;
    }

    public void setExtent(int extent) {
        this.extent = extent;
    }

    @Override
    public boolean equivalent(Type that) {
        if (that != null && that.getClass() == ArrayType.class) {
            return this.base.equivalent(((ArrayType) that).base);
        }
        return false;
    }

    @Override
    public String toString() {
        if (extent < 0) {
            return base + "[]";
        } else {
            return base + "[" + extent + "]";
        }
    }

}
