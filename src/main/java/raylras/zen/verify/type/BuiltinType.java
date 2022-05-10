package raylras.zen.verify.type;

public class BuiltinType extends AbstractType {

    public static final BuiltinType BOOL = new BuiltinType("bool");
    public static final BuiltinType BYTE = new BuiltinType("byte");
    public static final BuiltinType SHORT = new BuiltinType("short");
    public static final BuiltinType INT = new BuiltinType("int");
    public static final BuiltinType LONG = new BuiltinType("long");
    public static final BuiltinType FLOAT = new BuiltinType("float");
    public static final BuiltinType DOUBLE = new BuiltinType("double");
    public static final BuiltinType VOID = new BuiltinType("void");
    public static final BuiltinType STRING = new BuiltinType("string");
    public static final BuiltinType BOOL_OBJ = new BuiltinType("bool?");
    public static final BuiltinType BYTE_OBJ = new BuiltinType("byte?");
    public static final BuiltinType SHORT_OBJ = new BuiltinType("short?");
    public static final BuiltinType INT_OBJ = new BuiltinType("int?");
    public static final BuiltinType LONG_OBJ = new BuiltinType("long?");
    public static final BuiltinType FLOAT_OBJ = new BuiltinType("float?");
    public static final BuiltinType DOUBLE_OBJ = new BuiltinType("double?");

    protected final String typeName;

    protected BuiltinType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }

}
