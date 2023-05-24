package raylras.zen.service;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.eclipse.lsp4j.*;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.util.Logger;
import raylras.zen.util.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// this class manages file sync and workspaces
public class FileManager {
    private static final Logger logger = Logger.getLogger("file");

    // mapping to workspace -> script source root;
    private final Map<Path, Set<Path>> workspaceRoots = new HashMap<>();

    private final Map<Path, CompilationEnvironment> scriptSourceRoots = new HashMap<>();

    private final Map<Path, VersionedContent> activeDocuments = new HashMap<>();

    private final TreeMap<Path, SourceInfo> zsSources = new TreeMap<>();


    public static boolean isZSFile(Path path) {
        return path.toString().endsWith(CompilationUnit.FILE_EXTENSION);
    }

    public void initializeWorkspaces(List<WorkspaceFolder> workspaceFolders) {
        workspaceRoots.clear();
        for (CompilationEnvironment value : scriptSourceRoots.values()) {
            value.unload();
        }
        scriptSourceRoots.clear();
        zsSources.clear();
        for (WorkspaceFolder folder : workspaceFolders) {
            addWorkspace(folder);
        }
    }

    public void addWorkspace(WorkspaceFolder workspaceFolder) {
        Path path = Utils.toPath(workspaceFolder.getUri()).toAbsolutePath().normalize();
        if (workspaceRoots.containsKey(path)) {
            logger.warn("Trying to add existing workspace, skipping: %s", path);
            return;
        }

        Set<Path> sourceRoots = inferSourceRoots(path);
        workspaceRoots.put(path, sourceRoots);
        if (!sourceRoots.isEmpty()) {
            for (Path sourceRoot : sourceRoots) {
                addFiles(sourceRoot, getOrCreateEnvironment(sourceRoot));
            }
        }
    }

    public void deleteWorkspace(WorkspaceFolder workspaceFolder) {
        Path path = Utils.toPath(workspaceFolder.getUri()).toAbsolutePath().normalize();
        if (workspaceRoots.containsKey(path)) {
            logger.warn("Workspace %s is not belong to project yet!", path);
            return;
        }

        Set<Path> sourcePaths = workspaceRoots.get(path);

        for (Path sourcePath : sourcePaths) {
            CompilationEnvironment env = scriptSourceRoots.remove(sourcePath);
            if (env != null) {
                env.unload();
            }
        }

        List<Path> toRemove = new ArrayList<>();
        for (Map.Entry<Path, SourceInfo> entry : zsSources.entrySet()) {
            if (sourcePaths.contains(entry.getValue().sourceRoot)) {
                toRemove.add(entry.getKey());
            }
        }
        for (Path file : toRemove) {
            zsSources.remove(file);
        }
    }

    public CompilationEnvironment getOrCreateEnvironment(Path sourceRoot) {
        if (!scriptSourceRoots.containsKey(sourceRoot)) {
            throw new IllegalStateException(sourceRoot + "is not a 'scripts' folder!");
        }
        return scriptSourceRoots.computeIfAbsent(sourceRoot, path -> new CompilationEnvironment(sourceRoot));
    }

