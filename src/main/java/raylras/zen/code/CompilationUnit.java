package raylras.zen.code;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import raylras.zen.code.annotation.Annotation;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.PathUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class CompilationUnit implements SymbolProvider {

    public static final String ZS_FILE_EXTENSION = ".zs";
    public static final Set<String> DZS_FILE_EXTENSIONS = Set.of(".d.zs", ".dzs");

    private final Path path;
    private final CompilationEnvironment env;
    private final String qualifiedName;
    private final Map<ParseTree, Scope> scopeProperties = new IdentityHashMap<>();
    private final Map<ParseTree, Symbol> symbolProperties = new IdentityHashMap<>();

    private CommonTokenStream tokenStream;
    private ParseTree parseTree;

    public CompilationUnit(Path path, CompilationEnvironment env) {
        this.path = path;
        this.env = env;
        this.qualifiedName = extractClassName(getRelativePath());
    }

    public Scope lookupScope(ParseTree lookupCst) {
        ParseTree cst = lookupCst;
        while (cst != null) {
            Scope scope = scopeProperties.get(cst);
            if (scope != null) {
                return scope;
            }
            cst = cst.getParent();
        }
        return null;
    }

    public Scope getScope(ParseTree cst) {
        return scopeProperties.get(cst);
    }

    public void addScope(Scope scope) {
        scopeProperties.put(scope.getCst(), scope);
    }

    public Symbol getSymbol(ParseTree cst) {
        return symbolProperties.get(cst);
    }

    public <T extends Symbol> T getSymbol(ParseTree cst, Class<T> clazz) {
        Symbol symbol = symbolProperties.get(cst);
        if (clazz.isInstance(symbol)) {
            return clazz.cast(symbol);
        } else {
            return null;
        }
    }

    public void putSymbol(ParseTree cst, Symbol symbol) {
        symbolProperties.put(cst, symbol);
    }

    public Collection<Scope> getScopes() {
        return scopeProperties.values();
    }

    @Override
    public Collection<Symbol> getSymbols() {
        return symbolProperties.values();
    }

    public List<Symbol> getTopLevelSymbols() {
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

    public String getQualifiedName() {
        return qualifiedName;
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

    public boolean isGenerated() {
        return PathUtils.isSubPath(env.getGeneratedRoot(), path);
    }

    public Path getRelativePath() {
        Path root;
        if (isGenerated()) {
            root = env.getGeneratedRoot();
        } else {
            root = env.getRoot().getParent();
        }
        return root.relativize(path);
    }

    @Override
    public String toString() {
        return String.valueOf(path);
    }

    private static String extractClassName(Path path) {
        String classNameWithSlash = path.toString().replace(File.separatorChar, '/');

        // trim extension
        int lastDot = classNameWithSlash.lastIndexOf('.');
        if (lastDot > 0) {
            classNameWithSlash = classNameWithSlash.substring(0, lastDot);
        }

        classNameWithSlash = classNameWithSlash.replace(".", "_");
        classNameWithSlash = classNameWithSlash.replace(" ", "_");

        return classNameWithSlash.replace('/', '.');
    }

}
