package raylras.zen.antlr;

import com.google.gson.GsonBuilder;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import raylras.zen.ast.ScriptNode;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class ZenScriptVisitorTest {

    @Test
    void visitScript() throws IOException {
        Path path = Paths.get("src/test/resources/ZenScriptVisitorTest.zs");

        // ANTLR
        CharStream charStream = CharStreams.fromPath(path);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);

        // get a CST form antlr
        ZenScriptParser.ScriptContext cst = parser.script();

        // get an AST
        ScriptNode ast = new ZenScriptVisitor().visitScript(cst);

        String json = new GsonBuilder().setPrettyPrinting().create().toJson(ast);


    }

}