    private Set<Path> inferSourceRoots(Path workspaceRoot) {
        Set<Path> scriptSourceRoots = new HashSet<>();
        try {
            Files.walkFileTree(workspaceRoot, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (dir.getFileName().toString().equals("scripts")) {
                        scriptSourceRoots.add(dir);
                        return FileVisitResult.SKIP_SUBTREE; // Skip searching subdirectories
                    }
                    return FileVisitResult.CONTINUE; // Continue searching
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("Found %d source roots in workspace: ", scriptSourceRoots.size());
        return scriptSourceRoots;
    }

    private void addFiles(Path sourceRoot, CompilationEnvironment env) {
        logger.info("Adding files in source root: %s", sourceRoot);
        Instant start = Instant.now();
        AtomicInteger loaded = new AtomicInteger();
        try (Stream<Path> pathStream = Files.walk(sourceRoot)) {
            pathStream.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(CompilationUnit.FILE_EXTENSION))
                .forEach(unitPath -> {
                    CompilationUnit unit = loadCompilationUnit(env, unitPath);
                    loaded.incrementAndGet();
                });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("... loaded %d scripts in source root in %d ms", loaded.get(), Duration.between(start, Instant.now()).toMillis());
    }

    private void scheduleEnvironmentReload(Path sourceRoot) {
        // TODO:
    }

    public void reloadCompilationUnit(Path path) {

        SourceInfo sourceInfo = zsSources.get(path);
        if (sourceInfo == null) {
            logger.error("Could not get compliation unit at: %s", path);
            return;
        }

        CompilationUnit unit = sourceInfo.unit;

        unit.load(charStream(path));

        SourceInfo newSourceInfo = new SourceInfo(
            readModifiedTime(path),
            packageName(sourceInfo.sourceRoot, path),
            sourceInfo.sourceRoot,
            unit
        );
        zsSources.put(path, newSourceInfo);


        if (unit.isDzs()) {
            scheduleEnvironmentReload(sourceInfo.sourceRoot);
        }
    }

    public CompilationUnit loadCompilationUnit(CompilationEnvironment env, Path file) {
        Path sourceRoot = env.getSourceRoot();
        CompilationUnit unit = new CompilationUnit(file, env);
        unit.load(charStream(file));

        SourceInfo sourceInfo = new SourceInfo(
            readModifiedTime(file),
            packageName(sourceRoot, file),
            sourceRoot,
            unit
        );

        zsSources.put(file, sourceInfo);
        if (unit.isDzs()) {
            scheduleEnvironmentReload(sourceRoot);
        }
        return unit;
    }


    public Stream<CompilationUnit> getCompilationUnits(Path sourceRoot) {
        return zsSources.values()
            .stream()
            .filter(it -> Objects.equals(sourceRoot, it.sourceRoot))
            .map(it -> it.unit);
    }

    public CompilationUnit getCompilationUnit(Path path) {
        SourceInfo sourceInfo = zsSources.get(path);
        if (sourceInfo == null) {
            logger.error("Could not get compliation unit at: %s", path);
            return null;
        }
        return sourceInfo.unit;
    }

    public void removeCompilationUnit(Path path) {
        SourceInfo sourceInfo = zsSources.get(path);
        if (sourceInfo == null) {
            logger.warn("Could not remove compliation unit at: %s", path);
            return;
        }
        if (sourceInfo.unit.isDzs()) {
            scheduleEnvironmentReload(sourceInfo.sourceRoot);
        }
        zsSources.remove(path);
    }

    public Path sourceRootOf(Path file) {
        for (Path path : scriptSourceRoots.keySet()) {
            if (Utils.isSubPath(path, file)) {
                return path;
            }
        }
        logger.info("Could not find source root of file: %s", file);
        return null;
    }

    public void externalCreate(Path file) {
        Path sourceRoot = sourceRootOf(file);
        if (sourceRoot == null) {
            logger.warn("Skipping file creation out of source root: %s", file);
            return;
        }
        CompilationEnvironment env = scriptSourceRoots.get(sourceRoot);
        loadCompilationUnit(env, file);

    }

    public void externalChange(Path file) {
        reloadCompilationUnit(file);
    }

    public void externalDelete(Path file) {
        removeCompilationUnit(file);
    }


    public void open(DidOpenTextDocumentParams params) {
        logger.info("opening text document: %s", params.getTextDocument().getUri());
        // TODO: open a file outside workspace
        Path file = Utils.toPath(params.getTextDocument().getUri());
        if (!isZSFile(file))
            return;

        activeDocuments.put(file, new VersionedContent(
            params.getTextDocument().getText(),
            params.getTextDocument().getVersion()
        ));
    }

    public void close(DidCloseTextDocumentParams params) {
        logger.info("closing text document: %s", params.getTextDocument().getUri());
        Path file = Utils.toPath(params.getTextDocument().getUri());
        if (!isZSFile(file))
            return;

        activeDocuments.remove(file);
    }

    public void change(DidChangeTextDocumentParams params) {
        logger.info("editing text document: %s", params.getTextDocument().getUri());
        Path file = Utils.toPath(params.getTextDocument().getUri());
        if (!isZSFile(file))
            return;

        VersionedTextDocumentIdentifier newDoc = params.getTextDocument();
        VersionedContent current = activeDocuments.get(file);

        if (newDoc.getVersion() <= current.version) {
            logger.warn("Ignoring file: %s, change with version %d <= %d", file.getFileName(), newDoc.getVersion(), current.version);
            return;
        }

        StringBuilder content = new StringBuilder(current.content);

        for (TextDocumentContentChangeEvent change : params.getContentChanges()) {
            if (change.getRange() == null) {
                content = new StringBuilder(change.getText());
                continue;
            }
            applyChange(content, change);
        }

        activeDocuments.put(file, new VersionedContent(
            content.toString(),
            newDoc.getVersion()
        ));

        // reload
        reloadCompilationUnit(file);

    }


    public CharStream charStream(Path file) {
        if (!isZSFile(file)) {
            throw new RuntimeException(file + " is not a .zs file");
        }

        if (activeDocuments.containsKey(file)) {
            return CharStreams.fromString(activeDocuments.get(file).content);
        }

        try {
            return CharStreams.fromPath(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String packageName(Path sourceRoot, Path file) {
        if (!isZSFile(file)) {
            throw new RuntimeException(file + " is not a .zs file");
        }
        String scriptPackage = StreamSupport.stream(sourceRoot.relativize(file).spliterator(), false)
            .map(Path::toString)
            .collect(Collectors.joining("."));

        return "scripts." + scriptPackage.substring(0, scriptPackage.length() - 2);

    }

    private Instant readModifiedTime(Path path) {
        try {
            return Files.getLastModifiedTime(path).toInstant();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void applyChange(StringBuilder sourceText, TextDocumentContentChangeEvent change) {
        Range range = change.getRange();
        int start = posToIndex(0, sourceText, range.getStart());
        int end = posToIndex(start, sourceText, range.getEnd());

        sourceText.replace(start, end, change.getText());
    }

    private int posToIndex(int fromIndex, StringBuilder text, Position position) {

        int line = position.getLine();
        int character = position.getCharacter();

        int offset = 0;
        int currentLine = 0;
        int currentIndex = 0;

        while (currentLine < line && currentIndex < text.length()) {
            if (text.charAt(currentIndex) == '\n') {
                currentLine++;
            }
            currentIndex++;
            offset++;
        }

        return offset + character;
    }


    private static class SourceInfo {
        final Instant modified;
        final String packageName;

        final Path sourceRoot;

        final CompilationUnit unit;

        SourceInfo(Instant modified, String packageName, Path sourceRoot, CompilationUnit unit) {
            this.modified = modified;
            this.packageName = packageName;
            this.sourceRoot = sourceRoot;
            this.unit = unit;
        }
    }


    private static class VersionedContent {
        final String content;
        final int version;
        final Instant modified = Instant.now();

        VersionedContent(String content, int version) {
            Objects.requireNonNull(content, "content is null");
            this.content = content;
            this.version = version;
        }
    }
}
