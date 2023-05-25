package raylras.zen.code;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.resolve.DeclarationResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.ParseTreeProperty;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collection;

public class CompilationUnit {

    public static final String FILE_EXTENSION = ".zs";
    public static final String DZS_FILE_EXTENSION = ".d.zs";

    public final Path path;
    public final CompilationEnvironment env;
    public CommonTokenStream tokenStream;
    public ParseTree parseTree;

    private final ParseTreeProperty<Scope> scopeProp = new ParseTreeProperty<>();
    private final ParseTreeProperty<Symbol> symbolProp = new ParseTreeProperty<>();

    public CompilationUnit(Path path, CompilationEnvironment env) {
        this.path = path;
        this.env = env;
    }

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

    public void putScope(ParseTree node, Scope scope) {
        scopeProp.put(node, scope);
    }

    public Symbol getSymbol(ParseTree node) {
        return symbolProp.get(node);
    }

    public void putSymbol(ParseTree node, Symbol symbol) {
        symbolProp.put(node, symbol);
    }

    public Collection<Scope> getScopes() {
        return scopeProp.values();
    }

    public Collection<Symbol> getSymbols() {
        return symbolProp.values();
    }

    public Collection<Symbol> getTopLevelSymbols() {
        return getScope(parseTree).getSymbols();
    }

    public boolean isDzs() {
        return path.toString().endsWith(DZS_FILE_EXTENSION);
    }

    public void load() {
        try {
            load(CharStreams.fromPath(path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(String source) {
        load(CharStreams.fromString(source, String.valueOf(path)));
    }

    public void load(CharStream charStream) {
        parse(charStream);
        new DeclarationResolver().resolve(this);
    }

    public void parse(CharStream charStream) {
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        tokenStream = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokenStream);
        parser.removeErrorListeners();
        parseTree = parser.compilationUnit();
    }

}
