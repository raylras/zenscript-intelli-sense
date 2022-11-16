package raylras.zen.langserver;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.services.*;
import raylras.zen.langserver.provider.SemanticTokensProvider;

import java.util.concurrent.CompletableFuture;

public class ZenLanguageServer implements LanguageServer, LanguageClientAware {

    private LanguageClient languageClient;
    private final LanguageServerContext serverContext;
    private final TextDocumentService textDocumentService;
    private final WorkspaceService workspaceService;
    private int shutdown = 1;

    public ZenLanguageServer() {
        this.serverContext = new LanguageServerContext();
        this.textDocumentService = new ZenTextDocumentService(this, this.serverContext);
        this.workspaceService = new ZenWorkspaceService(this, this.serverContext);
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
        ServerCapabilities capabilities = new ServerCapabilities();
//        capabilities.setCompletionProvider(new CompletionOptions());
        capabilities.setTextDocumentSync(TextDocumentSyncKind.Full);
//        capabilities.setDocumentSymbolProvider(true);
//        capabilities.setWorkspaceSymbolProvider(true);
//        capabilities.setDocumentHighlightProvider(true);
//        SignatureHelpOptions signatureHelpOptions = new SignatureHelpOptions();
//        signatureHelpOptions.setTriggerCharacters(Arrays.asList("(", ","));
//        capabilities.setSignatureHelpProvider(signatureHelpOptions);
        capabilities.setSemanticTokensProvider(new SemanticTokensWithRegistrationOptions(SemanticTokensProvider.Semantic_Tokens_Legend, true));
//        capabilities.setReferencesProvider(true);
//        capabilities.setDefinitionProvider(true);
//        capabilities.setTypeDefinitionProvider(true);
//        capabilities.setHoverProvider(true);
//        capabilities.setRenameProvider(true);

        return CompletableFuture.completedFuture(new InitializeResult(capabilities));
    }

    @Override
    public void initialized(InitializedParams params) {
        languageClient.logMessage(new MessageParams(MessageType.Log, "ZenScript Language Server initialized"));
    }

    @Override
    public TextDocumentService getTextDocumentService() {
        return textDocumentService;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return workspaceService;
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        shutdown = 0;
        return CompletableFuture.completedFuture(new Object());
    }

    @Override
    public void exit() {
        System.exit(shutdown);
    }

    @Override
    public void connect(LanguageClient client) {
        // initialize the context
        this.languageClient = client;
        this.serverContext.put(LanguageClient.class, client);
        LanguageClientLogger clientLogger = LanguageClientLogger.getInstance(this.serverContext);
        clientLogger.connect(client);
    }

}
