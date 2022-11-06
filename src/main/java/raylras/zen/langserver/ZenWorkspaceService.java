package raylras.zen.langserver;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.services.WorkspaceService;

public class ZenWorkspaceService implements WorkspaceService {

    private final ZenLanguageServer languageServer;
    private final LanguageServerContext serverContext;
    private final LanguageClientLogger clientLogger;

    public ZenWorkspaceService(ZenLanguageServer languageServer,
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
