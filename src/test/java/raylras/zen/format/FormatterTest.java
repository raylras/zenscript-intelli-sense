package raylras.zen.format;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;
import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;

import static org.junit.jupiter.api.Assertions.*;

class FormatterTest {

    private static final String SOURCE = "var     a      as  float=1.0f   ;var b= 1;";

    @Test
    void getText() {
        CharStream charStream = CharStreams.fromString(SOURCE);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);
        ZenScriptParser.CompilationUnitContext cst = parser.compilationUnit();
        Formatter formatter = new Formatter(tokens);
        ParseTreeWalker.DEFAULT.walk(formatter, cst);
        System.out.println(formatter.getText());
    }

}