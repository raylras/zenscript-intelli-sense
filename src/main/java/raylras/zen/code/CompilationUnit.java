package raylras.zen.code;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.symbol.*;
import raylras.zen.code.type.resolve.DefinitionResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.service.LibraryService;
import raylras.zen.service.TypeService;
import raylras.zen.util.ParseTreeProperty;

import java.nio.file.Path;
import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;

public class CompilationUnit {

    public static final String FILE_EXTENSION = ".zs";
    public static final String DZS_FILE_EXTENSION = ".d.zs";

    private final CompilationEnvironment env;
    private final ParseTreeProperty<Scope> scopeProp = new ParseTreeProperty<>();
    private final ParseTreeProperty<Symbol> symbolProp = new ParseTreeProperty<>();
    private final Path filePath;

    // modifiable data
    private ParseTree parseTree;
    private CommonTokenStream tokenStream;

    private Instant fileModifyTime;


    public CompilationUnit(Path filePath, CompilationEnvironment env) {
        this.filePath = filePath;
        this.env = env;
    }

    private Map<String, VariableSymbol> globalVariables;

    private Map<String, List<Symbol>> publicSymbols;

    public Scope lookupScope(ParseTree node) {
        ParseTree n = node;
        while (n != null) {
            Scope scope = scopeProp.get(n);
            if (scope != null) {
                return scope;
            }
            n = n.getParent();
        }
        return null;
    }

    public <T extends Symbol> T lookupSymbol(Class<T> type, Scope fromScope, String name, boolean searchGlobal) {
        Symbol symbol = lookupLocalSymbol(fromScope,
            s -> type.isInstance(s) && Objects.equals(s.getName(), name)
        );

        if (symbol == null && searchGlobal) {
            symbol = environment().findSymbol(type, name);
        }

        return type.cast(symbol);
    }


    /**
     * lookup class symbol, considering import
     */
    public ClassSymbol lookupClassSymbol(Scope fromScope, String name, boolean searchGlobal) {
        // priority: local -> import -> global
        Symbol symbol = lookupLocalSymbol(fromScope,
            it -> (it instanceof ClassSymbol || it instanceof ImportSymbol) &&
                Objects.equals(it.getName(), name)
        );

        if (symbol instanceof ClassSymbol) {
            return (ClassSymbol) symbol;
        }

        if (symbol instanceof ImportSymbol) {
            Symbol target = ((ImportSymbol) symbol).getSimpleTarget();
            if (target instanceof ClassSymbol) {
                return (ClassSymbol) target;
            } else {
                return null;
            }
        }

        if (searchGlobal) {
            return environment().findSymbol(ClassSymbol.class, name);
        }

        return null;
    }

    private Symbol lookupLocalSymbol(Scope fromScope, Predicate<Symbol> condition) {
        Scope scope = fromScope;
        Symbol symbol = null;
        while (scope != null) {
            symbol = scope.getSymbol(condition);
            if (symbol != null)
                break;
            scope = scope.parent;
        }

        return symbol;
    }

    public <T extends Symbol> List<T> lookupLocalSymbols(Class<T> type, Scope fromScope, Predicate<T> condition) {
        Scope scope = fromScope;
        List<T> result = new ArrayList<>();
        while (scope != null) {
            for (Symbol symbol : scope.getSymbols()) {
                if (type.isInstance(symbol) && condition.test(type.cast(symbol))) {
                    result.add(type.cast(symbol));
                }
            }
            scope = scope.parent;
        }

        return result;
    }

    public Scope getScope(ParseTree node) {
        return scopeProp.get(node);
    }

    @SuppressWarnings("unchecked")
    public <T extends Symbol> T getSymbol(ParseTree node) {
        return (T) symbolProp.get(node);
    }

    public LibraryService libraryService() {
        return environment().libraryService();
    }

    public TypeService typeService() {
        return environment().typeService();
    }

    public CompilationEnvironment environment() {
        return env;
    }

    public Collection<Scope> getScopes() {
        return scopeProp.getProperties();
    }

    public Collection<Symbol> getSymbols() {
        return symbolProp.getProperties();
    }

    public Collection<Symbol> getTopLevelSymbols() {
        return getScope(getParseTree()).symbols;
    }

    public void load(CharStream charStream, Instant fileModifyTime) {
        this.symbolProp.clear();
        this.scopeProp.clear();
        this.publicSymbols = null;
        this.globalVariables = null;
        this.fileModifyTime = fileModifyTime;
        parse(charStream);
        new DefinitionResolver(this, scopeProp, symbolProp).resolve();
    }

    private void parse(CharStream charStream) {
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        tokenStream = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(getTokenStream());
        parser.removeErrorListeners();
        parseTree = parser.compilationUnit();
    }

    public boolean isDzs() {
        return filePath.toString().endsWith(DZS_FILE_EXTENSION);
    }


    public Path getFilePath() {
        return this.filePath;
    }

    public Instant getModifiedTime() {
        return fileModifyTime;
    }


    public String packageName() {
        return env.packageName(this.filePath);
    }

    public ParseTree getParseTree() {
        return parseTree;
    }

    public CommonTokenStream getTokenStream() {
        return tokenStream;
    }

    // for script

    private void loadScriptGlobals() {
        globalVariables = new HashMap<>();
        publicSymbols = new HashMap<>();
        String packageName = this.packageName();
        for (Symbol topLevelSymbol : this.getTopLevelSymbols()) {
            if (topLevelSymbol.getKind().isVariable()) {
                if (topLevelSymbol.getDeclarator() == Declarator.GLOBAL) {
                    globalVariables.put(topLevelSymbol.getName(), (VariableSymbol) topLevelSymbol);
                }
            } else if (topLevelSymbol.getKind() == ZenSymbolKind.FUNCTION) {
                publicSymbols.computeIfAbsent(packageName, i -> new ArrayList<>())
                    .add(topLevelSymbol);
            } else if (topLevelSymbol.getKind().isClass()) {
                String className = ((ClassSymbol) topLevelSymbol).getQualifiedName();
                publicSymbols.computeIfAbsent(className, i -> new ArrayList<>())
                    .add(topLevelSymbol);
            }

        }
    }

    public Map<String, VariableSymbol> getGlobalVariables() {
        if (globalVariables == null) {
            loadScriptGlobals();
        }
        return globalVariables;
    }

    public Map<String, List<Symbol>> getPublicSymbols() {
        if (globalVariables == null) {
            loadScriptGlobals();
        }
        return publicSymbols;
    }

}
