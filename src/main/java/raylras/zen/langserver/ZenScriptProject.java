package raylras.zen.langserver;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ZenScriptProject {

    private final Path projectRoot;
    private final Map<Path, ZenScriptDocument> srcDocuments;

    public ZenScriptProject(Path projectRoot) {
        this.projectRoot = projectRoot;
        this.srcDocuments = new HashMap<>();
    }



}
