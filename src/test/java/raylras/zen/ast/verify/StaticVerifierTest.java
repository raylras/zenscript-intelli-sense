package raylras.zen.ast.verify;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import raylras.zen.Environment;
import raylras.zen.antlr.ZenScriptLexer;
import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.antlr.ZenScriptVisitor;
import raylras.zen.ast.ScriptNode;
import raylras.zen.verify.StaticVerifier;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class StaticVerifierTest {

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

    StaticVerifierTest() throws IOException {}

    @Test
    void test() {
        new StaticVerifier(new Environment(null)).visitScript(ast);
    }


}