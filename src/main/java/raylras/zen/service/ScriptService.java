package raylras.zen.service;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.symbol.*;
import raylras.zen.util.StringUtils;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service to process scripts
 * TODO: could be moved.
 */
public class ScriptService {
    private final FileManager fileManager;
    private final Path sourceRoot;

    public ScriptService(FileManager fileManager, Path sourceRoot) {
        this.fileManager = fileManager;
        this.sourceRoot = sourceRoot;
    }


    public Collection<VariableSymbol> getGlobalVariables() {
        return fileManager.getCompilationUnits(sourceRoot).stream()
            .filter(it -> !it.isDzs())
            .flatMap(it -> it.getGlobalVariables().values().stream())
            .collect(Collectors.toList());
    }

    private Stream<CompilationUnit> findCompilationUnitByPackageName(String packageName) {
        return fileManager.getCompilationUnits(sourceRoot).stream()
            .filter(it -> !it.isDzs())
            .filter(it -> packageName.startsWith(it.packageName()));
    }

    public VariableSymbol getGlobalVariable(String name) {
        return findCompilationUnitByPackageName(name)
            .map(it -> it.getGlobalVariables().get(name))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }


    // package names that only take filename into consideration
    public Collection<String> allScriptPackageNames() {
        return fileManager.getCompilationUnits(sourceRoot).stream()
            .filter(it -> !it.isDzs())
            .map(CompilationUnit::packageName)
            .collect(Collectors.toList());
    }

    public Collection<String> allSubPackageNames(String packageName) {

        return findCompilationUnitByPackageName(packageName)
            .flatMap(it -> it.getPublicSymbols().keySet().stream())
            .filter(it -> it.startsWith(packageName))
            .collect(Collectors.toList());
    }

    public List<Symbol> getSymbolsOfPackage(String packageName) {
        return findCompilationUnitByPackageName(packageName)
            .flatMap(unit -> unit.getPublicSymbols()
                .entrySet()
                .stream()
                .filter(it -> it.getKey().startsWith(packageName) && it.getKey().indexOf('.', packageName.length() + 1) < 0)
                .flatMap(it -> it.getValue().stream())
            )
            .collect(Collectors.toList());
    }

    public <T extends Symbol> T findSymbol(Class<T> type, String name) {
        String packageName = StringUtils.getPackageName(name);
        String symbolName = StringUtils.getSimpleName(name);
        List<Symbol> symbols = getSymbolsOfPackage(packageName);

        if (symbols == null) {
            return null;
        }

        for (Symbol symbol : symbols) {
            if (type.isInstance(symbol) && Objects.equals(symbol.getName(), symbolName)) {
                return type.cast(symbol);
            }
        }
        return null;
    }

}
