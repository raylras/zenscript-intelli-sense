package raylras.zen.langserver;

import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ZenScriptWorkspaceManager {

    private final LanguageClientLogger clientLogger;
    private final LanguageServerContext serverContext;
    private final Map<Path, ZenScriptProject> projects;

    public ZenScriptWorkspaceManager(LanguageServerContext serverContext) {
        this.serverContext = serverContext;
        this.clientLogger = LanguageClientLogger.getInstance(serverContext);
        this.projects = new HashMap<>();
    }

    public void didOpen(DidOpenTextDocumentParams params) {

    }

    public void didClose(DidCloseTextDocumentParams params) {

    }

}
