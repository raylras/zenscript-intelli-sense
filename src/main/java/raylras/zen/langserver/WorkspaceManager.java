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

public class WorkspaceManager {

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceManager.class);

    private final List<Path> workspaceList = new ArrayList<>();
    private final List<CompilationEnvironment> compilationList = new ArrayList<>();

    public void addWorkspace(Path workspacePath) {
        workspaceList.add(workspacePath);
    }

    public void removeWorkspace(Path workspacePath) {
        workspaceList.remove(workspacePath);
        compilationList.removeIf(env -> PathUtils.isSubPath(workspacePath, env.getRoot()));
    }

    public CompilationUnit getUnit(Path documentPath) {
        CompilationEnvironment env = getEnv(documentPath);
        if (env != null) {
            return env.getUnit(documentPath);
        } else {
            return null;
        }
    }

    public CompilationEnvironment getEnv(Path documentPath) {
        for (CompilationEnvironment env : compilationList) {
            if (PathUtils.isSubPath(env.getRoot(), documentPath)) {
                return env;
            }
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
    }

    public void removeEnv(CompilationEnvironment env) {
        compilationList.remove(env);
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
