package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;

import java.util.List;

public class Types {

    public static final List<Type> PRIMITIVE_TYPES = List.of(
            BoolType.INSTANCE,
            ByteType.INSTANCE,
            ShortType.INSTANCE,
            IntType.INSTANCE,
            LongType.INSTANCE,
            FloatType.INSTANCE,
            DoubleType.INSTANCE,
            StringType.INSTANCE
    );

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
        return PRIMITIVE_TYPES.contains(type);
    }

}
