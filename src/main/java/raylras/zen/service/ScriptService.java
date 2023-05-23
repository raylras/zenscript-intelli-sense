package raylras.zen.service;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.data.Declarator;
import raylras.zen.code.symbol.*;
import raylras.zen.util.StringUtils;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service to process scripts
 */
public class ScriptService {

    private Path root;
    private final Map<Path, CompilationUnit> unitMap = new HashMap<>();

    private final Map<String, VariableSymbol> globalVariables = new HashMap<>();

    private final Map<String, List<Symbol>> publicSymbols = new HashMap<>();


    public Path getRoot() {
        return root;
    }

    public void setRoot(Path root) {
        this.root = root;
    }

    public void unload(Path unitPath) {
        unitMap.remove(unitPath);
        // TODO: Invalid symbols
    }


    public CompilationUnit getUnit(Path unitPath) {
        return unitMap.get(unitPath);
    }

    public void load(CompilationUnit unit) {
        unitMap.put(unit.path, unit);

        String packageName = packageName(unit.path);
        for (Symbol topLevelSymbol : unit.getTopLevelSymbols()) {

            if (topLevelSymbol.getKind().isVariable()) {
                if (topLevelSymbol.getDeclarator() == Declarator.GLOBAL) {
                    globalVariables.put(topLevelSymbol.getName(), (VariableSymbol) topLevelSymbol);
                }
            } else if (topLevelSymbol.getKind() == ZenSymbolKind.FUNCTION) {
                publicSymbols.computeIfAbsent(packageName, i -> new ArrayList<>())
                    .add(topLevelSymbol);
            } else if (topLevelSymbol.getKind().isClass()) {
                String className = ((ClassSymbol) topLevelSymbol).getQualifiedName();
                publicSymbols.computeIfAbsent(className, i -> new ArrayList<>())
                    .add(topLevelSymbol);
            }

        }
    }


    public String packageName(Path fileName) {
        String scriptPackage = StreamSupport.stream(root.relativize(fileName).spliterator(), false)
            .map(Path::toString)
            .collect(Collectors.joining("."));

        return "scripts." + scriptPackage.substring(0, scriptPackage.length() - 2);
    }

    public Collection<VariableSymbol> getGlobalVariables() {
        return globalVariables.values();
    }

    public VariableSymbol getGlobalVariable(String name) {
        return globalVariables.get(name);
    }

    public Collection<String> allPackageNames() {
        return publicSymbols.keySet();
    }

    public List<Symbol> getSymbolsOfPackage(String packageName) {
        return publicSymbols.getOrDefault(packageName, Collections.emptyList());
    }

    public <T extends Symbol> T findSymbol(Class<T> type, String name) {
        String packageName = StringUtils.getPackageName(name);
        String symbolName = StringUtils.getSimpleName(name);
        List<Symbol> symbols = publicSymbols.get(packageName);

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
