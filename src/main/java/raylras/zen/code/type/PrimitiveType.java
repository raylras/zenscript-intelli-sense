package raylras.zen.code.type;

public class PrimitiveType extends Type {

    public Tag tag;

    public PrimitiveType(Tag tag) {
        this.tag = tag;
    }

    @Override
    public Tag getTag() {
        return tag;
    }

    @Override
    public String toString() {
        switch (tag) {
            case BOOL:
                return "bool";
            case BYTE:
                return "byte";
            case SHORT:
                return "short";
            case INT:
                return "int";
            case LONG:
                return "long";
            case FLOAT:
                return "float";
            case DOUBLE:
                return "double";
            case VOID:
                return "void";

            case BOOL_OBJ:
                return "bool?";
            case BYTE_OBJ:
                return "byte?";
            case SHORT_OBJ:
                return "short?";
            case INT_OBJ:
                return "int?";
            case LONG_OBJ:
                return "long?";
            case FLOAT_OBJ:
                return "float?";
            case DOUBLE_OBJ:
                return "double?";

            case ANY:
                return "any";
            case STRING:
                return "string";
            default:
                return "none";
        }
    }

}
