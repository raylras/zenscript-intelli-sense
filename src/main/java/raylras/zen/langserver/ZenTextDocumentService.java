package raylras.zen.langserver;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.services.TextDocumentService;
import raylras.zen.langserver.provider.SemanticTokensProvider;
import raylras.zen.project.ZenProjectManager;
import raylras.zen.util.CommonUtils;
import raylras.zen.util.Timer;

import java.io.StringReader;
import java.util.concurrent.CompletableFuture;

public class ZenTextDocumentService implements TextDocumentService {

    private final ZenLanguageServer languageServer;
    private final LanguageServerContext serverContext;
    private final LanguageClientLogger logger;

    public ZenTextDocumentService(ZenLanguageServer languageServer, LanguageServerContext serverContext) {
        this.languageServer = languageServer;
        this.serverContext = serverContext;
        this.logger = LanguageClientLogger.getInstance(serverContext);
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        logger.logMessage("textDocument/didOpen: " + CommonUtils.getFileName(params.getTextDocument().getUri()));
        ZenProjectManager projectManager = ZenProjectManager.getInstance(serverContext);
        projectManager.openDocument(CommonUtils.toPath(params.getTextDocument().getUri()));
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        Timer timer = new Timer();
        ZenProjectManager projectManager = ZenProjectManager.getInstance(serverContext);
        projectManager.updateDocument(CommonUtils.toPath(params.getTextDocument().getUri()), new StringReader(params.getContentChanges().get(0).getText()));
        logger.logMessage("textDocument/didChange: " + CommonUtils.getFileName(params.getTextDocument().getUri()) + ", took " + timer.cost());
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
        logger.logMessage("textDocument/didClose: " + CommonUtils.getFileName(params.getTextDocument().getUri()));
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
        logger.logMessage("textDocument/didSave: " + CommonUtils.getFileName(params.getTextDocument().getUri()));
    }

    @Override
    public CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
        Timer timer = new Timer();
        SemanticTokens semanticTokens = SemanticTokensProvider.semanticTokensFull(serverContext, params);
        logger.logMessage("textDocument/semanticTokensFull: " + CommonUtils.getFileName(params.getTextDocument().getUri()) + ", took " + timer.cost());
        return CompletableFuture.completedFuture(semanticTokens);
    }

}
