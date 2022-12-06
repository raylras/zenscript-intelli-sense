package raylras.zen.semantic.type;

public final class PrimitiveType implements Type {

    public static final PrimitiveType ANY = new PrimitiveType("any");
    public static final PrimitiveType BOOL = new PrimitiveType("bool");
    public static final PrimitiveType BYTE = new PrimitiveType("byte");
    public static final PrimitiveType SHORT = new PrimitiveType("short");
    public static final PrimitiveType INT = new PrimitiveType("int");
    public static final PrimitiveType LONG = new PrimitiveType("long");
    public static final PrimitiveType FLOAT = new PrimitiveType("float");
    public static final PrimitiveType DOUBLE = new PrimitiveType("double");
    public static final PrimitiveType STRING = new PrimitiveType("string");
    public static final PrimitiveType VOID = new PrimitiveType("void");

    private final String name;

    private PrimitiveType(String name) {
        this.name = name;
    }

    @Override
    public String typeName() {
        return name;
    }

    @Override
    public boolean isType(Type type) {
        return this == type;
    }

    @Override
    public String toString() {
        return "(type " + name + ")";
    }

}
