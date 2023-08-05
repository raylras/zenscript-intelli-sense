package raylras.zen.code;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import raylras.zen.code.annotation.Annotation;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.ParseTreeProperty;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompilationUnit {

    public static final String ZS_FILE_EXTENSION = ".zs";
    public static final String DZS_FILE_EXTENSION = ".dzs";

    private final Path path;
    private final CompilationEnvironment env;
    private final ParseTreeProperty<Scope> scopeProp = new ParseTreeProperty<>();
    private final ParseTreeProperty<Symbol> symbolProp = new ParseTreeProperty<>();

    private CommonTokenStream tokenStream;
    private ParseTree parseTree;

    public CompilationUnit(Path path, CompilationEnvironment env) {
        this.path = path;
        this.env = env;
    }

    public Scope lookupScope(ParseTree lookupCst) {
        ParseTree cst = lookupCst;
        while (cst != null) {
            Scope scope = scopeProp.get(cst);
            if (scope != null) {
                return scope;
            }
            cst = cst.getParent();
        }
        return null;
    }

    public Scope getScope(ParseTree cst) {
        return scopeProp.get(cst);
    }

    public void addScope(Scope scope) {
        scopeProp.put(scope.getCst(), scope);
    }

    public Symbol getSymbol(ParseTree cst) {
        return symbolProp.get(cst);
    }

    public <T extends Symbol> T getSymbol(ParseTree cst, Class<T> clazz) {
        Symbol symbol = symbolProp.get(cst);
        if (clazz.isInstance(symbol)) {
            return clazz.cast(symbol);
        } else {
            return null;
        }
    }

    public void addSymbol(Symbol symbol) {
        symbolProp.put(symbol.getCst(), symbol);
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

    public List<Annotation> getDeclaredAnnotations() {
        List<Token> annoTokens = tokenStream.getHiddenTokensToRight(0, ZenScriptLexer.PREPROCESSOR_CHANNEL);
        return annoTokens.stream()
                .map(Token::getText)
                .map(Annotation::create)
                .collect(Collectors.toList());
    }

    public Path getPath() {
        return path;
    }

    public CompilationEnvironment getEnv() {
        return env;
    }

    public ParseTree getParseTree() {
        return parseTree;
    }

    public void setParseTree(ParseTree parseTree) {
        this.parseTree = parseTree;
    }

    public CommonTokenStream getTokenStream() {
        return tokenStream;
    }

    public void setTokenStream(CommonTokenStream tokenStream) {
        this.tokenStream = tokenStream;
    }

    public void accept(Visitor<?> visitor) {
        Objects.requireNonNull(visitor);
        parseTree.accept(visitor);
    }

    public void accept(Listener listener) {
        Objects.requireNonNull(listener);
        ParseTreeWalker.DEFAULT.walk(listener, parseTree);
    }

    @Override
    public String toString() {
        return String.valueOf(path);
    }

}
