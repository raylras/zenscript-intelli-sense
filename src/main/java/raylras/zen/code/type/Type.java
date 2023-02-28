package raylras.zen.code.type;

public abstract class Type {

    public abstract Tag getTag();

    public enum Tag {
        BOOL,
        BYTE,
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        VOID,

        BOOL_OBJ,
        BYTE_OBJ,
        SHORT_OBJ,
        INT_OBJ,
        LONG_OBJ,
        FLOAT_OBJ,
        DOUBLE_OBJ,

        ANY,
        STRING,
        ARRAY,
        LIST,
        MAP,

        PACKAGE,
        CLASS,
        FUNCTION,

        NO_TAG;
    }

    public interface Visitor<R> {
        R visitClassType(ClassType type);
        R visitFunctionType(FunctionType type);
        R visitArrayType(ArrayType type);
        R visitListType(ListType type);
        R visitMapType(MapType type);
        R visitPrimitiveType(PrimitiveType type);
        R visitNoType(NoType type);
        R visitType(Type type);
    }

}
