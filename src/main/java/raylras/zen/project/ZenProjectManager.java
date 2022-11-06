package raylras.zen.project;

import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import raylras.zen.langserver.LanguageClientLogger;
import raylras.zen.langserver.LanguageServerContext;
import raylras.zen.project.ZenProject;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ZenProjectManager {

    private final LanguageClientLogger clientLogger;
    private final LanguageServerContext serverContext;
    private final Map<Path, ZenProject> projects;

    public ZenProjectManager(LanguageServerContext serverContext) {
        this.serverContext = serverContext;
        this.clientLogger = LanguageClientLogger.getInstance(serverContext);
        this.projects = new HashMap<>();
    }

    public void didOpen(DidOpenTextDocumentParams params) {

    }

    public void didClose(DidCloseTextDocumentParams params) {

    }

}