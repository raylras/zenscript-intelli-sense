package raylras.zen.util;

import raylras.zen.code.type.Type;

public class TypeUtils {
    public static boolean isValidType(Type type) {
        return type != null && type.getKind() != Type.Kind.NONE;
    }
}
