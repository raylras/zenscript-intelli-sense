package raylras.zen.ast;

import org.antlr.v4.runtime.*;
import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.Comparator;
import java.util.List;

public final class SourceUnit implements Comparable<SourceUnit> {

    public static final String ZEN_SCRIPT_FILE_EXTENSION = ".zs";
    public static final Comparator<SourceUnit> PRIORITY_COMPARATOR = Comparator.comparingInt(SourceUnit::getPriority);
    private final URI uri;
    private ZenScriptParser.CompilationUnitContext cst;
    private CompilationUnitNode ast;
    private List<String> preprocessors;

    public SourceUnit(URI uri) {
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    public ZenScriptParser.CompilationUnitContext getCst() {
        return cst;
    }

    public CompilationUnitNode getAst() {
        return ast;
    }

    public List<String> getPreprocessors() {
        return preprocessors;
    }

    public int getPriority() {
        return preprocessors.stream()
                .filter(prep -> prep.startsWith("#priority"))
                .findFirst()
                .map(prep -> prep.split(" ")[1])
                .map(Integer::valueOf)
                .orElse(0);
    }

    public void parse(Reader source) {
        try {
            CharStream charStream = CharStreams.fromReader(source, uri == null ? "<unknown>" : uri.toString());
            ZenScriptLexer lexer = new ZenScriptLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ZenScriptParser parser = new ZenScriptParser(tokens);
            this.cst = parser.compilationUnit();

            preprocessors = tokens.getTokens().stream()
                    .filter(token -> token.getChannel() == ZenScriptLexer.PREPROCESSOR_CHANNEL)
                    .map(Token::getText)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compareTo(SourceUnit that) {
        return PRIORITY_COMPARATOR.compare(this, that);
    }

    public static SourceUnit create(URI root, URI absolute) {
        return new SourceUnit(root.relativize(absolute));
    }

    public static SourceUnit create(URI relative) {
        return new SourceUnit(relative);
    }

}
