package raylras.zen.code;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.resolve.DefResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.ParseTreeProperty;

import java.nio.file.Path;

public class CompilationUnit {

    public static final String FILE_EXTENSION = ".zs";

    public final Path path;
    public final CompilationContext context;

    public ParseTree parseTree;

    public final ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>();
    public final ParseTreeProperty<Symbol> symbols = new ParseTreeProperty<>();


    public CompilationUnit(Path path, CompilationContext context) {
        this.path = path;
        this.context = context;
    }

    public Scope getScope(ParseTree node) {
        ParseTree n = node;
        while (n != null) {
            Scope scope = scopes.get(n);
            if (scope != null) {
                return scope;
            }
            n = n.getParent();
        }
        return null;
    }

    public  <T extends Symbol> T getSymbol(ParseTree node) {
        return (T) symbols.get(node);
    }

    public void load(CharStream charStream) {
        parse(charStream);
        new DefResolver(this).resolve();
    }

    public void parse(CharStream charStream) {
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokenStream);
        parser.removeErrorListeners();
        parseTree = parser.compilationUnit();
    }

}
