package raylras.zen.service;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.data.Declarator;
import raylras.zen.code.symbol.*;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;
import raylras.zen.util.StringUtils;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EnvironmentService {

    private final Map<String, ClassSymbol> classes = new HashMap<>();
    private final Map<String, List<FunctionSymbol>> globalFunctions = new HashMap<>();
    private final Map<String, VariableSymbol> globalVariables = new HashMap<>();

    private final Map<String, LibraryPackageSymbol> packages = new HashMap<>();


    private final Map<String, List<ExpandFunctionSymbol>> expandFunctions = new HashMap<>();


    private <T extends FunctionSymbol> void putFunction(Map<String, List<T>> map, String key, T function) {
        Objects.requireNonNull(function);
        map.computeIfAbsent(key, k -> new ArrayList<>())
            .add(function);
    }

    private void getOrCreatePackage(String name) {
        packages.computeIfAbsent(name, n -> new LibraryPackageSymbol(n, this));
    }

    public void load(List<CompilationUnit> dtsUnits) {
        for (CompilationUnit dtsUnit : dtsUnits) {

            for (Symbol topLevelSymbol : dtsUnit.getTopLevelSymbols()) {

                if (topLevelSymbol.getKind().isClass()) {
                    String qualifiedName = topLevelSymbol.getName();
                    classes.put(qualifiedName, (ClassSymbol) topLevelSymbol);
                    String packageName = StringUtils.getPackageName(qualifiedName);
                    getOrCreatePackage(packageName);
                } else if (topLevelSymbol.getKind().isFunction()) {
                    if (topLevelSymbol.getKind() == ZenSymbolKind.EXPAND_FUNCTION) {
                        String target = ((ExpandFunctionSymbol) topLevelSymbol).getExpandTarget().getName();
                        putFunction(expandFunctions, target, ((ExpandFunctionSymbol) topLevelSymbol));
                    } else {
                        putFunction(globalFunctions, topLevelSymbol.getName(), (FunctionSymbol) topLevelSymbol);
                    }
                } else if (topLevelSymbol.getKind().isVariable()) {
                    Type type = topLevelSymbol.getType();

                    // variable is also possible as function
                    if (type.getKind() == Type.Kind.FUNCTION) {
                        FunctionSymbol functionSymbol = (FunctionSymbol) type.lookupSymbol(dtsUnit);
                        putFunction(globalFunctions, topLevelSymbol.getName(), functionSymbol);
                    } else if (type.getKind() == Type.Kind.FUNCTIONAL_INTERFACE) {
                        ClassSymbol classSymbol = (ClassSymbol) type.lookupSymbol(dtsUnit);
                        putFunction(globalFunctions, topLevelSymbol.getName(), classSymbol.getFunctionalInterface());

                        // acts as both variable and function
                        if (!topLevelSymbol.getMembers().isEmpty()) {
                            globalVariables.put(topLevelSymbol.getName(), (VariableSymbol) topLevelSymbol);
                        }
                    } else {
                        globalVariables.put(topLevelSymbol.getName(), (VariableSymbol) topLevelSymbol);
                    }

                } else {
                    throw new IllegalArgumentException(topLevelSymbol.getKind() + " is not a valid global symbol in library");
                }
            }
        }
    }

    public PackageSymbol getPackageSymbol(String packageName) {
        return packages.get(packageName);
    }

    public List<PackageSymbol> getPackageSymbols(BiPredicate<String, PackageSymbol> predicate) {
        return packages.entrySet()
            .stream()
            .filter(entry -> predicate.test(entry.getKey(), entry.getValue()))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }

    public List<ClassSymbol> getClassSymbols(BiPredicate<String, ClassSymbol> predicate) {
        return classes.entrySet()
            .stream()
            .filter(entry -> predicate.test(entry.getKey(), entry.getValue()))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }


    public List<ClassSymbol> getClassSymbolsByPackageName(String packageName) {
        return getClassSymbols((name, symbol) -> name.startsWith(packageName) && name.indexOf('.', packageName.length() + 1) < 0);
    }

    public List<FunctionSymbol> getGlobalFunctions(BiPredicate<String, FunctionSymbol> predicate) {
        return globalFunctions.entrySet()
            .stream()
            .flatMap(entry -> entry.getValue()
                .stream()
                .filter(it -> predicate.test(entry.getKey(), it))
            )
            .collect(Collectors.toList());
    }

    public List<VariableSymbol> getGlobalVariables(BiPredicate<String, VariableSymbol> predicate) {
        return globalVariables.entrySet()
            .stream()
            .filter(entry -> predicate.test(entry.getKey(), entry.getValue()))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }

    public Collection<String> allGlobalClasses() {
        return classes.keySet();
    }


}
