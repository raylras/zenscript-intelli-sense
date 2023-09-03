package raylras.zen.util;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.SymbolProvider;
import raylras.zen.code.symbol.*;
import raylras.zen.code.type.Type;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Symbols {

    public static <T extends Symbol> List<T> getMembersByName(Type type, String simpleName, Class<T> clazz, CompilationEnvironment env) {
        return getMember(type, clazz, env, it -> it.getName().equals(simpleName));
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


    public static List<Symbol> lookupGlobalSymbols(CompilationUnit unit, String name) {
        List<Symbol> globals = unit.getEnv().getGlobalSymbols()
                .stream()
                .filter(it -> !(it instanceof Locatable locatable && locatable.getUnit() == unit))
                .filter(it -> Objects.equals(it.getName(), name))
                .toList();
        if (globals.isEmpty()) {
            return getTopLevelPackageSymbols(unit)
                    .stream()
                    .filter(it -> Objects.equals(it.getName(), name))
                    .toList();
        }
        return globals;
    }

    private static List<Symbol> getTopLevelPackageSymbols(CompilationUnit unit) {
        PackageTree<ClassSymbol> packageTree = PackageTree.of(".", unit.getEnv().getClassSymbolMap());
        List<Symbol> globalPackages = new ArrayList<>(packageTree.getSubTrees().size());
        CompilationEnvironment environment = unit.getEnv();
        for (Map.Entry<String, PackageTree<ClassSymbol>> entry : packageTree.getSubTrees().entrySet()) {
            boolean isGenerated = !"scripts".equals(entry.getKey());
            PackageSymbol packageSymbol = SymbolFactory.createPackageSymbol(entry.getKey(), entry.getKey(), entry.getValue(), environment, isGenerated);
            globalPackages.add(packageSymbol);
        }
        return globalPackages;
    }


}
