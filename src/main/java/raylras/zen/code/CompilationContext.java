package raylras.zen.code;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CompilationContext {

    public final Map<Path, CompilationUnit> units = new HashMap<>();
    public final Path compilationRoot;

    public CompilationContext(Path compilationRoot) {
        this.compilationRoot = compilationRoot;
    }

    public void addCompilationUnit(CompilationUnit unit) {
        units.put(unit.path, unit);
    }

    public CompilationUnit getCompilationUnit(Path unitPath) {
        return units.get(unitPath);
    }

    public void removeCompilationUnit(Path unitPath) {
        units.remove(unitPath);
    }

}
