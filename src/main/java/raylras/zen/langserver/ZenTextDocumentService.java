package raylras.zen.langserver;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.services.TextDocumentService;
import raylras.zen.langserver.provider.SemanticTokensProvider;
import raylras.zen.project.ZenDocument;
import raylras.zen.project.ZenProjectManager;
import raylras.zen.util.CommonUtils;

import java.util.concurrent.CompletableFuture;

public class ZenTextDocumentService implements TextDocumentService {

    private final ZenLanguageServer languageServer;
    private final LanguageServerContext serverContext;
    private final LanguageClientLogger clientLogger;

    public ZenTextDocumentService(ZenLanguageServer languageServer, LanguageServerContext serverContext) {
        this.languageServer = languageServer;
        this.serverContext = serverContext;
        this.clientLogger = LanguageClientLogger.getInstance(serverContext);
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        clientLogger.logMessage("Opened: " + params.getTextDocument().getUri());
        ZenProjectManager projectManager = ZenProjectManager.getInstance(serverContext);
        ZenDocument document = projectManager.getDocument(CommonUtils.toPath(params.getTextDocument().getUri()));
        document.update(params.getTextDocument().getText());
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        ZenProjectManager projectManager = ZenProjectManager.getInstance(serverContext);
        ZenDocument document = projectManager.getDocument(CommonUtils.toPath(params.getTextDocument().getUri()));
        document.update(params.getContentChanges().get(0).getText());
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
        clientLogger.logMessage("Closed: " + params.getTextDocument().getUri());
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
        ZenProjectManager projectManager = ZenProjectManager.getInstance(serverContext);
        ZenDocument document = projectManager.getDocument(CommonUtils.toPath(params.getTextDocument().getUri()));
        document.update(params.getText());
    }

    @Override
    public CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
        clientLogger.logMessage("SemanticTokensFull: " + params.getTextDocument().getUri());
        return CompletableFuture.completedFuture(SemanticTokensProvider.semanticTokensFull(serverContext, params));
    }

}
