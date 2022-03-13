package raylras.zen.ast;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import raylras.zen.antlr.ZenScriptLexer;
import raylras.zen.antlr.ZenScriptParser;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ZenScriptVisitorTest {

    @Test
    void visitScript() throws IOException {
        Path path = Paths.get("src/test/resources/ZenScriptVisitorTest.zs");

        // normal ANTLR
        CharStream charStream = CharStreams.fromPath(path);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);
        ZenScriptParser.ScriptContext ctx = parser.script();

        ScriptNode parsedScript = new ZenScriptVisitor(null).visitScript(ctx, null);

        
    }

}
