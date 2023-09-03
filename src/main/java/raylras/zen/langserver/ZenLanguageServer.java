package raylras.zen.langserver;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.code.CompilationUnit;
import raylras.zen.util.l10n.L10N;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ZenLanguageServer implements LanguageServer, LanguageClientAware {

    private static final Logger logger = LoggerFactory.getLogger(ZenLanguageServer.class);

    private final ZenLanguageService service;
    private final ExecutorService mainExecutor = Executors.newCachedThreadPool();

    public ZenLanguageServer() {
        this.service = new ZenLanguageService(mainExecutor);
    }

    @Override
    public void setTrace(SetTraceParams params) {
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
        return CompletableFuture.supplyAsync(() -> {
            List<WorkspaceFolder> workspaceFolders = params.getWorkspaceFolders();
            ServerCapabilities capabilities = new ServerCapabilities();
            capabilities.setTextDocumentSync(TextDocumentSyncKind.Full);
            L10N.setLocale(params.getLocale());
            if (workspaceFolders != null) {
                service.initializeWorkspaces(workspaceFolders);
                CompletionOptions completionOptions = new CompletionOptions();
                completionOptions.setTriggerCharacters(Arrays.asList(".", ":"));
                capabilities.setCompletionProvider(completionOptions);
                capabilities.setDocumentSymbolProvider(true);
//          capabilities.setWorkspaceSymbolProvider(true);
//          capabilities.setDocumentHighlightProvider(true);
//          SignatureHelpOptions signatureHelpOptions = new SignatureHelpOptions();
//          signatureHelpOptions.setTriggerCharacters(Arrays.asList("(", ","));
//          capabilities.setSignatureHelpProvider(signatureHelpOptions);
//          capabilities.setSemanticTokensProvider(new SemanticTokensWithRegistrationOptions(Semantics.SEMANTIC_TOKENS_LEGEND, true));
                capabilities.setReferencesProvider(true);
//          capabilities.setDeclarationProvider(true);
                capabilities.setDefinitionProvider(true);
//          capabilities.setTypeDefinitionProvider(true);
                capabilities.setHoverProvider(true);
//          capabilities.setRenameProvider(true);
            }
            return new InitializeResult(capabilities);
        }, mainExecutor);
    }

    @Override
    public void initialized(InitializedParams params) {
        startListeningFileChanges();
        logger.info("Language server initialized");
    }

    @Override
    public TextDocumentService getTextDocumentService() {
        return service;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return service;
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        mainExecutor.shutdown();
        return CompletableFuture.supplyAsync(() -> {
            logger.info("waiting operation to stop..");
            try {
                return mainExecutor.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void exit() {
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
