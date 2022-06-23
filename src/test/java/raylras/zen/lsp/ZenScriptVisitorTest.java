package raylras.zen.lsp;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import raylras.zen.antlr.ZenScriptLexer;
import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.ast.ASTBuilder;
import raylras.zen.ast.ScriptNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

class ZenScriptVisitorTest {

//    @Test
    void visitScriptUnit() throws IOException {
        Path script = Paths.get("src/test/resources/TestScript.zs");

        // ANTLR
        CharStream charStream = CharStreams.fromPath(script);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);

        // get a CST form antlr
        ZenScriptParser.ScriptUnitContext cst = parser.scriptUnit();

        // get an AST
        ASTBuilder astBuilder = new ASTBuilder();
        ScriptNode scriptNode = astBuilder.lower(script.toFile().toURI(), cst);

        ZenScriptVisitor astVisitor = new ZenScriptVisitor();
        astVisitor.visit(scriptNode);
    }

//    @Test
    void visitScripts() {
        Path scripts = Paths.get("src/test/resources/scripts");
        ZenScriptVisitor visitor = new ZenScriptVisitor();

        // Traverse all "*.zs" under "scripts"
        try (Stream<Path> pathStream = Files.find(scripts, 10, (path, attributes) -> path.getFileName().toString().matches(".*\\.zs"))) {
            pathStream.forEach(path -> {
                try {
                    // System.out.println(path.toUri());

                    // ANTLR
                    CharStream charStream = CharStreams.fromPath(path);
                    ZenScriptLexer lexer = new ZenScriptLexer(charStream);
                    CommonTokenStream tokens = new CommonTokenStream(lexer);
                    ZenScriptParser parser = new ZenScriptParser(tokens);

                    // get a CST form antlr
                    ZenScriptParser.ScriptUnitContext scriptContext = parser.scriptUnit();

                    // get an AST
                    ASTBuilder astBuilder = new ASTBuilder();
                    ScriptNode scriptNode = astBuilder.lower(path.toUri(), scriptContext);

                    scriptNode.accept(visitor);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}