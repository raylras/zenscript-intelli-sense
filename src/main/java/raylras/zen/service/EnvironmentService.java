package raylras.zen.service;

import com.google.common.collect.ImmutableList;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.VariableSymbol;
import raylras.zen.util.MemberUtils;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvironmentService {

    private final LibraryService libraryService;
    private final ScriptService scriptService;
    private final TypeService typeService;

    private Map<Path, CompilationUnit> libraryDeclarations = new HashMap<>();

    public EnvironmentService(LibraryService library, ScriptService scripts, TypeService typeService) {
        this.libraryService = library;
        this.scriptService = scripts;
        this.typeService = typeService;
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


    public void addCompilationUnit(CompilationUnit unit) {
        if (unit.isDzs()) {
            libraryDeclarations.put(unit.path, unit);
            libraryService().load(libraryDeclarations.values());
        } else {
            scriptService().load(unit);
        }
    }


    public CompilationUnit getCompilationUnit(Path unitPath) {
        if (libraryDeclarations.containsKey(unitPath)) {
            return libraryDeclarations.get(unitPath);
        } else {
            return scriptService().getUnit(unitPath);
        }
    }

    public void removeCompilationUnit(Path unitPath) {
        if (libraryDeclarations.containsKey(unitPath)) {
            libraryDeclarations.remove(unitPath);
            libraryService().load(libraryDeclarations.values());
        } else {
            scriptService().unload(unitPath);
        }
    }


    public <T extends Symbol> T findSymbol(Class<T> type, String name) {
        if (name.startsWith("scripts")) {
            return scriptService.findSymbol(type, name);
        }
        if (type.isAssignableFrom(VariableSymbol.class)) {
            // script only has global variables, not having methods
            T scriptGlobal = type.cast(scriptService.getGlobalVariable(name));
            if (scriptGlobal != null) {
                return scriptGlobal;
            }
        }

        return libraryService.findSymbol(type, name);
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
}
