package raylras.zen.code;

import com.google.common.collect.ImmutableList;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.VariableSymbol;
import raylras.zen.service.FileManager;
import raylras.zen.service.LibraryService;
import raylras.zen.service.ScriptService;
import raylras.zen.service.TypeService;
import raylras.zen.util.Logger;
import raylras.zen.util.Utils;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompilationEnvironment {
    private static final Logger logger = Logger.getLogger("environment");

    private final Path sourceRoot;
    public final LibraryService libraryService;
    public final ScriptService scriptService;
    public final TypeService typeService;

    private final FileManager fileManager;

    public CompilationEnvironment(Path sourceRoot, FileManager fileManager) {
        this.fileManager = fileManager;
        this.libraryService = new LibraryService(new Scope(null, null));
        this.scriptService = new ScriptService(this.fileManager, sourceRoot);
        this.typeService = new TypeService();
        this.sourceRoot = sourceRoot;
    }

    public LibraryService libraryService() {
        return libraryService;
    }

    public ScriptService scriptService() {
        return scriptService;
    }

    public TypeService typeService() {
        return typeService;
    }

    // about compliation units


    public void reloadLibraries(List<CompilationUnit> dzsUnits) {
        libraryService.reload(dzsUnits);
    }

    public <T extends Symbol> T findSymbol(Class<T> type, String name) {
        Instant started = Instant.now();
        T result = null;
        if (name.startsWith("scripts")) {
            result = scriptService.findSymbol(type, name);
        } else if (type.isAssignableFrom(VariableSymbol.class)) {
            // script only has global variables, not having methods
            T scriptGlobal = type.cast(scriptService.getGlobalVariable(name));
            if (scriptGlobal != null) {
                result = scriptGlobal;
            }
        }
        if (result == null) {
            result = libraryService.findSymbol(type, name);
        }

        Utils.logLongTime(started, 10, time -> {
            logger.warn("Find symbol used long times (%d ms), name=%s, type=%s", time, name, type);
        });
        return result;
    }


    public List<Symbol> getExpandFunctions(String toString) {
        // TODO: scripts expand functions
        return libraryService.getExpandFunctions(toString);
    }

    public List<Symbol> getGlobals() {
        return ImmutableList.<Symbol>builder()
            .addAll(scriptService.getGlobalVariables())
            .addAll(libraryService().allGlobalVariables())
            .addAll(libraryService().allGlobalFunctions())
            .build();
    }

    public List<Symbol> getSymbolsOfPackage(String packageName) {
        return ImmutableList.<Symbol>builder()
            .addAll(scriptService.getSymbolsOfPackage(packageName))
            .addAll(libraryService.getSymbolsOfPackage(packageName))
            .build();
    }

    public Path getSourceRoot() {
        return sourceRoot;
    }

    public void unload() {
        //
    }

    public String packageName(Path path) {
        return fileManager.packageName(this.sourceRoot, path);
    }
}
