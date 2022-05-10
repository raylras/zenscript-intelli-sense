package raylras.zen.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import raylras.zen.ast.IdentifierNode;
import raylras.zen.ast.ScriptNode;
import raylras.zen.ast.ZenScriptBaseVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

class ASTBuilderTest {

    static class TestVisitor extends ZenScriptBaseVisitor {
        @Override
        public void visitIdentifier(IdentifierNode node) {
            System.out.println("\t" + node.getName()); // print out the identifier
            super.visitIdentifier(node);
        }
    }

    @Test
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
        ScriptNode scriptNode = astBuilder.visitScriptUnit(cst, script.toFile().toURI());

        scriptNode.accept(new TestVisitor());

        // String json = new GsonBuilder().setPrettyPrinting().create().toJson(scriptNode);
        // System.out.println(json);
    }

    @Test
    void visitScripts() {
        Path scripts = Paths.get("src/test/resources/scripts");

        // Traverse all "*.zs" under "scripts"
        try (Stream<Path> pathStream = Files.find(scripts, 10, (path, attributes) -> path.getFileName().toString().matches(".*\\.zs"))) {
            pathStream.forEach(path -> {
                try {
                    System.out.println(path);

                    // ANTLR
                    CharStream charStream = CharStreams.fromPath(path);
                    ZenScriptLexer lexer = new ZenScriptLexer(charStream);
                    CommonTokenStream tokens = new CommonTokenStream(lexer);
                    ZenScriptParser parser = new ZenScriptParser(tokens);

                    // get a CST form antlr
                    ZenScriptParser.ScriptUnitContext scriptContext = parser.scriptUnit();

                    // get an AST
                    ASTBuilder astBuilder = new ASTBuilder();
                    ScriptNode scriptNode = astBuilder.visitScriptUnit(scriptContext, path.toFile().toURI());

                    scriptNode.accept(new TestVisitor());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}