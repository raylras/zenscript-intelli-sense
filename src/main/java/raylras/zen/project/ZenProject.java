package raylras.zen.project;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ZenProject {

    private Path projectRoot;
    private Map<Path, ZenDocument> documents;

    ZenProject(Path projectRoot) {
        this.projectRoot = projectRoot;
        this.documents = new HashMap<>();
    }

    public Path getProjectRoot() {
        return projectRoot;
    }

    public ZenDocument getDocument(Path documentPath) {
        return documents.get(documentPath);
    }

    Map<Path, ZenDocument> getDocuments() {
        return documents;
    }

    ZenDocument buildDocument(Path documentPath) {
        ZenDocument document = new ZenDocument(documentPath, this);
        documents.put(documentPath, document);
        return document;
    }

}
