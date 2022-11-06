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

}
