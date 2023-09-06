package raylras.zen.util;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.resolve.DeclarationResolver;
import raylras.zen.langserver.Document;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;

public class Compilations {

    private static final Logger logger = LoggerFactory.getLogger(Compilations.class);

    public static boolean hasDzsUnit(CompilationEnvironment env) {
        for (CompilationUnit unit : env.getUnits()) {
            if (PathUtils.isDzsFile(unit.getPath())) {
                return true;
            }
        }
        return false;
    }

    public static void loadEnv(CompilationEnvironment env) {
        env.getUnitMap().clear();
        // Most of the files under 'env' are correct, allowing for a faster parsing strategy.
        try (Stream<CompilationUnit> unitStream = collectUnits(env)) {
            unitStream.forEach(Compilations::loadUnit);
        }
    }

    private static Stream<CompilationUnit> collectUnits(CompilationEnvironment env) {
        try {
            return Files.walk(env.getRoot())
                    .filter(Files::isRegularFile)
                    .filter(PathUtils::isSourceFile)
                    .map(env::createUnit);
        } catch (Exception e) {
            logger.error("Failed to collect compilation units of env: {}", env, e);
            return Stream.empty();
        }
    }

    public static void loadUnit(CompilationUnit unit) {
        // The source from disk probably has no syntax errors, using the faster parsing strategy.
        try {
            CharStream charStream = CharStreams.fromPath(unit.getPath(), StandardCharsets.UTF_8);
            CommonTokenStream tokenStream = lex(charStream);
            ParseTree parseTree = fastParse(tokenStream);
            unit.setTokenStream(tokenStream);
            unit.setParseTree(parseTree);
            DeclarationResolver.resolveDeclarations(unit);
        } catch (Exception e) {
            logger.error("Failed to load unit: {}", unit, e);
        }
    }

    public static void reload(Document document, String source) {
        if (document.getUnit().isPresent()) {
            loadUnit(document.getUnit().get(), source);
        }
    }

    public static void loadUnit(CompilationUnit unit, String source) {
        // The source from LSP may have some syntax errors, using the default parsing strategy.
        CharStream charStream = CharStreams.fromString(source, String.valueOf(unit.getPath()));
        loadUnit(unit, charStream);
    }

    public static void loadUnit(CompilationUnit unit, CharStream charStream) {
        CommonTokenStream tokenStream = lex(charStream);
        ParseTree parseTree = parse(tokenStream);
        unit.setTokenStream(tokenStream);
        unit.setParseTree(parseTree);
        DeclarationResolver.resolveDeclarations(unit);
    }

    public static CommonTokenStream lex(CharStream charStream) {
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        lexer.removeErrorListeners();
        return new CommonTokenStream(lexer);
    }

    public static ParseTree parse(TokenStream tokenStream) {
        ZenScriptParser parser = new ZenScriptParser(tokenStream);
        parser.removeErrorListeners();
        return parser.compilationUnit();
    }

    public static ParseTree fastParse(TokenStream tokenStream) {
        // The faster parsing strategy, only effective when the source has no syntax errors.
        ZenScriptParser parser = new ZenScriptParser(tokenStream);
        parser.removeErrorListeners();
        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
        parser.setErrorHandler(new BailErrorStrategy());
        try {
            return parser.compilationUnit();
        } catch (ParseCancellationException ignore) {
            parser.reset();
            parser.setErrorHandler(new DefaultErrorStrategy());
            parser.getInterpreter().setPredictionMode(PredictionMode.LL);
            return parser.compilationUnit();
        }
    }

}
