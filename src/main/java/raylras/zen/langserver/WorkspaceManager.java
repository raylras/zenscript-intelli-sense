package raylras.zen.langserver;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.util.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceManager {

    private final List<Path> workspaceList = new ArrayList<>();
    private final List<CompilationEnvironment> compilationList = new ArrayList<>();

    public void addWorkspace(Path workspacePath) {
        workspaceList.add(workspacePath);
    }

    public void removeWorkspace(Path workspacePath) {
        workspaceList.remove(workspacePath);
        compilationList.removeIf(env -> Utils.isSubPath(workspacePath, env.root));
    }

    public CompilationUnit getUnit(Path documentPath) {
        CompilationEnvironment env = getEnv(documentPath);
        if (env != null)
            return env.getUnit(documentPath);
        return null;
    }

    public CompilationEnvironment getEnv(Path documentPath) {
        for (CompilationEnvironment env : compilationList) {
            if (Utils.isSubPath(env.root, documentPath))
                return env;
        }
        return null;
    }

    public void checkEnv(Path documentPath) {
        CompilationEnvironment env = getEnv(documentPath);
        if (env == null) {
            createEnv(documentPath);
        } else if (Files.notExists(env.root)) {
            removeEnv(env);
        }
    }

    public void createEnv(Path documentPath) {
        Path compilationRoot = Utils.findUpwards(documentPath, "scripts");
        if (compilationRoot == null || !isPathInWorkspace(compilationRoot))
            compilationRoot = documentPath;
        CompilationEnvironment env = new CompilationEnvironment(compilationRoot);
        env.load();
        compilationList.add(env);
    }

    public void removeEnv(CompilationEnvironment env) {
        compilationList.remove(env);
    }

    private boolean isPathInWorkspace(Path documentPath) {
        for (Path workspace : workspaceList) {
            if (Utils.isSubPath(workspace, documentPath))
                return true;
        }
        return false;
    }

}
