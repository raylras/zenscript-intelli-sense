package raylras.zen.langserver;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.util.Compilations;
import raylras.zen.util.PathUtils;
import raylras.zen.util.l10n.L10N;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WorkspaceManager {

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceManager.class);

    private final List<Path> workspaceList = new ArrayList<>();
    private final List<CompilationEnvironment> compilationList = new ArrayList<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    /**
     * RAII-like lock method
     */
    private AutoCloseable autoLock(Lock autoLock) {
        autoLock.lock();
        return autoLock::unlock;
    }

    public void addWorkspace(Path workspacePath) {
        try (AutoCloseable lk = autoLock(lock.writeLock())) {
            workspaceList.add(workspacePath);
        } catch (Exception ignored) {
            logger.error("failed to add workspace: {}", workspacePath);
        }
    }

    public void removeWorkspace(Path workspacePath) {
        try (AutoCloseable lk = autoLock(lock.writeLock())) {
            workspaceList.remove(workspacePath);
            compilationList.removeIf(env -> PathUtils.isSubPath(workspacePath, env.getRoot()));
        } catch (Exception ignored) {
            logger.error("failed to remove workspace: {}", workspacePath);
        }
    }

    /**
     * since we should keep the lock through the full lifetime of accessing unit
     * it is better to use getEnv to access lock first and then get unit
     */
    @Deprecated
    public CompilationUnit getUnit(Path documentPath) {
        CompilationEnvironment env = getEnv(documentPath);
        if (env != null) {
            try (AutoCloseable lk = autoLock(env.getLock().readLock())) {
                return env.getUnit(documentPath);
            } catch (Exception ignored) {
                logger.error("failed to get unit at: {}", documentPath);
            }
        }
        return null;

    }

    public CompilationEnvironment getEnv(Path documentPath) {
        try (AutoCloseable lk = autoLock(lock.readLock())) {
            for (CompilationEnvironment env : compilationList) {
                if (PathUtils.isSubPath(env.getRoot(), documentPath)) {
                    return env;
                }
            }
        } catch (Exception e) {
            logger.error("failed to get env at: {}", documentPath, e);
        }
        return null;
    }

    public void checkEnv(Path documentPath) {
        CompilationEnvironment env = getEnv(documentPath);
        if (env == null) {
            createEnv(documentPath);
        } else if (Files.notExists(env.getRoot())) {
            removeEnv(env);
        }

    }

    public void createEnv(Path documentPath) {
        try (AutoCloseable lk = autoLock(lock.writeLock())) {
            Path compilationRoot = PathUtils.findUpwards(documentPath, CompilationEnvironment.DEFAULT_ROOT_DIRECTORY);
            if (compilationRoot == null || !isPathInWorkspace(compilationRoot))
                compilationRoot = documentPath;
            CompilationEnvironment env = new CompilationEnvironment(compilationRoot);
            Compilations.loadEnv(env);
            if (!Compilations.hasDzsUnit(env)) {
                logger.info("Could not find *.dzs files in project environment: {}", env);
                ZenLanguageService.showMessage(new MessageParams(MessageType.Info, L10N.getString("environment.dzs_not_found")));
            }
            compilationList.add(env);
        } catch (Exception ignored) {
            logger.error("failed to create env at: {}", documentPath);
        }
    }

    public void removeEnv(CompilationEnvironment env) {
        try (AutoCloseable lk = autoLock(lock.writeLock())) {
            compilationList.remove(env);
        } catch (Exception ignored) {
            logger.error("failed to remove env at: {}", env.getRoot());
        }
    }

    private boolean isPathInWorkspace(Path documentPath) {
        for (Path workspace : workspaceList) {
            if (PathUtils.isSubPath(workspace, documentPath)) {
                return true;
            }
        }
        return false;
    }

}
