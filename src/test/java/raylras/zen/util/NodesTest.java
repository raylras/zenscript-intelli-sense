package raylras.zen.util;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser;

import java.util.stream.Stream;

class NodesTest {

    static TokenStream tokenStream;
    static ZenScriptParser.CompilationUnitContext unit;

    @BeforeAll
    static void beforeAll() {
        CharStream charStream = CharStreams.fromString("var foo = bar.baz; val qux;");
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        tokenStream = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokenStream);
        unit = parser.compilationUnit();
    }

    @ParameterizedTest
    @MethodSource("getNodeAtLineAndColumn")
    void getNodeAtLineAndColumn(String expected, int line, int column) {
        ParseTree node = Nodes.getNodeAtLineAndColumn(unit, line, column);
        Assertions.assertEquals(expected, getText(node));
    }

    static Stream<Arguments> getNodeAtLineAndColumn() {
        return Stream.of(
                Arguments.of(null, -1, -1),
                Arguments.of("var", 0, 0),
                Arguments.of("bar", 0, 13),
                Arguments.of(".", 0, 14),
                Arguments.of("baz", 0, 15),
                Arguments.of(";", 0, 18),
                Arguments.of("<EOF>", 0, 28)
        );
    }

    @ParameterizedTest
    @MethodSource("getPrevTerminal")
    void getPrevTerminal(String expected, int line, int column) {
        ParseTree node = Nodes.getNodeAtLineAndColumn(unit, line, column);
        TerminalNode prev = Nodes.getPrevTerminal(node, tokenStream);
        Assertions.assertEquals(expected, getText(prev));
    }

    public static Stream<Arguments> getPrevTerminal() {
        return Stream.of(
                Arguments.of(null, 0, 0),
                Arguments.of("bar", 0, 14),
                Arguments.of(";", 0, 19)
        );
    }

    private static String getText(ParseTree node) {
        return node == null ? null : node.getText();
    }

}