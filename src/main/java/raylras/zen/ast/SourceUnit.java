package raylras.zen.ast;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.jetbrains.annotations.NotNull;
import raylras.zen.antlr.ZenScriptLexer;
import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.control.ErrorCollector;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.List;

public final class SourceUnit implements Comparable<SourceUnit> {

    private final URI uri;
    private ZenScriptParser.ScriptUnitContext cst;
    private ScriptNode ast;
    private final ErrorCollector errorCollector;

    private List<String> preprocessors;
    private int priority = 0;

    public SourceUnit(URI uri, ErrorCollector errorCollector) {
        this.uri = uri;
        this.errorCollector = errorCollector;
    }

    public URI getUri() {
        return uri;
    }

    public ZenScriptParser.ScriptUnitContext getCst() {
        return cst;
    }

    public ScriptNode getAst() {
        return ast;
    }

    public ErrorCollector getErrorCollector() {
        return errorCollector;
    }

    public List<String> getPreprocessors() {
        return preprocessors;
    }

    public int getPriority() {
        return priority;
    }

    public void parse(Reader source) {
        try {
            CharStream charStream = uri == null ? CharStreams.fromReader(source) : CharStreams.fromReader(source, uri.toString());
            ZenScriptLexer lexer = new ZenScriptLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ZenScriptParser parser = new ZenScriptParser(tokens);
            this.cst = parser.scriptUnit();

            preprocessors = tokens.getTokens().stream()
                    .filter(token -> token.getChannel() == ZenScriptLexer.PREPROCESSOR_CHANNEL)
                    .map(Token::getText)
                    .toList();

            priority = preprocessors.stream()
                    .filter(prep -> prep.startsWith("#priority"))
                    .findFirst()
                    .map(prep -> prep.split(" ")[1])
                    .map(Integer::valueOf)
                    .orElse(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void convert(ASTBuilder builder) {
        ast = builder.lower(uri, cst);
    }

    @Override
    public int compareTo(@NotNull SourceUnit that) {
        return that.getPriority() - this.getPriority();
    }

}
