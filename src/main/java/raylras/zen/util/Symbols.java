package raylras.zen.util;

import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.Type;

import java.util.List;
import java.util.stream.Collectors;

public class Symbols {

    public static <T extends Symbol> List<T> getMembersByName(Type type, String simpleName, Class<T> clazz) {
        return type.getMembers().stream()
                .filter(symbol -> symbol.getName().equals(simpleName))
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

}
