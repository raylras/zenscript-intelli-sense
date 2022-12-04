package raylras.zen.project;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZenDocument {

    private String uri;
    private ZenProject project;
    private String source;
    private ZenScriptParser.CompilationUnitContext cst;
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

    public Lock getLock() {
        return lock;
    }

    private void parse() {
        CharStream charStream = CharStreams.fromString(source);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);
        this.cst = parser.compilationUnit();
    }

}
