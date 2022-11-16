package raylras.zen.project;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ZenProject {

    private final Path projectRoot;
    private final Map<Path, ZenDocument> documents;

    public ZenProject(Path projectRoot) {
        this.projectRoot = projectRoot;
        this.documents = new HashMap<>();
    }

    public Path getProjectRoot() {
        return projectRoot;
    }

    public ZenDocument getDocument(Path path) {
        if (documents.containsKey(path)) {
            return documents.get(path);
        } else {
            ZenDocument document = new ZenDocument();
            documents.put(path, document);
            return document;
        }
    }

    public void addDocument(Path path, ZenDocument document) {
        documents.put(path, document);
    }

}
