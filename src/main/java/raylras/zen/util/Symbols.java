package raylras.zen.util;

import raylras.zen.code.common.MemberProvider;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.Type;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Symbols {

    public static <T extends Symbol> List<T> getMembersByName(Type type, String simpleName, Class<T> clazz) {
        return getMember(type, clazz, it -> it.getName().equals(simpleName));
    }

    public static <T extends Symbol> List<T> getMember(Type type, Class<T> clazz, Predicate<T> filter) {

        if (!(type instanceof MemberProvider memberProvider)) {
            return Collections.emptyList();
        }
        return memberProvider.getMembers().stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .filter(filter)
                .collect(Collectors.toList());

    }

}
