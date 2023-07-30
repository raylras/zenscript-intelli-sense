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
import java.util.stream.Collectors;

public class CompilationUnit {

    public static final String ZS_FILE_EXTENSION = ".zs";
    public static final String DZS_FILE_EXTENSION = ".d.zs";

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

    public Scope lookupScope(ParseTree lookupNode) {
        ParseTree node = lookupNode;
        while (node != null) {
            Scope scope = scopeProp.get(node);
            if (scope != null) {
                return scope;
            }
            node = node.getParent();
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

    public <T extends Symbol> T getSymbol(ParseTree node, Class<T> clazz) {
        Symbol symbol = symbolProp.get(node);
        if (clazz.isInstance(symbol)) {
            return clazz.cast(symbol);
        } else {
            return null;
        }
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

    public <T> T accept(Visitor<T> visitor) {
        if (parseTree != null) {
            return parseTree.accept(visitor);
        } else {
            return null;
        }
    }

    public void accept(Listener listener) {
        if (parseTree != null) {
            ParseTreeWalker.DEFAULT.walk(listener, parseTree);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(path);
    }

}
