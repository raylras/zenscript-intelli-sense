package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.util.Operators;

public sealed interface Type
        permits AnyType, ArrayType, BoolType, ClassType, ErrorType, FunctionType, IntRangeType, IntersectionType, ListType, MapEntryType, MapType, NumberType, StringType, VoidType {

    String getTypeName();

    default String getSimpleTypeName() {
        return getTypeName();
    }

    default boolean isSuperclassTo(Type type) {
        return this.getClass().isAssignableFrom(type.getClass());
    }

    default boolean isCastableTo(Type type, CompilationEnvironment env) {
        return type.isSuperclassTo(this) || Operators.hasCaster(this, type, env);
    }

}
