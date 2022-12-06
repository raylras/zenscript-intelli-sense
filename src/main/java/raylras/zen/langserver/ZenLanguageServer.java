package raylras.zen.langserver;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.*;
import raylras.zen.langserver.provider.SemanticTokensProvider;
import raylras.zen.project.ZenDocument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
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
        capabilities.setSemanticTokensProvider(new SemanticTokensWithRegistrationOptions(SemanticTokensProvider.SEMANTIC_TOKENS_LEGEND, true));
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
        startListeningFileChanges();
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
        return CompletableFuture.completedFuture(Boolean.TRUE);
    }

    @Override
    public void exit() {
        System.exit(shutdown);
    }

    @Override
    public void connect(LanguageClient client) {
        this.languageClient = client;
        serverContext.put(LanguageClient.class, client);
        LanguageClientLogger logger = LanguageClientLogger.getInstance(serverContext);
        logger.connect(client);
    }

    private void startListeningFileChanges() {
        LanguageClient client = serverContext.get(LanguageClient.class);
        List<FileSystemWatcher> watchers = new ArrayList<>();
        watchers.add(new FileSystemWatcher(Either.forLeft("**/*" + ZenDocument.DOCUMENT_EXTENSION), WatchKind.Create + WatchKind.Change + WatchKind.Delete));
        watchers.add(new FileSystemWatcher(Either.forLeft("**/zenproject.toml"), WatchKind.Create + WatchKind.Change + WatchKind.Delete));
        DidChangeWatchedFilesRegistrationOptions opts = new DidChangeWatchedFilesRegistrationOptions(watchers);
        Registration registration = new Registration(UUID.randomUUID().toString(), "workspace/didChangeWatchedFiles", opts);
        client.registerCapability(new RegistrationParams(Collections.singletonList(registration)));
    }

}
