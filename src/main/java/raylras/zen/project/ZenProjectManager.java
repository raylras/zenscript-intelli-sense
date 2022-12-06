package raylras.zen.project;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import raylras.zen.langserver.LanguageServerContext;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ZenProjectManager {

    private final Map<Path, ZenProject> projects;
    private final LanguageServerContext serverContext;

    private ZenProjectManager(LanguageServerContext serverContext) {
        this.projects = new HashMap<>();
        this.serverContext = serverContext;
    }

    public static ZenProjectManager getInstance(LanguageServerContext serverContext) {
        ZenProjectManager projectManager = serverContext.get(ZenProjectManager.class);
        if (projectManager == null) {
            projectManager = new ZenProjectManager(serverContext);
            serverContext.put(ZenProjectManager.class, projectManager);
        }
        return projectManager;
    }

    public void openDocument(Path documentPath) {
        if (getProject(documentPath).isPresent()) {
            return;
        }
        buildProject(findProjectRoot(documentPath));
    }

    public void updateDocument(Path documentPath, Reader source) {
        getDocument(documentPath).ifPresent(document -> document.parse(source));
    }

    public Optional<ZenDocument> getDocument(Path documentPath) {
        return getProject(documentPath).map(project -> project.getDocument(documentPath));
    }

    public Optional<ZenProject> getProject(Path documentPath) {
        for (ZenProject project : projects.values()) {
            if (documentPath.toString().startsWith(project.getProjectRoot().toString())) {
                return Optional.of(project);
            }
        }
        return Optional.empty();
    }

    private void buildProject(Path projectRoot) {
        ZenProject project = new ZenProject(projectRoot);
        // build all source files
        try (Stream<Path> paths = Files.walk(projectRoot)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(ZenDocument.DOCUMENT_EXTENSION))
                    .forEach(project::buildDocument);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        projects.put(projectRoot, project);
    }

    private Path findProjectRoot(Path documentPath) {
        try {
            Path tomlPath = findZenProjectToml(documentPath);
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
