package raylras.zen.util;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.symbol.Executable;
import raylras.zen.model.symbol.FunctionSymbol;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolProvider;
import raylras.zen.model.type.ClassType;
import raylras.zen.model.type.Type;

import java.util.Optional;
import java.util.stream.Stream;

public class Symbols {

    public static Optional<FunctionSymbol> getAnonymousFunction(ClassType type, CompilationEnvironment env) {
        return Symbols.getMembers(type, FunctionSymbol.class, env)
                .filter(it -> it.getName().isEmpty())
                .findFirst();
    }

    public static Stream<Executable> getExecutableMembersByName(Type type, String simpleName, CompilationEnvironment env) {
        return getMembers(type, Symbol.class, env)
                .filter(it -> it.getName().equals(simpleName))
                .filter(Executable.class::isInstance)
                .map(Executable.class::cast);
    }

    public static <T extends Symbol> Stream<T> getMembers(Type type, Class<T> memberClass, CompilationEnvironment env) {
        if (type instanceof SymbolProvider provider) {
            return provider.withExpands(env).getSymbols().stream()
                    .filter(memberClass::isInstance)
                    .map(memberClass::cast);
        }
        return Stream.empty();
    }

}
