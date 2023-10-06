package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;

import java.util.stream.Collectors;

public class Types {

    public static SubtypeResult test(Type source, Type target, CompilationEnvironment env) {
        if (source.equals(target)) {
            return SubtypeResult.SELF;
        }
        if (target.isSuperclassTo(source)) {
            return SubtypeResult.INHERIT;
        }
        if (source.isCastableTo(target, env)) {
            return SubtypeResult.CASTER;
        }
        return SubtypeResult.MISMATCH;
    }

    public static boolean isPrimitive(Type type) {
        return type instanceof NumberType
                || type instanceof BoolType
                || type instanceof VoidType;
    }

    public static String getSimpleName(Type type) {
        if (type instanceof ClassType classType) {
            return classType.getSimpleName();
        }
        if (type instanceof ArrayType arrayType) {
            return getSimpleName(arrayType.elementType()) + "[]";
        }
        if (type instanceof ListType listType) {
            return "[" + getSimpleName(listType.elementType()) + "]";
        }
        if (type instanceof MapType mapType) {
            return "Map.Entry<" + getSimpleName(mapType.keyType()) + "," + getSimpleName(mapType.valueType()) + ">";
        }
        if (type instanceof FunctionType functionType) {
            return "function" + functionType.parameterTypes().stream()
                    .map(Types::getSimpleName)
                    .collect(Collectors.joining(",", "(", ")"))
                    + getSimpleName(functionType.returnType());
        }
        return type.getTypeName();
    }

}