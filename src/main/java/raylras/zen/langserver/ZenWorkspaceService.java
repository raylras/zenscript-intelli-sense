package raylras.zen.langserver;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.util.CommonUtils;

import java.util.stream.Collectors;

public class ZenWorkspaceService implements WorkspaceService {

    private final ZenLanguageServer languageServer;
    private final LanguageServerContext serverContext;
    private final LanguageClientLogger logger;

    public ZenWorkspaceService(ZenLanguageServer languageServer,
                               LanguageServerContext serverContext) {
        this.languageServer = languageServer;
        this.serverContext = serverContext;
        this.logger = LanguageClientLogger.getInstance(serverContext);
    }

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {

    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
        logger.logMessage("workspace/didChangeWatchedFiles: "
                + params.getChanges().stream()
                .map(event -> CommonUtils.getFileName(event.getUri()))
                .collect(Collectors.toList()));
    }

}
