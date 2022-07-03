package raylras.zen.ast;

import raylras.zen.control.ErrorCollector;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public final class CompileUnit {

    private static final String ZEN_SCRIPT_FILE_EXTENSION = ".zs";

    private final URI root;
    private final Map<URI,SourceUnit> sourceUnits = new HashMap<>();
    private final ErrorCollector errorCollector;
    private final ASTBuilder builder;

    public CompileUnit(URI root) {
        this.root = root;
        this.errorCollector = new ErrorCollector();
        this.builder = new ASTBuilder();
    }

    public URI getRoot() {
        return root;
    }

    public void compile(String absolute, Reader source) {
        compile(URI.create(absolute), source);
    }

    public void compile(URI absolute, Reader source) {
        SourceUnit sourceUnit = getSourceUnit(absolute);
        if (sourceUnit == null) {
            sourceUnit = SourceUnit.create(root, absolute, errorCollector);
            addSourceUnit(sourceUnit);
        }
        try {
            sourceUnit.parse(source);
            sourceUnit.convert(builder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void refresh(String absolute) {
        refresh(URI.create(absolute));
    }

    public void refresh(URI absolute) {
        SourceUnit sourceUnit = getSourceUnit(absolute);
        try {
            sourceUnit.parse(new FileReader(Paths.get(absolute).toFile()));
            sourceUnit.convert(builder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ScriptNode> getScriptNodes() {
        return sourceUnits.values().stream()
                .map(SourceUnit::getAst)
                .filter(Objects::nonNull)
                .toList();
    }

    public ScriptNode getScriptNode(String absolute) {
        return getScriptNode(URI.create(absolute));
    }

    public ScriptNode getScriptNode(URI absolute) {
        return getSourceUnit(absolute).getAst();
    }

    private SourceUnit getSourceUnit(URI absolute) {
        return sourceUnits.get(root.relativize(absolute));
    }

    public static CompileUnit fromPath(Path root) {
        CompileUnit compileUnit = new CompileUnit(root.toUri());

        // find all "*.zs" file using BFS
        Queue<File> queue = new ArrayDeque<>();
        queue.add(root.toFile());
        while (!queue.isEmpty()) {
            File current = queue.poll();
            if (current.isFile()) {
                if (current.getName().endsWith(ZEN_SCRIPT_FILE_EXTENSION)) {
                    compileUnit.addSourceUnit(SourceUnit.create(root.toUri(), current.toURI(), compileUnit.errorCollector));
                }
            } else {
                File[] listFiles = current.listFiles();
                if (listFiles == null) continue;
                queue.addAll(Arrays.asList(listFiles));
            }
        }

        for (Map.Entry<URI, SourceUnit> entry : compileUnit.sourceUnits.entrySet()) {
            URI uri = entry.getKey();
            SourceUnit sourceUnit = entry.getValue();
            try {
                sourceUnit.parse(new FileReader(Paths.get(URI.create(compileUnit.root.toString() + uri)).toFile()));
                sourceUnit.convert(compileUnit.builder);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return compileUnit;
    }

    private void addSourceUnit(SourceUnit sourceUnit) {
        sourceUnits.put(sourceUnit.getUri(), sourceUnit);
    }

}
