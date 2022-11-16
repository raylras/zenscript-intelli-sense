package raylras.zen.project;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import raylras.zen.ast.ASTBuilder;
import raylras.zen.ast.CompilationUnitNode;
import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZenDocument {

    private String uri;
    private ZenProject project;
    private String source;
    private ZenScriptParser.CompilationUnitContext cst;
    private CompilationUnitNode ast;
    private final Lock lock;

    public ZenDocument() {
        this.lock = new ReentrantLock();
    }

    public String getUri() {
        return uri;
    }

    public ZenProject getProject() {
        return project;
    }

    public String getSource() {
        return source;
    }

    public ZenScriptParser.CompilationUnitContext getCst() {
        lock.lock();
        try {
            return cst;
        } finally {
            lock.unlock();
        }
    }

    public CompilationUnitNode getAst() {
        lock.lock();
        try {
            return ast;
        } finally {
            lock.unlock();
        }
    }

    public Lock getLock() {
        return lock;
    }

    public void update(String source) {
        lock.lock();
        try {
            this.source = source;
            parse();
            transform();
        } finally {
            lock.unlock();
        }
    }

    private void parse() {
        CharStream charStream = CharStreams.fromString(source);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);
        this.cst = parser.compilationUnit();
    }

    private void transform() {
        ASTBuilder astBuilder = new ASTBuilder(uri);
        ParseTreeWalker.DEFAULT.walk(astBuilder, cst);
        this.ast = astBuilder.compilationUnit();
    }

}
