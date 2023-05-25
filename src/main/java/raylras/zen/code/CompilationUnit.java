package raylras.zen.code;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.resolve.DeclarationResolver;
import raylras.zen.code.symbol.*;
import raylras.zen.code.scope.Scope;
import raylras.zen.service.LibraryService;
import raylras.zen.service.TypeService;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.ParseTreeProperty;

import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

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
            if (scope != null)
                return scope;
            n = n.getParent();
        }
        return null;
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
        return getScope(parseTree).getSymbols();
    }

    public void load(CharStream charStream, Instant fileModifyTime) {
        this.symbolProp.clear();
        this.scopeProp.clear();
        this.publicSymbols = null;
        this.globalVariables = null;
        this.fileModifyTime = fileModifyTime;
        parse(charStream);
        new DeclarationResolver(this, scopeProp, symbolProp).resolve();
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
                } else {
                    publicSymbols.computeIfAbsent(packageName + "." + topLevelSymbol.getName(), i -> new ArrayList<>())
                        .add(topLevelSymbol);
                }
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
        if (publicSymbols == null) {
            loadScriptGlobals();
        }
        return publicSymbols;
    }

}
