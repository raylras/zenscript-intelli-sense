package raylras.zen.util;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.Document;
import raylras.zen.model.parser.ZenScriptLexer;
import raylras.zen.model.parser.ZenScriptParser;
import raylras.zen.model.resolve.DeclarationResolver;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Compilations {

    private static final Logger logger = LoggerFactory.getLogger(Compilations.class);

    public static void loadEnv(CompilationEnvironment env) {
        env.getUnitMap().clear();
        // Most of the files under 'env' are correct, allowing for a faster parsing strategy.
        for (File unitFile : collectUnitFiles(env)) {
            CompilationUnit unit = env.createUnit(unitFile.toPath());
            loadUnit(unit);
        }
    }

    private static Set<File> collectUnitFiles(CompilationEnvironment env) {
        Set<File> units = new HashSet<>();
        List.of(env.getRoot(), env.getGeneratedRoot())
                .forEach(path -> {
                    try(Stream<Path> walk = Files.walk(path)) {
                        walk.filter(Files::isRegularFile)
                                .filter(Files::isReadable)
                                .filter(PathUtils::isSourceFile)
                                .map(Path::toFile)
                                .forEach(units::add);
                    } catch (IOException e) {
                        logger.error("Failed to collect unit files of env: {}", env, e);
                    }
                });
        return units;
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
