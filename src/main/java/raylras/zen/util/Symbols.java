package raylras.zen.util;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.MemberProvider;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.Type;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Symbols {

    public static <T extends Symbol> List<T> getMembersByName(Type type, String simpleName, Class<T> clazz, CompilationEnvironment env) {
        return getMember(type, clazz, env, it -> it.getName().equals(simpleName));
    }

    public static <T extends Symbol> List<T> getMember(Type type, Class<T> clazz, CompilationEnvironment env, Predicate<T> filter) {

        if (!(type instanceof MemberProvider memberProvider)) {
            return Collections.emptyList();
        }
        return memberProvider.withExpandMembers(env).getMembers().stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .filter(filter)
                .collect(Collectors.toList());

    }

}
