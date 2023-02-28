package raylras.zen.code;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.parser.ZenScriptParser.CompilationUnitContext;
import raylras.zen.code.resolve.Resolver;
import raylras.zen.code.resolve.ScopeResolver;
import raylras.zen.code.tree.CompilationUnit;
import raylras.zen.code.tree.TreeBuilder;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * Represents a source file.
 */
public class SourceUnit {

    public static final String DOCUMENT_EXTENSION = ".zs";

    public Path path;
    public CompilationContext context;

    public TokenStream tokens;
    public CompilationUnitContext cst;
    public CompilationUnit ast;

    public SourceUnit(Path path, CompilationContext context) {
        this.path = path;
        this.context = context;
    }

    public void updateAll(String source) {
        lex(source);
        parse();
        convert();
        accept(new ScopeResolver());
//        accept(new ReferenceResolver());
//        accept(new TypeResolver());
    }

    public void lex(String source) {
        try {
            CharStream chars;
            if (source != null) {
                chars = CharStreams.fromString(source, path.toString());
            } else {
                chars = CharStreams.fromPath(path, StandardCharsets.UTF_8);
            }
            tokens = new CommonTokenStream(new ZenScriptLexer(chars));
        } catch (Exception e) {
            throw new RuntimeException("Error while lexing source for document: " + path , e);
        }
    }

    public void parse() {
        try {
            ZenScriptParser parser = new ZenScriptParser(tokens);
            parser.removeErrorListeners();
            cst = parser.compilationUnit();
        } catch (Exception e) {
            throw new RuntimeException("Error while building CST for document " + path, e);
        }
    }

    public void convert() {
        try {
            ast = new TreeBuilder().visitCompilationUnit(cst);
            ast.sourceUnit = this;
        } catch (Exception e) {
            throw new RuntimeException("Error while building AST for document " + path, e);
        }
    }

    public void accept(Resolver resolver) {
        try {
            resolver.resolve(this);
        } catch (Exception e) {
            throw new RuntimeException("Error while resolving AST for document " + path, e);
        }
    }

}
