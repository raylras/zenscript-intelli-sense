package raylras.zen.code;

import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompilationEnvironment {
    private static final Logger logger = Logger.getLogger("env");
    private final Path root;
    private final Map<Path, CompilationUnit> unitMap = new HashMap<>();

    public CompilationEnvironment(Path root) {
        this.root = root;
    }

    public void createUnit(Path unitPath) {
        CompilationUnit unit = new CompilationUnit(unitPath, this);
        unit.load();
        unitMap.put(unit.getPath(), unit);
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

    public Path getRoot() {
        return root;
    }

    public void load() {
        Instant started = Instant.now();
        unitMap.clear();
        try (Stream<Path> walk = Files.walk(root)) {
            walk.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(CompilationUnit.FILE_EXTENSION))
                .forEach(this::createUnit);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("Environment loaded in %d ms: %s", Duration.between(started, Instant.now()).toMillis(), root);
    }

    @Override
    public String toString() {
        return root.toString();
    }

}
