package raylras.zen.lsp;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.bracket.RpcClient;
import raylras.zen.model.CompilationUnit;
import raylras.zen.util.l10n.L10N;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZenLanguageServer implements LanguageServer, LanguageClientAware {

    private static final Logger logger = LoggerFactory.getLogger(ZenLanguageServer.class);

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ZenLanguageService languageService = new ZenLanguageService();

    public ExecutorService getExecutorService() {
        return executorService;
    }

    @Override
    public void setTrace(SetTraceParams params) {
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
        languageService.initializeWorkspaces(params.getWorkspaceFolders());
        L10N.setLocale(params.getLocale());

        ServerCapabilities capabilities = new ServerCapabilities();
        capabilities.setTextDocumentSync(TextDocumentSyncKind.Full);
        capabilities.setCompletionProvider(new CompletionOptions(true, List.of(".", ":")));
        capabilities.setDocumentSymbolProvider(true);
        // capabilities.setWorkspaceSymbolProvider(true);
        // capabilities.setDocumentHighlightProvider(true);
        // SignatureHelpOptions signatureHelpOptions = new SignatureHelpOptions();
        // signatureHelpOptions.setTriggerCharacters(Arrays.asList("(", ","));
        // capabilities.setSignatureHelpProvider(signatureHelpOptions);
        // capabilities.setSemanticTokensProvider(new SemanticTokensWithRegistrationOptions(Semantics.SEMANTIC_TOKENS_LEGEND, true));
        capabilities.setReferencesProvider(true);
        // capabilities.setDeclarationProvider(true);
        capabilities.setDefinitionProvider(true);
        // capabilities.setTypeDefinitionProvider(true);
        capabilities.setHoverProvider(true);
        // capabilities.setRenameProvider(true);
        return CompletableFuture.completedFuture(new InitializeResult(capabilities));
    }

    @Override
    public void initialized(InitializedParams params) {
        startListeningFileChanges();
        logger.info("Language server initialized");
    }

    @Override
    public TextDocumentService getTextDocumentService() {
        return languageService;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return languageService;
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("Language server shutting down");
            RpcClient.shutdown();
            executorService.shutdown();
            return null;
        });
    }

    @Override
    public void exit() {
        logger.info("Language server exiting");
        System.exit(0);
    }

    @Override
    public void connect(LanguageClient client) {
        ZenLanguageService.setClient(client);
    }

    private void startListeningFileChanges() {
        List<FileSystemWatcher> watchers = new ArrayList<>(1);
        watchers.add(new FileSystemWatcher(Either.forLeft("**/*" + CompilationUnit.ZS_FILE_EXTENSION), WatchKind.Create + WatchKind.Change + WatchKind.Delete));
        Object options = new DidChangeWatchedFilesRegistrationOptions(watchers);
        Registration registration = new Registration(UUID.randomUUID().toString(), "workspace/didChangeWatchedFiles", options);
        ZenLanguageService.getClient().registerCapability(new RegistrationParams(Collections.singletonList(registration)));
    }

}
