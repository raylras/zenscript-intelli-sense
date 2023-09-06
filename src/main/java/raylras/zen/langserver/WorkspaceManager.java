package raylras.zen.langserver;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.util.Compilations;
import raylras.zen.util.PathUtils;
import raylras.zen.util.l10n.L10N;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.stream.Stream;

public class WorkspaceManager {

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceManager.class);

    private final Set<Workspace> workspaceSet = new HashSet<>();

    public Document openAsRead(TextDocumentIdentifier textDocument) {
        Path path = PathUtils.toPath(textDocument.getUri());
        Optional<CompilationUnit> compilationUnit = getUnit(path);
        Optional<ReadLock> readLock = compilationUnit.map(unit -> unit.getEnv().readLock());
        readLock.ifPresent(ReadLock::lock);
        return new Document() {
            @Override
            public Optional<CompilationUnit> getUnit() {
                return compilationUnit;
            }

            @Override
            public void close() {
                readLock.ifPresent(ReadLock::unlock);
            }
        };
    }

    public Document openAsWrite(TextDocumentIdentifier textDocument) {
        Path path = PathUtils.toPath(textDocument.getUri());
        Optional<CompilationUnit> compilationUnit = getUnit(path);
        Optional<WriteLock> writeLock = compilationUnit.map(unit -> unit.getEnv().writeLock());
        writeLock.ifPresent(WriteLock::lock);
        return new Document() {
            @Override
            public Optional<CompilationUnit> getUnit() {
                return compilationUnit;
            }

            @Override
            public void close() {
                writeLock.ifPresent(WriteLock::unlock);
            }
        };
    }

    public void addWorkspace(WorkspaceFolder folder) {
        Path workspacePath = PathUtils.toPath(folder.getUri());
        workspaceSet.add(new Workspace(workspacePath));
    }

    public void removeWorkspace(WorkspaceFolder folder) {
        Path workspacePath = PathUtils.toPath(folder.getUri());
        workspaceSet.removeIf(workspace -> workspace.path().equals(workspacePath));
    }

    public void createEnvIfNotExists(String uri) {
        createEnvIfNotExists(PathUtils.toPath(uri));
    }

    public void createEnv(Path documentPath) {
        getWorkspace(documentPath).ifPresentOrElse(
                workspace -> {
                    Path compilationRoot = PathUtils.findUpwardsOrSelf(documentPath, CompilationEnvironment.DEFAULT_ROOT_DIRECTORY);
                    CompilationEnvironment env = new CompilationEnvironment(compilationRoot);
                    Compilations.loadEnv(env);
                    workspace.add(env);
                    checkDzs(workspace);
                },
                () -> logger.warn("Could not find workspace for document: {}", documentPath)
        );
    }

    public Optional<CompilationEnvironment> getEnv(String documentUri) {
        return getEnv(PathUtils.toPath(documentUri));
    }

    /* Private Methods */

    private void createEnvIfNotExists(Path documentPath) {
        Optional<CompilationEnvironment> env = getEnv(documentPath);
        if (env.isEmpty()) {
            createEnv(documentPath);
        }
    }

    private Optional<Workspace> getWorkspace(Path documentPath) {
        return workspaceSet.stream()
                .filter(workspace -> PathUtils.isSubPath(workspace.path, documentPath))
                .findFirst();
    }

    private Optional<CompilationEnvironment> getEnv(Path documentPath) {
        return getWorkspace(documentPath).stream()
                .flatMap(Workspace::stream)
                .filter(env -> PathUtils.isSubPath(env.getRoot(), documentPath))
                .findFirst();
    }

    private Optional<CompilationUnit> getUnit(Path documentPath) {
        return getEnv(documentPath).map(env -> env.getUnit(documentPath));
    }

    private void checkDzs(Workspace workspace) {
        for (CompilationEnvironment env : workspace) {
            if (!Compilations.hasDzsUnit(env)) {
                logger.info("Could not find *.dzs files in project environment: {}", env);
                ZenLanguageService.showMessage(new MessageParams(MessageType.Info, L10N.getString("environment.dzs_not_found")));
                return;
            }
        }
    }

    /* End Private Methods */

    public record Workspace(Path path, Set<CompilationEnvironment> envSet) implements Iterable<CompilationEnvironment> {
        public Workspace(Path path) {
            this(path, new HashSet<>());
        }

        public void add(CompilationEnvironment env) {
            envSet.add(env);
        }

        public Stream<CompilationEnvironment> stream() {
            return envSet.stream();
        }

        @Override
        public Iterator<CompilationEnvironment> iterator() {
            return envSet.iterator();
        }
    }

}
