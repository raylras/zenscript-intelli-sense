package raylras.zen.code;

import raylras.zen.code.data.Declarator;
import raylras.zen.code.symbol.Symbol;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CompilationContext {

    public final Path compilationRoot;
    private final Map<Path, CompilationUnit> unitMap = new HashMap<>();

    public CompilationContext(Path compilationRoot) {
        this.compilationRoot = compilationRoot;
    }

    public void addCompilationUnit(CompilationUnit unit) {
        unitMap.put(unit.path, unit);
    }

    public CompilationUnit getCompilationUnit(Path unitPath) {
        return unitMap.get(unitPath);
    }

    public void removeCompilationUnit(Path unitPath) {
        unitMap.remove(unitPath);
    }

    public Collection<CompilationUnit> getCompilationUnits() {
        return unitMap.values();
    }

    public Symbol lookupGlobal(String name) {
        for (CompilationUnit unit : getCompilationUnits()) {
            for (Symbol symbol : unit.getTopLevelSymbols()) {
                if (!symbol.getName().equals(name)) {
                    continue;
                }
                if (unit.isDzs()) {
                    return symbol;
                }
                if (symbol.isDeclaredBy(Declarator.GLOBAL)) {
                    return symbol;
                }
            }
        }
        return null;
    }

    public List<Symbol> getGlobals() {
        return getCompilationUnits().stream()
                .flatMap(unit -> unit.getTopLevelSymbols().stream())
                .filter(symbol -> symbol.isDeclaredBy(Declarator.GLOBAL))
                .collect(Collectors.toList());
    }

}
