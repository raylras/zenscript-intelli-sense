package raylras.zen.langserver;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.services.WorkspaceService;

public class ZenScriptWorkspaceService implements WorkspaceService {

    private final ZenScriptLanguageServer languageServer;
    private final LanguageServerContext serverContext;
    private final LanguageClientLogger clientLogger;

    public ZenScriptWorkspaceService(ZenScriptLanguageServer languageServer,
                                     LanguageServerContext serverContext) {
        this.languageServer = languageServer;
        this.serverContext = serverContext;
        this.clientLogger = LanguageClientLogger.getInstance(serverContext);
    }

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {

    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {

    }

}
