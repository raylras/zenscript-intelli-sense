package raylras.zen.code;

import raylras.zen.code.symbol.Symbol;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<Symbol> getGlobalSymbols() {
        return getUnits().stream()
                .flatMap(unit -> unit.getTopLevelSymbols().stream())
                .filter(symbol -> symbol.isDeclaredBy(Declarator.GLOBAL))
                .collect(Collectors.toList());
    }

    public void load() {
        unitMap.clear();
        try (Stream<Path> pathStream = Files.walk(compilationRoot)) {
            pathStream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(CompilationUnit.FILE_EXTENSION))
                    .forEach(unitPath -> {
                        CompilationUnit unit = new CompilationUnit(unitPath, this);
                        unit.load();
                        addUnit(unit);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
