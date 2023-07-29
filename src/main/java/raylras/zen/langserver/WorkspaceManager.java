package raylras.zen.langserver;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.util.Logger;
import raylras.zen.util.PathUtils;
import raylras.zen.util.l10n.L10N;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceManager {

    private static final Logger logger = Logger.getLogger("workspace");

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
        env.load();
        if (!hasDzsFile(env)) {
            logger.logInfo("Could not find *.d.zs files in project environment: {0}", env);
            logger.showInfo(L10N.getString("environment.dzs_not_found"));
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

    private boolean hasDzsFile(CompilationEnvironment env) {
        for (CompilationUnit unit : env.getUnits()) {
            if (unit.isDzs()) {
                return true;
            }
        }
        return false;
    }

}
