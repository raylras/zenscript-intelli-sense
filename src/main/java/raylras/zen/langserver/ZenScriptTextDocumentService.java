package raylras.zen.langserver;

import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.services.TextDocumentService;

public class ZenScriptTextDocumentService implements TextDocumentService {

    private final ZenScriptLanguageServer languageServer;
    private final LanguageServerContext serverContext;
    private final LanguageClientLogger clientLogger;

    public ZenScriptTextDocumentService(ZenScriptLanguageServer languageServer,
                                        LanguageServerContext serverContext) {
        this.languageServer = languageServer;
        this.serverContext = serverContext;
        this.clientLogger = LanguageClientLogger.getInstance(serverContext);
    }


    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        clientLogger.logMessage("Opened: " + params.getTextDocument().getUri());
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {

    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {

    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {

    }

}
