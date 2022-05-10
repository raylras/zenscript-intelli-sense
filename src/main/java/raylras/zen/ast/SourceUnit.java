package raylras.zen.ast;

import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.verify.ErrorCollector;

import java.io.File;
import java.net.URI;

public class SourceUnit {

    private File source;
    private ErrorCollector errorCollector;
    private ZenScriptParser.ScriptUnitContext cst;
    private ScriptNode ast;

    public SourceUnit(File source) {
        this.source = source;
    }

    public URI getURI() {
        return source.toURI();
    }

    public ErrorCollector getErrorCollector() {
        return errorCollector;
    }

    public ZenScriptParser.ScriptUnitContext getCst() {
        return cst;
    }

    public ScriptNode getAst() {
        return ast;
    }

}
