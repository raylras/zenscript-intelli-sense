package raylras.zen.code;

import com.google.common.collect.ImmutableSet;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import raylras.zen.code.annotation.Annotation;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.ParseTreeProperty;
import raylras.zen.util.PathUtils;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class CompilationUnit {

    public static final String ZS_FILE_EXTENSION = ".zs";
    public static final Set<String> DZS_FILE_EXTENSIONS = ImmutableSet.of(".d.zs", ".dzs");

    private final Path path;
    private final CompilationEnvironment env;
    private final String zsPackage;
    private final ParseTreeProperty<Scope> scopeProp = new ParseTreeProperty<>();
    private final ParseTreeProperty<Symbol> symbolProp = new ParseTreeProperty<>();

    private CommonTokenStream tokenStream;
    private ParseTree parseTree;

    public CompilationUnit(Path path, CompilationEnvironment env) {
        this.path = path;
        this.env = env;
        this.zsPackage = processPackage();
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

    public void putSymbol(ParseTree cst, Symbol symbol) {
        symbolProp.put(cst, symbol);
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

    public String getPackage() {
        return zsPackage;
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

    private String processPackage() {
        StringBuilder sb = new StringBuilder();
        if (PathUtils.isZsFile(path)) {
            Path current = path;
            List<String> strings = new ArrayList<>();
            do {
                strings.add(current.getFileName().toString());
                current = current.getParent();
            } while (!"scripts".equals(current.getFileName().toString()));
            sb.append("scripts.");
            for (int i = strings.size() - 1; i >= 0; i--) {
                sb.append(strings.get(i));
                if (i != 0) {
                    sb.append('.');
                }
            }
            // remove .zs suffix
            return sb.substring(0, sb.length() - 3);
        } else if (PathUtils.isDzsFile(path)) {
            Path current = path.getParent();
            List<String> strings = new ArrayList<>();
            while (!"generated".equals(current.getFileName().toString())) {
                strings.add(current.getFileName().toString());
                current = current.getParent();
            }
            for (int i = strings.size() - 1; i >= 0; i--) {
                sb.append(strings.get(i));
                if (i != 0) {
                    sb.append('.');
                }
            }
            return sb.toString();
        } else {
            return sb.toString();
        }
    }

}
