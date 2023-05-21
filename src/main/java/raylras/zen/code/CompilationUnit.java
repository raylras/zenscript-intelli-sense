package raylras.zen.code;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.resolve.DefinitionResolver;
import raylras.zen.code.type.resolve.NameResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.service.LibraryService;
import raylras.zen.service.TypeService;
import raylras.zen.util.ParseTreeProperty;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CompilationUnit {

    public static final String FILE_EXTENSION = ".zs";
    public static final String DZS_FILE_EXTENSION = ".d.zs";

    public final Path path;
    public final CompilationContext context;
    private final ParseTreeProperty<Scope> scopeProp = new ParseTreeProperty<>();
    private final ParseTreeProperty<Symbol> symbolProp = new ParseTreeProperty<>();
    private final Map<String, ClassType> classTypes = new HashMap<>();
    public ParseTree parseTree;
    public CommonTokenStream tokenStream;

    private final TypeService typeService;
    private final LibraryService libraryService;

    public CompilationUnit(Path path, CompilationContext context, TypeService typeService, LibraryService libraryService) {
        this.path = path;
        this.context = context;
        this.typeService = typeService;
        this.libraryService = libraryService;
    }

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


    public <T extends Symbol> T lookupSymbol(ParseTree node) {
        String name = new NameResolver().resolve(node);
        Scope scope = lookupScope(node);
        Symbol symbol = null;
        while (scope != null) {
            symbol = scope.getSymbol(name);
            if (symbol != null)
                break;
            scope = scope.parent;
        }
        if (symbol == null)
            symbol = context.lookupGlobal(name);
        return (T) symbol;
    }

    public Scope getScope(ParseTree node) {
        return scopeProp.get(node);
    }

    public void putScope(ParseTree node, Scope scope) {
        scopeProp.put(node, scope);
    }

    public <T extends Symbol> T getSymbol(ParseTree node) {
        return (T) symbolProp.get(node);
    }

    public void putSymbol(ParseTree node, Symbol symbol) {
        symbolProp.put(node, symbol);
    }

    public void putClassType(String name, ClassType type) {
        if (classTypes.containsKey(name)) {
            throw new IllegalStateException("Class " + name + " already exists");
        }
        classTypes.put(name, type);
    }

    public ClassType lookupClassType(String qualifiedName) {
        return classTypes.get(qualifiedName);
    }

    public LibraryService libraryService() {
        return libraryService;
    }

    public TypeService typeService() {
        return typeService;
    }

    public Collection<Scope> getScopes() {
        return scopeProp.getProperties();
    }

    public Collection<Symbol> getSymbols() {
        return symbolProp.getProperties();
    }

    public Collection<Symbol> getTopLevelSymbols() {
        return getScope(parseTree).symbols;
    }

    public void load(CharStream charStream) {
        parse(charStream);
    }

    private void parse(CharStream charStream) {
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        tokenStream = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokenStream);
        parser.removeErrorListeners();
        parseTree = parser.compilationUnit();


        new DefinitionResolver(this, tokenStream).resolve();
    }


    public boolean isDzs() {
        return path.toString().endsWith(DZS_FILE_EXTENSION);
    }


    public String relativePath() {
        Path root = context.compilationRoot;
        String scriptPackage = StreamSupport.stream(root.relativize(path).spliterator(), false)
            .map(Path::toString)
            .collect(Collectors.joining("."));


        return "scripts." + scriptPackage.substring(0, scriptPackage.length() - 2);
    }

}
