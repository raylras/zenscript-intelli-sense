package raylras.zen.code;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.ClassType;
import raylras.zen.util.Symbols;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompilationEnvironment {

    public static final String DEFAULT_ROOT_DIRECTORY = "scripts";

    private final Path root;
    private final Map<Path, CompilationUnit> unitMap = new HashMap<>();

    public CompilationEnvironment(Path root) {
        this.root = root;
    }

    public CompilationUnit createUnit(Path unitPath) {
        CompilationUnit unit = new CompilationUnit(unitPath, this);
        unitMap.put(unitPath, unit);
        return unit;
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

    public Map<Path, CompilationUnit> getUnitMap() {
        return unitMap;
    }

    /**
     * @deprecated Use {@link #getGlobalSymbolMap()} instead.
     */
    @Deprecated
    public List<Symbol> getGlobalSymbols() {
        return getUnits().stream()
                .flatMap(unit -> unit.getTopLevelSymbols().stream())
                .filter(symbol -> symbol.isModifiedBy(Symbol.Modifier.GLOBAL))
                .collect(Collectors.toList());
    }

    public Multimap<String, Symbol> getGlobalSymbolMap() {
        return getUnits().stream()
                .flatMap(Symbols::getToplevelSymbolsSpecial)
                .collect(Multimaps.toMultimap(Symbols::getQualifierName, Function.identity(), HashMultimap::create));
    }

    public Map<String, ClassType> getClassTypeMap() {
        return getUnits().stream()
                .flatMap(unit -> unit.getTopLevelSymbols().stream())
                .filter(symbol -> symbol instanceof ClassSymbol)
                .map(symbol -> (ClassSymbol) symbol)
                .collect(Collectors.toMap(ClassSymbol::getQualifiedName, ClassSymbol::getType));
    }

    public Path getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return root.toString();
    }

}
