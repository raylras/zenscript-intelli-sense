package raylras.zen.model;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.model.parser.ZenScriptLexer;
import raylras.zen.model.parser.ZenScriptParser;
import raylras.zen.model.resolve.DeclarationResolver;
import raylras.zen.model.scope.Scope;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class Compilations {

    public static boolean isSourceFile(Path path) {
        return isZsFile(path) || isDzsFile(path);
    }

    public static boolean isZsFile(Path path) {
        return path.toString().endsWith(CompilationUnit.ZS_FILE_EXTENSION);
    }

    public static boolean isDzsFile(Path path) {
        return path.toString().endsWith(CompilationUnit.DZS_FILE_EXTENSION);
    }

    public static String extractClassName(Path path) {
        String classNameWithSlash = path.toString().replace(File.separatorChar, '/');

        // trim extension
        int lastDot = classNameWithSlash.lastIndexOf('.');
        if (lastDot > 0) {
            classNameWithSlash = classNameWithSlash.substring(0, lastDot);
        }

        classNameWithSlash = classNameWithSlash.replace(".", "_");
        classNameWithSlash = classNameWithSlash.replace(" ", "_");

        return classNameWithSlash.replace('/', '.');
    }

    public static Optional<Scope> lookupScope(CompilationUnit unit, ParseTree cst) {
        ParseTree t = cst;
        while (t != null) {
            Optional<Scope> scope = unit.getScope(t);
            if (scope.isPresent()) {
                return scope;
            }
            t = t.getParent();
        }
        return Optional.empty();
    }

    public static void load(CompilationEnvironment env) {
        env.clear();
        for (File unitFile : collectUnitFiles(env)) {
            CompilationUnit unit = env.createUnit(unitFile.toPath());
            load(unit);
        }
    }

    public static void load(CompilationUnit unit) {
        try {
            load(unit, CharStreams.fromPath(unit.getPath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load unit: " + unit, e);
        }
    }

    public static void load(CompilationUnit unit, String source) {
        load(unit, CharStreams.fromString(source, unit.getPath().toString()));
    }

    /* Private Methods */

    private static void load(CompilationUnit unit, CharStream charStream) {
        unit.clear();
        CommonTokenStream tokenStream = lex(charStream);
        ParseTree parseTree = parse(tokenStream);
        unit.setTokenStream(tokenStream);
        unit.setParseTree(parseTree);
        DeclarationResolver.resolveDeclarations(unit);
    }

    private static CommonTokenStream lex(CharStream charStream) {
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        lexer.removeErrorListeners();
        return new CommonTokenStream(lexer);
    }

    private static ParseTree parse(TokenStream tokenStream) {
        ZenScriptParser parser = new ZenScriptParser(tokenStream);
        parser.removeErrorListeners();
        // faster but less robust strategy, effective when no syntax errors
        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
        parser.setErrorHandler(new BailErrorStrategy());
        try {
            return parser.compilationUnit();
        } catch (ParseCancellationException ignore) {
            parser.reset();
            // fall back to default strategy, slower but more robust
            parser.getInterpreter().setPredictionMode(PredictionMode.LL);
            parser.setErrorHandler(new DefaultErrorStrategy());
            return parser.compilationUnit();
        }
    }

    private static Set<File> collectUnitFiles(CompilationEnvironment env) {
        Set<File> units = new HashSet<>();
        collect(units, env.getRoot());
        Path generatedRoot = env.getGeneratedRoot();
        if (Files.exists(generatedRoot)) {
            collect(units, generatedRoot);
        }
        return units;
    }

    private static void collect(Set<File> units, Path root) {
        try (Stream<Path> walk = Files.walk(root)) {
            walk.filter(Files::isRegularFile)
                    .filter(Files::isReadable)
                    .filter(Compilations::isSourceFile)
                    .map(Path::toFile)
                    .forEach(units::add);
        } catch (IOException e) {
            throw new RuntimeException("Failed to collect unit files of root: " + root, e);
        }
    }

    /* End Private Methods */

}
