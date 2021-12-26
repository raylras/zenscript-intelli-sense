package raylras.zen.type;

import raylras.zen.lsp.antlr.ZenScriptParser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ZenType {

    ANY(ZenScriptParser.ANY, TypeAny.INSTANCE),
    BOOL(ZenScriptParser.BOOL, TypeBool.INSTANCE),
    BYTE(ZenScriptParser.BYTE, TypeByte.INSTANCE),
    SHORT(ZenScriptParser.SHORT, TypeShort.INSTANCE),
    INT(ZenScriptParser.INT, TypeInt.INSTANCE),
    LONG(ZenScriptParser.LONG, TypeLong.INSTANCE),
    FLOAT(ZenScriptParser.FLOAT, TypeFloat.INSTANCE),
    DOUBLE(ZenScriptParser.DOUBLE, TypeDouble.INSTANCE),
    STRING(ZenScriptParser.STRING, TypeString.INSTANCE),
    VOID(ZenScriptParser.VOID, TypeVoid.INSTANCE),
    ;

    private final int id;
    private final Type type;

    ZenType(int id, Type type) {
        this.type = type;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public static Type getZenType(ZenScriptParser.AsTypeContext asTypeContext) {
        if (asTypeContext == null) {
            return TypeAny.INSTANCE;
        }
        ZenScriptParser.TypeContext typeContext = asTypeContext.type();
        return getZenType(typeContext);
    }

    public static List<Type> getZenTypes(List<ZenScriptParser.TypeContext> contexts) {
        return contexts.stream().map(ZenType::getZenType).collect(Collectors.toList());
    }

    public static Type getZenType(ZenScriptParser.TypeContext context) {
        if (context == null) {
            return null;
        }
        switch (context.getRuleIndex()) {

            case ZenScriptParser.RULE_typePrimitive:
                return getPrimitiveType(context.typePrimitive());

            case ZenScriptParser.RULE_typeArray:
                return new TypeArray(context.typeArray());

            case ZenScriptParser.RULE_typeClass:
                return new TypeZenClass(context.typeClass());

            case ZenScriptParser.RULE_typeFunction:
                return new TypeFunction(context.typeFunction());

            case ZenScriptParser.RULE_typeList:
                return new TypeList(context.typeList());

            case ZenScriptParser.RULE_typeMap:
                return new TypeMap(context.typeMap());

            default:
                return null;
        }
    }

    private static Type getPrimitiveType(ZenScriptParser.TypePrimitiveContext context) {
        int id = context.start.getType();
        return Arrays.stream(ZenType.values())
                .filter(zenType -> zenType.getId() == id)
                .map(ZenType::getType)
                .findFirst().orElse(null);
    }


}

