package raylras.zen.code;

import raylras.zen.code.symbol.Symbol;

import java.nio.file.Path;
import java.util.*;

public class CompilationEnvironment {

    public final Path compilationRoot;
    private final Map<Path, CompilationUnit> unitMap = new HashMap<>();

    public CompilationEnvironment(Path compilationRoot) {
        this.compilationRoot = compilationRoot;
    }

    public void addUnit(CompilationUnit unit) {
        unitMap.put(unit.path, unit);
    }

    public CompilationUnit getUnit(Path unitPath) {
        return unitMap.get(unitPath);
    }

    public void removeUnit(Path unitPath) {
        unitMap.remove(unitPath);
    }

    public Collection<CompilationUnit> getUnits() {
        return unitMap.values();
    }

    public Symbol lookupGlobalSymbol(String simpleName) {
        return getGlobalSymbols().stream()
                .filter(symbol -> symbol.isDeclaredBy(Declarator.GLOBAL))
                .filter(symbol -> Objects.equals(symbol.getName(), simpleName))
                .findFirst()
                .orElse(null);
    }

    public Collection<Symbol> getGlobalSymbols() {
        return Collections.emptyList();
    }

}
