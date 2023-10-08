package raylras.zen.model;

import raylras.zen.bracket.BracketHandlerService;
import raylras.zen.model.symbol.ClassSymbol;
import raylras.zen.model.symbol.ExpandFunctionSymbol;
import raylras.zen.model.symbol.PackageSymbol;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.type.StringType;
import raylras.zen.model.type.Type;
import raylras.zen.model.type.Types;
import raylras.zen.util.PathUtil;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

public class CompilationEnvironment {

    public static final String DEFAULT_ROOT_DIRECTORY = "scripts";

    private final Path root;
    private final Path generatedRoot;
    private final Map<Path, CompilationUnit> unitMap = new HashMap<>();
    private final BracketHandlerService bracketHandlerService = new BracketHandlerService(this);

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public CompilationEnvironment(Path root) {
        Objects.requireNonNull(root);
        this.root = root;
        this.generatedRoot = resolveGeneratedRoot(this);
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

    public Stream<Symbol> getGlobals() {
        return getUnits().stream()
                .flatMap(unit -> unit.getTopLevelSymbols().stream())
                .filter(Symbol::isGlobal);
    }

    public Stream<ClassSymbol> getClasses() {
        return getUnits().stream()
                .flatMap(unit -> unit.getTopLevelSymbols().stream())
                .filter(ClassSymbol.class::isInstance)
                .map(ClassSymbol.class::cast);
    }

    public Stream<ExpandFunctionSymbol> getExpandFunctions() {
        return getUnits().stream()
                .flatMap(unit -> unit.getTopLevelSymbols().stream())
                .filter(ExpandFunctionSymbol.class::isInstance)
                .map(ExpandFunctionSymbol.class::cast);
    }

    public Stream<Symbol> getExpands(Type type) {
        Stream<Symbol> expandFunctions = getExpandFunctions()
                .filter(symbol -> symbol.getExpandingType().isSuperclassTo(type))
                .map(Symbol.class::cast);
        if (type instanceof StringType || Types.isPrimitive(type)) {
            Stream<Symbol> expandPrimitives = getClasses()
                    .filter(symbol -> symbol.getQualifiedName().equals(type.getTypeName()))
                    .findFirst()
                    .map(primitiveClass -> primitiveClass.getSymbols().stream())
                    .orElse(Stream.empty());
            return Stream.concat(expandFunctions, expandPrimitives);
        } else {
            return expandFunctions;
        }
    }

    public PackageSymbol getRootPackage() {
        // TODO: getRootPackage
        throw new RuntimeException("TODO: getRootPackage");
    }

    public Path getRoot() {
        return root;
    }

    public Path getGeneratedRoot() {
        return generatedRoot;
    }

    public BracketHandlerService getBracketHandlerService() {
        return bracketHandlerService;
    }

    public Path relativize(Path other) {
        Path root;
        if (PathUtil.isSubPath(other, generatedRoot)) {
            root = this.generatedRoot;
        } else {
            root = this.root.getParent();
        }
        return root.relativize(other);
    }

    public ReentrantReadWriteLock.ReadLock readLock() {
        return readWriteLock.readLock();
    }

    public ReentrantReadWriteLock.WriteLock writeLock() {
        return readWriteLock.writeLock();
    }

    public void clear() {
        unitMap.clear();
    }

    @Override
    public String toString() {
        return root.toString();
    }

    private static Path resolveGeneratedRoot(CompilationEnvironment env) {
        return PathUtil.resolveGeneratedRoot(env.getRoot());
    }

}
