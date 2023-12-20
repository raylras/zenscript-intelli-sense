package raylras.zen.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import raylras.zen.model.symbol.*;
import raylras.zen.model.type.StringType;
import raylras.zen.model.type.Type;
import raylras.zen.model.type.Types;
import raylras.zen.util.PathUtil;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

public class CompilationEnvironment {

    public static final String DEFAULT_ROOT_DIRECTORY = "scripts";
    public static final String DEFAULT_GENERATED_DIRECTORY = "generated";

    private final Path root;
    private final Map<Path, CompilationUnit> unitMap = new HashMap<>();
    private Collection<String> availablePreprocessors;

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

    public boolean containsUnit(Path unitPath) {
        return unitMap.containsKey(unitPath);
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
        return SymbolFactory.createPackageSymbol(this);
    }

    public Collection<String> getAvailablePreprocessors() {
        if (availablePreprocessors == null) {
            resolveAvailablePreprocessors(this);
        }
        return availablePreprocessors;
    }

    public Path getRoot() {
        return root;
    }

    public Path getGeneratedRoot() {
        return FileSystems.getDefault()
                .getPath(System.getProperty("user.home"))
                .resolve(".probezs")
                .resolve(PathUtil.toHash(root))
                .resolve(DEFAULT_GENERATED_DIRECTORY);
    }

    public Path relativize(Path other) {
        Path generatedRoot = getGeneratedRoot();
        if (Files.exists(generatedRoot) && PathUtil.isSubPath(other, generatedRoot)) {
            return generatedRoot.relativize(other);
        } else {
            return root.getParent().relativize(other);
        }
    }

    public void clear() {
        unitMap.clear();
    }

    @Override
    public String toString() {
        return root.toString();
    }

    private static void resolveAvailablePreprocessors(CompilationEnvironment env) {
        Path path = env.getGeneratedRoot().resolve("preprocessors.json");
        if (Files.exists(path)) {
            try {
                Gson gson = new Gson();
                env.availablePreprocessors = gson.fromJson(Files.newBufferedReader(path), new TypeToken<HashSet<String>>(){});
            } catch (Exception e) {
                env.availablePreprocessors = new HashSet<>();
                throw new RuntimeException(e);
            }
        }
    }

}
