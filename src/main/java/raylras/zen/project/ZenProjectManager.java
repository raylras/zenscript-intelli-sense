package raylras.zen.project;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import raylras.zen.langserver.LanguageServerContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ZenProjectManager {

    private final Map<Path, ZenProject> projects;

    private ZenProjectManager(LanguageServerContext serverContext) {
        this.projects = new HashMap<>();
    }

    public static ZenProjectManager getInstance(LanguageServerContext serverContext) {
        ZenProjectManager projectManager = serverContext.get(ZenProjectManager.class);
        if (projectManager == null) {
            projectManager = new ZenProjectManager(serverContext);
            serverContext.put(ZenProjectManager.class, projectManager);
        }

        return projectManager;
    }

    public Optional<ZenProject> getProject(Path path) {
        Path projectRoot = findProjectRoot(path);
        if (projectRoot == null) {
            return Optional.empty();
        }
        if (projects.containsKey(projectRoot)) {
            return Optional.of(projects.get(projectRoot));
        } else {
            return Optional.of(buildProject(projectRoot));
        }
    }

    private ZenProject buildProject(Path projectRoot) {
        ZenProject project = new ZenProject(projectRoot);
        projects.put(projectRoot, project);
        return project;
    }

    private Path findProjectRoot(Path path) {
        try {
            Path tomlPath = findZenProjectToml(path);
            TomlParseResult toml = Toml.parse(tomlPath);
            Object o = toml.get("project.root");
            Path projectRoot = Paths.get(String.valueOf(tomlPath.getParent()), String.valueOf(o)).normalize();
            if (projectRoot.toFile().exists()) {
                return projectRoot;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse toml: ", e);
        } catch (RuntimeException e) {
            // fallback to find "scripts" folder
            // or single file project
        }
        return null;
    }

    private Path findZenProjectToml(Path path) {
        Path p = path;
        if (!Files.isDirectory(p)) {
            p = p.getParent();
        }
        while (p != null) {
            Path tomlPath = p.resolve("zenproject.toml");
            if (tomlPath.toFile().exists()) {
                return tomlPath;
            }
            p = p.getParent();
        }
        throw new RuntimeException("Project description file not found: " + path);
    }

}
