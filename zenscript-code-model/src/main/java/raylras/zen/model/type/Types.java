package raylras.zen.model.type;

import raylras.zen.model.CompilationEnvironment;

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

}
