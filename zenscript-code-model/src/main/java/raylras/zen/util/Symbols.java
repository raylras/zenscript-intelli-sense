package raylras.zen.util;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.SymbolProvider;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.Executable;
import raylras.zen.model.type.Type;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Symbols {

    public static <T extends Symbol> List<T> getMembersByName(Type type, String simpleName, Class<T> clazz, CompilationEnvironment env) {
        return getMember(type, clazz, env, it -> it.getName().equals(simpleName));
    }

    public static List<Executable> getExecutableMembersByName(Type type, String simpleName, CompilationEnvironment env) {
        if (!(type instanceof SymbolProvider symbolProvider)) {
            return Collections.emptyList();
        }
        return symbolProvider.withExpands(env).getSymbols().stream()
                .filter(it -> it.getName().equals(simpleName))
                .filter(Executable.class::isInstance)
                .map(Executable.class::cast)
                .collect(Collectors.toList());
    }

    public static <T extends Symbol> List<T> getMember(Type type, Class<T> clazz, CompilationEnvironment env, Predicate<T> filter) {

        if (!(type instanceof SymbolProvider symbolProvider)) {
            return Collections.emptyList();
        }
        return symbolProvider.withExpands(env).getSymbols().stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .filter(filter)
                .collect(Collectors.toList());

    }

}
