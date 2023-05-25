package raylras.zen.service;

import jdk.internal.net.http.common.Log;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.*;
import raylras.zen.code.type.Type;
import raylras.zen.util.Logger;
import raylras.zen.util.StringUtils;
import raylras.zen.util.SymbolUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class LibraryService {
    private static final Logger logger = Logger.getLogger("library");

    // NATIVE SYMBOL
    private final Scope rootScope;

    private final Map<String, ClassSymbol> classes = new HashMap<>();
    private final Map<String, List<FunctionSymbol>> globalFunctions = new HashMap<>();
    private final Map<String, VariableSymbol> globalVariables = new HashMap<>();

    private final Set<String> packages = new HashSet<>();

    private final Map<String, List<ExpandFunctionSymbol>> expandFunctions = new HashMap<>();


    public LibraryService(Scope rootScope) {
        this.rootScope = rootScope;
    }


    private <T extends FunctionSymbol> void putFunction(Map<String, List<T>> map, String key, T function) {
        Objects.requireNonNull(function);
        map.computeIfAbsent(key, k -> new ArrayList<>())
            .add(function);
    }

    private void addPackage(String name) {
        packages.add(name);
    }

    public void reload(Collection<CompilationUnit> dtsUnits) {
        logger.info("Begin building library index...");
        Instant start = Instant.now();
        classes.clear();
        packages.clear();
        globalFunctions.clear();
        globalVariables.clear();
        expandFunctions.clear();

        for (CompilationUnit dtsUnit : dtsUnits) {

            for (Symbol topLevelSymbol : dtsUnit.getTopLevelSymbols()) {

                if (topLevelSymbol.getKind().isClass()) {
                    String qualifiedName = ((ClassSymbol) topLevelSymbol).getQualifiedName();
                    classes.put(qualifiedName, (ClassSymbol) topLevelSymbol);
                    String packageName = StringUtils.getPackageName(qualifiedName);
                    addPackage(packageName);
                } else if (topLevelSymbol.getKind().isFunction()) {
                    if (topLevelSymbol.getKind() == ZenSymbolKind.FUNCTION_EXPRESSION) {
                        continue;
                    }
                    if (topLevelSymbol.getKind() == ZenSymbolKind.EXPAND_FUNCTION) {
                        String target = ((ExpandFunctionSymbol) topLevelSymbol).getExpandTarget().toString();
                        putFunction(expandFunctions, target, ((ExpandFunctionSymbol) topLevelSymbol));
                    } else {
                        putFunction(globalFunctions, topLevelSymbol.getName(), (FunctionSymbol) topLevelSymbol);
                    }
                } else if (topLevelSymbol.getKind().isVariable()) {
                    if (topLevelSymbol.getType().getKind() == Type.Kind.FUNCTION) {
                        // variable but typeof function:
                        ParseTree owner = topLevelSymbol.getOwner();
                        if (owner instanceof ZenScriptParser.VariableDeclarationContext) {
                            ZenScriptParser.InitializerContext initializer = ((ZenScriptParser.VariableDeclarationContext) owner).initializer();
                            FunctionSymbol expressionFunction = dtsUnit.getSymbol(initializer.expression());
                            if (expressionFunction != null) {
                                putFunction(globalFunctions, topLevelSymbol.getName(), expressionFunction);
                                continue;
                            }
                        }
                    }
                    globalVariables.put(topLevelSymbol.getName(), (VariableSymbol) topLevelSymbol);

                } else {
                    throw new IllegalArgumentException(topLevelSymbol.getKind() + " is not a valid global symbol in library");
                }
            }
        }

        logger.info("... Libray index loaded for %d ms", Duration.between(start, Instant.now()).toMillis());
    }

    public ClassSymbol getClassSymbol(String qualifiedName) {
        return classes.get(qualifiedName);
    }

    public List<ClassSymbol> getClassSymbols(BiPredicate<String, ClassSymbol> predicate) {
        return classes.entrySet()
            .stream()
            .filter(entry -> predicate.test(entry.getKey(), entry.getValue()))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }


    public List<ClassSymbol> getSymbolsOfPackage(String packageName) {
        return getClassSymbols((name, symbol) -> name.startsWith(packageName) && name.indexOf('.', packageName.length() + 1) < 0);
    }

    public List<Symbol> getNativeMembers(String nativeName) {
        if (!SymbolUtils.isNativeClass(nativeName)) {
            throw new IllegalStateException("not a native: " + nativeName);
        }

        return getClassSymbol(nativeName).getMembers();
    }

    public List<Symbol> getExpandFunctions(String type) {
        return Collections.emptyList();
    }

    public List<FunctionSymbol> getGlobalFunctions(String name) {
        return globalFunctions.getOrDefault(name, Collections.emptyList());
    }

    public List<FunctionSymbol> getGlobalFunctions(BiPredicate<String, FunctionSymbol> predicate) {
        return globalFunctions.entrySet()
            .stream()
            .flatMap(entry -> entry.getValue()
                .stream()
                .filter(it -> predicate.test(entry.getKey(), it))
            )
            .collect(Collectors.toList());
    }

    public VariableSymbol getGlobalVariable(String name) {
        return globalVariables.get(name);
    }

    public List<VariableSymbol> getGlobalVariables(BiPredicate<String, VariableSymbol> predicate) {
        return globalVariables.entrySet()
            .stream()
            .filter(entry -> predicate.test(entry.getKey(), entry.getValue()))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }

    public Collection<String> allGlobalClasses() {
        return classes.keySet();
    }

    public Collection<VariableSymbol> allGlobalVariables() {
        return globalVariables.values();
    }

    public List<FunctionSymbol> allGlobalFunctions() {
        return globalFunctions.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public Scope getRootScope() {
        return rootScope;
    }

    public Collection<String> allPackageNames() {
        return packages;
    }


    public Collection<String> allRootPackageNames() {
        Set<String> rootPackages = new HashSet<>();
        for (String packageName : allPackageNames()) {
            String rootName = StringUtils.getBeforeFirstDot(packageName);
            if (!rootName.isEmpty()) {
                rootPackages.add(rootName);
            }
        }
        return rootPackages;
    }

    public <T extends Symbol> T findSymbol(Class<T> type, String name) {
        ClassSymbol classSymbol = getClassSymbol(name);
        if (type.isInstance(classSymbol)) {
            return type.cast(classSymbol);
        }

        VariableSymbol variableSymbol = getGlobalVariable(name);
        if (type.isInstance(variableSymbol)) {
            return type.cast(variableSymbol);
        }

        List<FunctionSymbol> functionSymbols = getGlobalFunctions(name);
        if (type.isAssignableFrom(VariableSymbol.class)) {
            return type.cast(getGlobalVariable(name));
        }

        if (FunctionSymbol.class.isAssignableFrom(type)) {
            return type.cast(getGlobalFunctions(name).get(0));
        }

        return null;
    }

    public <T extends Symbol> List<T> findSymbols(Class<T> type, String name) {
        List<T> result = new ArrayList<>();

        if (type.isAssignableFrom(ClassSymbol.class)) {
            result.add(type.cast(getClassSymbol(name)));
        }

        if (type.isAssignableFrom(VariableSymbol.class)) {
            result.add(type.cast(getGlobalVariable(name)));
        }

        if (type.isAssignableFrom(FunctionSymbol.class)) {
            result.add(type.cast(getGlobalFunctions(name).get(0)));
        }

        return result;
    }
}
