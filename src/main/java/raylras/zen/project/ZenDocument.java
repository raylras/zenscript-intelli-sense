package raylras.zen.project;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;
import raylras.zen.semantic.DeclarationResolver;
import raylras.zen.semantic.ReferenceResolver;
import raylras.zen.semantic.AnnotatedTree;
import raylras.zen.util.CommonUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

public class ZenDocument {

    public static final String DOCUMENT_EXTENSION = ".zs";

    private Path documentPath;
    private ZenProject project;
    private AnnotatedTree annotatedTree;
    private TokenStream tokenStream;

    ZenDocument(Path documentPath, ZenProject project) {
        this.documentPath = documentPath;
        this.project = project;
    }

    public Path getDocumentPath() {
        return this.documentPath;
    }

    public ZenProject getProject() {
        return this.project;
    }

    public AnnotatedTree getAnnotatedTree() {
        if (annotatedTree == null) {
            try {
                parse(new FileReader(documentPath.toFile()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return this.annotatedTree;
    }

    public TokenStream getTokenStream() {
        return tokenStream;
    }

    void parse(Reader source) {
        try {
            String fileName = CommonUtils.getFileName(documentPath);
            CharStream charStream = CharStreams.fromReader(source, fileName);
            ZenScriptLexer lexer = new ZenScriptLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ZenScriptParser parser = new ZenScriptParser(tokens);

            this.tokenStream = tokens;
            this.annotatedTree = new AnnotatedTree(fileName, parser.compilationUnit());
            DeclarationResolver declResolver = new DeclarationResolver(annotatedTree);
            ParseTreeWalker.DEFAULT.walk(declResolver, annotatedTree.getParseTree());
            ReferenceResolver refResolver = new ReferenceResolver(annotatedTree);
            ParseTreeWalker.DEFAULT.walk(refResolver, annotatedTree.getParseTree());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
