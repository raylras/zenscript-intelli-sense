package raylras.zen.ast;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;
import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;

class ASTBuilderTest {

    private static final String SOURCE =
            "var func as function(int,int)int = function(a as int = 0, b as int) as int {" +
                    "var integer as int = 1;" +
                    "var map as string[int] = {1:\"1\", 2:\"2\"};" +
                    "a.b[c]*d();" +
            "};";

    @Test
    void compilationUnit() {
        CharStream charStream = CharStreams.fromString(SOURCE);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);
        ZenScriptParser.CompilationUnitContext cst = parser.compilationUnit();
        ASTBuilder astBuilder = new ASTBuilder("test source");
        ParseTreeWalker.DEFAULT.walk(astBuilder, cst);
        CompilationUnitNode compilationUnitNode = astBuilder.compilationUnit();
    }

}