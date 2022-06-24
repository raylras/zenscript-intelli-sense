package raylras.zen.ast;

import raylras.zen.control.ErrorCollector;
import raylras.zen.util.URIUtils;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public final class CompileUnit {

    private static final String ZEN_SCRIPT_FILE_EXTENSION = ".zs";

    private final URI root;
    private final Map<URI,SourceUnit> sourceUnits = new HashMap<>();
    private final ErrorCollector errorCollector;
    private final ASTBuilder builder;

    public CompileUnit(URI root) {
        this.root = root;
        this.errorCollector = new ErrorCollector();
        this.builder = new ASTBuilder(this);
    }

    public URI getRoot() {
        return root;
    }

    public void compile(String uri, Reader source) {
        compile(URIUtils.create(uri), source);
    }

    public void compile(URI uri, Reader source) {
        SourceUnit sourceUnit = sourceUnits.get(uri);
        if (sourceUnit == null) {
            sourceUnit = new SourceUnit(uri, errorCollector);
            sourceUnits.put(uri, sourceUnit);
        }
        try {
            sourceUnit.parse(source);
            sourceUnit.convert(builder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void refresh(String uri) {
        refresh(URIUtils.create(uri));
    }

    public void refresh(URI uri) {
        SourceUnit sourceUnit = sourceUnits.get(uri);
        try {
            sourceUnit.parse(new FileReader(Paths.get(uri).toFile()));
            sourceUnit.convert(builder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ScriptNode> getScriptNodes() {
        return sourceUnits.values().stream().sorted(SourceUnit::compareTo).map(SourceUnit::getAst).collect(Collectors.toList());
    }

    public ScriptNode getScriptNode(String uri) {
        return getScriptNode(URIUtils.create(uri));
    }

    public ScriptNode getScriptNode(URI uri) {
        return sourceUnits.get(uri).getAst();
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
                    URI uri = current.toURI();
                    compileUnit.sourceUnits.put(uri, new SourceUnit(uri, compileUnit.errorCollector));
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
                sourceUnit.parse(new FileReader(Paths.get(uri).toFile()));
                sourceUnit.convert(compileUnit.builder);
            } catch (Throwable ignored) {
            }
        }

        return compileUnit;
    }

}
