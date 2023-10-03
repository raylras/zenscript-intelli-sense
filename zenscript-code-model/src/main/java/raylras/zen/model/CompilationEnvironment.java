package raylras.zen.model;

import raylras.zen.bracket.BracketHandlerService;
import raylras.zen.model.symbol.ClassSymbol;
import raylras.zen.model.symbol.ExpandFunctionSymbol;
import raylras.zen.model.symbol.PackageSymbol;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.type.*;
import raylras.zen.util.PathUtil;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class CompilationEnvironment {

    public static final String DEFAULT_ROOT_DIRECTORY = "scripts";
    public static final String DEFAULT_GENERATED_DIRECTORY = "generated";

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

    public List<Symbol> getGlobalSymbols() {
        return getUnits().stream()
                .flatMap(unit -> unit.getTopLevelSymbols().stream())
                .filter(Symbol::isGlobal)
                .collect(Collectors.toList());
    }

    public Collection<ExpandFunctionSymbol> getExpandFunctions() {
        return getUnits().stream()
                .flatMap(unit -> unit.getTopLevelSymbols().stream())
                .filter(ExpandFunctionSymbol.class::isInstance)
                .map(ExpandFunctionSymbol.class::cast)
                .toList();
    }

    public Collection<ClassSymbol> getGeneratedClasses() {
        return getUnits().stream()
                .filter(CompilationUnit::isGenerated)
                .map(CompilationUnit::getGeneratedClass)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
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

    public Collection<Symbol> getExpands(Type type) {
        Collection<Symbol> expandFunctions = getExpandFunctions().stream()
                .filter(symbol -> symbol.getExpandingType().isSuperclassTo(type))
                .map(Symbol.class::cast)
                .toList();
        if (isPrimitive(type)) {
            Collection<Symbol> expands = new ArrayList<>(expandFunctions);
            getGeneratedClasses().stream()
                    .filter(symbol -> symbol.getQualifiedName().equals(type.toString()))
                    .findFirst()
                    .ifPresent(classSymbol -> expands.addAll(classSymbol.getSymbols()));
            return expands;
        } else {
            return expandFunctions;
        }
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
        return FileSystems.getDefault()
                .getPath(System.getProperty("user.home"))
                .resolve(".probezs")
                .resolve(PathUtil.toHash(env.getRoot()))
                .resolve(DEFAULT_GENERATED_DIRECTORY);
    }

    private static final List<Type> primitives = List.of(
            BoolType.INSTANCE,
            ByteType.INSTANCE,
            ShortType.INSTANCE,
            IntType.INSTANCE,
            LongType.INSTANCE,
            FloatType.INSTANCE,
            DoubleType.INSTANCE,
            StringType.INSTANCE
    );

    private static boolean isPrimitive(Type type) {
        return primitives.contains(type);
    }

}
